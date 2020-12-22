package com.ymest.rebost.json

import com.google.gson.annotations.SerializedName

data class Producto(
    @field:SerializedName("product")
    val product: Product? = null,

    @field:SerializedName("code")
    val code: String? = null,

    @field:SerializedName("status_verbose")
    val statusVerbose: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)