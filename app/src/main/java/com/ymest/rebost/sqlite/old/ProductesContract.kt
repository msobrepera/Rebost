package com.ymest.rebost.sqlite.old

import android.provider.BaseColumns

class ProductesContract {
    companion object{
        val VERSION = 2
        class Entrada: BaseColumns {
            companion object{
                //TAULA PRODUCTES
                val NOM_TAULA_PRODUCTES = "productes"
                val COL_ID = "id"
                val COL_CODI_BARRES = "codibarres"
                val COL_NOM = "nom"
                val COL_NOM_ES = "nom_es"
                val COL_MARCA = "marca"
                val COL_IMG_URL = "img"
                val COL_STATUS = "status"
                val COL_STORE = "store"
                val COL_QUANTITAT = "quantity"

                //TAULA REBOST
                val NOM_TAULA_REBOST = "rebost"
                val COL_ID_REBOST = "id"
                val COL_CODI_BARRES_REBOST = "codibarres"
                val COL_DATA_CADUCITAT_REBOST = "datacaducitat"
                val COL_QUANTITAT_REBOST = "quantitat"
                val COL_CONSUMIT_REBOST = "consumit"
                val COL_ID_UBICACIO_REBOST = "idubicacio"
                val COL_DATA_ALTA_REBOST = "dataalta"

                //TAULA COMPRAR
                val NOM_TAULA_COMPRAR = "comprar"
                val COL_ID_COMPRAR = "id"
                val COL_CODI_BARRES_COMPRAR = "codibarres"
                val COL_QUANTITAT_COMPRAR = "quantitat"
                val COL_DATA_ALTA_COMPRAR = "dataalta"
                val COL_COMPRAT_COMPRAR = "comprat"

                //TAULA UBICACIONS
                val NOM_TAULA_UBICACIONS = "ubicacions"
                val COL_ID_UBICACIONS = "id"
                val COL_NOM_UBICACIONS = "nomubicacio"
                val COL_DESCUBICACIONS = "descubicacio"


                //TAULA DATES CADUCITAT
                val NOM_TAULA_DATES_CADUCITAT = "datescaducitat"
                val COL_ID_DATES_CADUCITAT = "id"
                val COL_CODI_BARRES_DATES_CADUCITAT = "codibarres"
                val COL_QUANTITAT_DATES_CADUCITAT = "quantitat"
                val COL_DATA_DATES_CADUCITAT = "data"
            }
        }
    }
}