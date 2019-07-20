package com.db.foody.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.db.foody.app.FoodyApplication
import com.db.foody.data.sync.RecipesRepository
import com.db.foody.view.adapter.RecipesAdapter
import com.db.foody.viewmodel.state.MainActivityState

class MainActivityViewModel : ViewModel() {
    private val state:MutableLiveData<MainActivityState>
    lateinit var repository: RecipesRepository
    private var currentPage=0
    var recipesAdapter = RecipesAdapter(ArrayList())
    init {
        FoodyApplication.component.inject(this)
        state = MutableLiveData()
        //TODO initRx()
        //TODO initSearch()
    }


}