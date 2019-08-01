package com.db.newly.view.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.db.newly.R
import com.db.newly.extensions.hide
import com.db.newly.extensions.show
import com.db.newly.util.InfiniteRecyclerViewListener
import com.db.newly.util.Status
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import android.content.res.Configuration
import com.db.newly.view.BaseActivity


class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private var searchView:SearchView?=null
    private var infiniteRecyclerViewListener: InfiniteRecyclerViewListener?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
         initViewModel()
         initLayout()
         initSubscriptions()
    }


    private fun initViewModel(){
        viewModel=ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private fun initLayout(){

        val spanCount = if (resources.configuration.orientation
            == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
        val gridLayoutManager = GridLayoutManager(applicationContext, spanCount)

        rv_recipes.layoutManager=gridLayoutManager
        rv_recipes.adapter = viewModel.imagesAdapter
        rv_recipes.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard()
            }
        })

        infiniteRecyclerViewListener = object : InfiniteRecyclerViewListener(gridLayoutManager){
            override fun onLoadMore(currentPage: Int) {
                viewModel.loadMore(searchView?.query.toString())
            }
        }
        rv_recipes.addOnScrollListener(infiniteRecyclerViewListener as InfiniteRecyclerViewListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        searchView=menu?.findItem(R.id.search)?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.search(p0!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.search(p0!!)
                search_results.hide()
                return true
            }
        })
        searchView?.setQuery(viewModel.searchQuery,true)
        return true
    }

    private fun initSubscriptions(){
        viewModel.state.observe(this, Observer<MainActivityState>{
            it?.let {
                update(it)
            }
        })
        attachClickListener()
    }

    private fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        //checking if no views has focus
        val v = this.currentFocus?:return
        inputManager.hideSoftInputFromWindow(v.windowToken,0)
    }

    private fun attachClickListener(){

    }

    private fun update(state: MainActivityState){
        when(state.status){
            Status.LOADING->{
                pb_recipes.show()
                empty.hide()
            }
            Status.SHORT_QUERY->{
                empty.show()
                title="Newly"
                search_results.hide()
            }
            Status.COMPLETE->{
                pb_recipes.hide()
            }
            Status.SUCCESS->{
                if(rv_recipes.adapter?.itemCount==0){
                    empty.show()
                    title="Newly"
                    search_results.hide()
                }else{
                    search_results.show()
                    search_results.text="${viewModel.imagesAdapter.itemCount} results"
                    title="Newly - ${searchView?.query}"
                }
                infiniteRecyclerViewListener?.setLoading(false)
                pb_recipes.hide()
            }
            Status.ERROR->{
                Log.e("MainActivity: ", state.error?.localizedMessage)
            }
        }
    }
}
