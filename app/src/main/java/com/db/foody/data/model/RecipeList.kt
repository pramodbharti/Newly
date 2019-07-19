package com.db.foody.data.model

import com.google.gson.annotations.SerializedName

data class RecipeList(
    @SerializedName("count")
    val count:String,
    @SerializedName("recipes")
    val recipes:List<Recipe>
)