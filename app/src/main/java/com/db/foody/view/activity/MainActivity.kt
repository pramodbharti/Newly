package com.db.foody.view.activity

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.db.foody.R
import com.db.foody.view.adapter.InfiniteRecyclerViewListener
import com.db.foody.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private var searchView:SearchView?=null
    private var infiniteRecyclerViewListener:InfiniteRecyclerViewListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        //TODO initViewModel()
        //TODO initLayout()
        //TODO initSubscriptions()
    }




}
