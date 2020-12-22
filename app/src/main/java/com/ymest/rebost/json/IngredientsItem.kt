package com.ymest.rebost.json

import com.google.gson.annotations.SerializedName

data class IngredientsItem(

    @field:SerializedName("percent_max")
    val percentMax: Double? = null,

    @field:SerializedName("percent_min")
    val percentMin: Double? = null,

    @field:SerializedName("vegetarian")
    val vegetarian: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("vegan")
    val vegan: String? = null,

    @field:SerializedName("text")
    val text: String? = null,

    @field:SerializedName("rank")
    val rank: Int? = null,

    @field:SerializedName("has_sub_ingredients")
    val hasSubIngredients: String? = null
)