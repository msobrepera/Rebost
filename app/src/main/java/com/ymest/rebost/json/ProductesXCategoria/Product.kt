package com.ymest.rebost.json.ProductesXCategoria

data class Product(
    var _keywords: List<String>,
    var brands: String,
    var categories: String,
    var generic_name: String,
    var generic_name_es: String,
    var id: String,
    var image_front_url: String,
    var image_nutrition_url: String,
    var image_thumb_url: String,
    var image_url: String,
    var pnns_groups_1: String,
    var pnns_groups_2: String,
    var product_name: String,
    var product_name_es: String,
    var purchase_places: String,
    var stores_tags: List<String>,
    var url: String
)