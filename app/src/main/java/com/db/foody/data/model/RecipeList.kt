package com.db.foody.data.model

import com.google.gson.annotations.SerializedName

data class RecipeList(
    @SerializedName("recipes")
    val recipes:List<Recipe>
)