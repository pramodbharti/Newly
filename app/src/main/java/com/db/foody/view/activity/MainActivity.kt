package com.db.foody.view.activity

import android.content.Context
import android.hardware.input.InputManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db.foody.R
import com.db.foody.extensions.hide
import com.db.foody.extensions.show
import com.db.foody.extensions.toastLong
import com.db.foody.extensions.toastShort
import com.db.foody.view.adapter.InfiniteRecyclerViewListener
import com.db.foody.viewmodel.MainActivityViewModel
import com.db.foody.viewmodel.state.MainActivityState
import com.db.foody.viewmodel.state.Status
import kotlinx.android.synthetic.main.activity_main.*

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
         initViewModel()
         initLayout()
         initSubscriptions()
    }


    private fun initViewModel(){
        viewModel=ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private fun initLayout(){
        val layoutManager = LinearLayoutManager(this)
        rv_recipes.layoutManager=layoutManager
        rv_recipes.adapter = viewModel.recipesAdapter
        rv_recipes.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard()
            }
        })

        infiniteRecyclerViewListener = object : InfiniteRecyclerViewListener(layoutManager){
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
            }
            Status.SHORT_QUERY->{
                pb_recipes.hide()
            }
            Status.COMPLETE->{
                pb_recipes.hide()
            }
            Status.SUCCESS->{
                if(rv_recipes.adapter?.itemCount==0){
                    toastLong("No recipes found")
                }else{
                    rv_recipes.visibility= View.VISIBLE
                    toastShort("Total ${rv_recipes.adapter?.itemCount}")
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
