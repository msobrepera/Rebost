package com.ymest.rebost.json



data class ListaPersonalizadaProducto(
    val product: Product? = null,
    val code: String? = null,
    val statusVerbose: String? = null,
    val status: Int? = null,
    val personalitzat: Personalitzat?
)