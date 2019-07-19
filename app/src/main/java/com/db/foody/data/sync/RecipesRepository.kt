package com.db.foody.data.sync

import android.annotation.SuppressLint
import com.db.foody.data.model.RecipeList
import com.db.foody.extensions.maybe
import io.reactivex.Maybe
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class RecipesRepository(private val apiService: ApiService) {
    private var cache = RecipeCache()

    fun getRecipes(search: String, page: Int): Maybe<RecipeList> {
        return Maybe
            .concat(
                cache.getPageOfRecipes(search, page)
                    .subscribeOn(Schedulers.io()),
                apiService.getRecipes(search, page)
                    .doOnSuccess {
                        cache.update(search, page, it)
                    }.subscribeOn(Schedulers.io())
            )
            .firstElement()
    }


    class RecipeCache {
        @SuppressLint("UseSparseArrays")
        private var cachedRecipes = HashMap<Int, RecipeList>()

        var searchQuery = ""

        private fun clear() {
            cachedRecipes.clear()
        }

        fun getPageOfRecipes(search: String, page: Int): Maybe<RecipeList> {
            if (searchQuery != search)
                clear()
            searchQuery = search
            if (cachedRecipes.keys.contains(page))
                return maybe(cachedRecipes[page])
            return Maybe.empty()
        }

        fun update(search: String, page: Int, data: RecipeList) {
            if (searchQuery == search)
                cachedRecipes[page] = data
        }
    }
}