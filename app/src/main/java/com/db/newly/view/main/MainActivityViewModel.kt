package com.db.newly.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.db.newly.app.NewlyApp
import com.db.newly.data.model.ImagePost
import com.db.newly.data.model.ImgurResponse
import com.db.newly.data.sync.ImagesRepository
import com.db.newly.extensions.isValidURL

import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {
    val state: MutableLiveData<MainActivityState>

    init {
        NewlyApp.component.inject(this)
        state = MutableLiveData()
        initRx()
        initSearch()
    }

    @Inject lateinit var repository: ImagesRepository

    private var currentPage = 1
    var imagesAdapter = ImagesAdapter(ArrayList())
    var searchQuery = "nature"
    private lateinit var searchEmitter: PublishSubject<String>
    private lateinit var disposable: CompositeDisposable

    fun search(query: String?=null) {
        if (searchQuery != query) {
            imagesAdapter.images = ArrayList()
            imagesAdapter.notifyDataSetChanged()
        }
        searchQuery = query!!
        searchEmitter.onNext(query)
    }

    private fun searchImages(query: String?=null): Maybe<ImgurResponse> {
        return repository.getImages(currentPage,query)
    }


    fun loadMore(query: String?=null) {
        currentPage++
        searchEmitter.onNext(query!!)
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
                .doOnNext { state.postValue(MainActivityState.loading()) }
                .doOnTerminate { state.postValue(MainActivityState.complete()) }
                .switchMap {
                    searchImages(it).toObservable()
                }
                .map {
                    val newData = ImgurResponse(it.data?.filter { item ->
                        item.images?.isNotEmpty() == true && item.images.first().link.isValidURL()
                                && item.nsfw == false
                                && item.images.first().size ?:Long.MAX_VALUE <= 1500000L
                    })
                    newData
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { response ->
                    updateAdapter(response.data)
                    state.postValue(MainActivityState.success())
                }
                .subscribe({}, { state.postValue(MainActivityState.error(it)) })
        )
    }

    private fun updateAdapter(data: List<ImagePost>?) {
        imagesAdapter.images = imagesAdapter.images.plus(data?:ArrayList())
        imagesAdapter.notifyDataSetChanged()
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