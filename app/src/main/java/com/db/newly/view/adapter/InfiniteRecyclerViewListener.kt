package com.db.newly.view.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.db.newly.util.VISIBLE_THRESHOLD

abstract class InfiniteRecyclerViewListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var loading = true
    private var currentPage = 1
    private var previousTotal = 0


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem + VISIBLE_THRESHOLD) {
            //End of list has been reached
            onLoadMore(++currentPage)
            loading = true
        }
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    abstract fun onLoadMore(currentPage: Int)
}