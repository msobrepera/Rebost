package com.ymest.rebost.json

import com.google.gson.annotations.SerializedName

data class NutrientLevels(

    @field:SerializedName("sugars")
    val sugars: String? = null,

    @field:SerializedName("salt")
    val salt: String? = null,

    @field:SerializedName("fat")
    val fat: String? = null,

    @field:SerializedName("saturated-fat")
    val saturatedFat: String? = null
)