package com.ymest.rebost.json.ProductesXCategoria

data class ProductosXCategoria(
    val count: Int,
    val page: Int,
    val page_size: Int,
    val products: List<Product>,
    val skip: Int
)