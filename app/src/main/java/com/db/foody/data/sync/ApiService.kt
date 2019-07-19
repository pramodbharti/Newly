package com.db.foody.data.sync

import com.db.foody.data.model.RecipeDetail
import com.db.foody.data.model.RecipeList
import com.db.foody.util.GET
import com.db.foody.util.SEARCH
import io.reactivex.Maybe
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(SEARCH)
    fun getRecipes(
        @Query("q") q: String?,
        @Query("page") page: Int?
    ): Maybe<RecipeList>

    @GET(GET)
    fun getRecipeDetail(
        @Query("rId") recipeId: String
    ): Maybe<RecipeDetail>
}