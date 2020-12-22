package com.ymest.rebost.utils

class Constants {
    companion object{



        val TAB_PRIMERA = "REBOST"
        val TAB_SEGONA = "COMPRAR"
        val TAB_TERCERA = "CONSULTATS"

        val TAB_DP_PRIMERA = "RESUM"
        val TAB_DP_SEGONA = "INGREDIENTS"
        val TAB_DP_TERCERA = "PENDENT"

        val TIEMPO_AVISO_CADUCIDAD_HORAS = 48

        //JSON - API

        val URL_API_CATEGORIES = "https://es.openfoodfacts.org/categories.json"
        val URL_API_PRODUCTES_CATEGORIA = "https://es.openfoodfacts.org/categoria/"
        val URL_API_SEARCH = "https://es.openfoodfacts.org/cgi/search.pl?search_terms=havarti&search_simple=1&tagtype_0=categories&tag_contains_0=contains&tag_0=queso-de-vaca&action=process&json=1"
        val URL_API_MARQUES = "https://es.openfoodfacts.org/brands.json"
        val URL_API_PRODUCTES_MARCA = "https://es.openfoodfacts.org/brand/"
        val URL_INFO_NOVA = "https://es.openfoodfacts.org/nova"
        val URL_INFO_NUTRISCORE = "https://es.openfoodfacts.org/nutriscore"
        val URL_PRODUCT_WEB = "https://es.openfoodfacts.org/product/"

        val CATEGORIES = "Categorias"
        val MARQUES = "Marcas"
        val MARQUESYSUPER = "Marcas y Supermercados"
        val UBICACIONS = "Ubicaciones"
        val LLISTES = "Listas"
        val MISLISTAS = "Mis Listas"
        val MAINACT = "MainActivity"
        val HISTORIAL = "Historial"
    }
}