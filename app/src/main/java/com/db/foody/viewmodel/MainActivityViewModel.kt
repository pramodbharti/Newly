package com.db.foody.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.db.foody.app.FoodyApplication
import com.db.foody.data.model.Recipe
import com.db.foody.data.model.RecipeList
import com.db.foody.data.sync.RecipesRepository
import com.db.foody.extensions.isValidURL
import com.db.foody.view.adapter.RecipesAdapter
import com.db.foody.viewmodel.state.MainActivityState
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MainActivityViewModel : ViewModel() {
    private val state: MutableLiveData<MainActivityState>
    lateinit var repository: RecipesRepository
    private var currentPage = 0
    var recipesAdapter = RecipesAdapter(ArrayList())
    var searchQuery = ""
    private lateinit var searchEmitter: PublishSubject<String>
    private lateinit var disposable: CompositeDisposable

    init {
        FoodyApplication.component.inject(this)
        state = MutableLiveData()
        initRx()
        initSearch()
    }

    fun search(query: String) {
        if (searchQuery != query) {
            recipesAdapter.recipes = ArrayList()
            recipesAdapter.notifyDataSetChanged()
        }
        searchQuery = query
        searchEmitter.onNext(query)
    }

    private fun searchRecipes(query: String): Maybe<RecipeList> {
        return repository.getRecipes(query, currentPage)
    }

    fun loadMore(query: String) {
        currentPage++
        searchEmitter.onNext(query)
    }

    private fun initRx() {
        disposable = CompositeDisposable()
    }

    private fun initSearch() {
        searchEmitter = PublishSubject.create()
        addSub(
            searchEmitter
                .subscribeOn(Schedulers.io())
                .debounce(250, TimeUnit.MILLISECONDS)
                .doOnNext {
                    if (it.length < 2) {
                        state.postValue(MainActivityState.shortQuery())
                    }
                }
                .filter {
                    it.length > 1
                }
                .doOnNext { state.postValue(MainActivityState.loading()) }
                .doOnTerminate { state.postValue(MainActivityState.complete()) }
                .switchMap {
                    searchRecipes(it).toObservable()
                }
                .map {
                    val newData = RecipeList(it.recipes.filter { item ->
                        item.imageUrl.isValidURL()
                    })
                    newData
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { response ->
                    updateAdapter(response.recipes)
                    state.postValue(MainActivityState.success())
                }
                .subscribe({}, { state.postValue(MainActivityState.error(it)) })
        )
    }

    private fun updateAdapter(data: List<Recipe>) {
        if (searchQuery.length > 1) {
            recipesAdapter.recipes = recipesAdapter.recipes.plus(data)
            recipesAdapter.notifyDataSetChanged()
        } else {
            recipesAdapter.recipes = ArrayList()
        }
    }

    @Synchronized
    private fun addSub(disposable: Disposable?) {
        if (disposable == null) return
        this.disposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.dispose()
    }
}