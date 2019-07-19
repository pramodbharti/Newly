package com.db.foody.data.model

import com.google.gson.annotations.SerializedName

data class RecipeDetail(
    @SerializedName("recipe")
    val recipe: Recipe
)