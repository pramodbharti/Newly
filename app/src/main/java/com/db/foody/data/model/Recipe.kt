package com.db.foody.data.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    @SerializedName("publisher")
    val publisher:String,
    @SerializedName("f2f_url")
    val f2fUrl:String,
    @SerializedName("ingredients")
    val ingredients:List<String>?=null,
    @SerializedName("source_url")
    val sourceUrl:String,
    @SerializedName("recipe_id")
    val recipeId:String,
    @SerializedName("image_url")
    val imageUrl:String,
    @SerializedName("social_rank")
    val socialRanking:Double,
    @SerializedName("publisher_url")
    val publisherUrl:String,
    @SerializedName("title")
    val title:String
)