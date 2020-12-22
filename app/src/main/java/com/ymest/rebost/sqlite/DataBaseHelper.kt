package com.ymest.rebost.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context):SQLiteOpenHelper(context, Column.Companion.TProductes.T_PRODUCTES, null, Column.VERSION) {
    companion object{
        val CREATE_TAULA_PRODUCTES =
            "CREATE TABLE " + Column.Companion.TProductes.T_PRODUCTES +
                    "(" + Column.Companion.TProductes.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Column.Companion.TProductes.COL_CODI + " TEXT, " +
                    Column.Companion.TProductes.COL_STATUS + " INTEGER, " +
                    Column.Companion.TProductes.COL_STATUS_VERBOSE + " TEXT, " +
                    Column.Companion.TProductes.COL_NAME_ES + " TEXT, " +
                    Column.Companion.TProductes.COL_NAME_EN + " TEXT, " +
                    Column.Companion.TProductes.COL_GENERIC_NAME + " TEXT, " +
                    Column.Companion.TProductes.COL_GENERIC_NAME_ES + " TEXT, " +
                    Column.Companion.TProductes.COL_PRODUCT_NAME + " TEXT, " +
                    Column.Companion.TProductes.COL_QUANTITY + " TEXT, " +
                    Column.Companion.TProductes.COL_PRODUCT_QUANTITY + " TEXT, " +
                    Column.Companion.TProductes.COL_SERVING_SIZE + " TEXT, " +
                    Column.Companion.TProductes.COL_SERVING_QUANTITY + " TEXT, " +
                    Column.Companion.TProductes.COL_BRANDS + " TEXT, " +
                    Column.Companion.TProductes.COL_STORES + " TEXT, " +
                    Column.Companion.TProductes.COL_STORES_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_MANUFACTURING_PLACES + " TEXT, " +
                    Column.Companion.TProductes.COL_CATEGORIES + " TEXT, " +
                    Column.Companion.TProductes.COL_CATEGORIES_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_CATEGORIES_HIERARCHY + " TEXT, " +
                    Column.Companion.TProductes.COL_PNNS_GROUPS_1 + " TEXT, " +
                    Column.Companion.TProductes.COL_PNNS_GROUPS_2 + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TEXT + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TEXT_ES + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TEXT_EN + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_ES + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_TEXT_WITH_ALLERGENS_EN + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_FROM_PALM_OIL_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_INGREDIENTS_THAT_MAY_BE_FROM_PALM_OIL_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_ADDITIVES_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_ADDITIVES_ORIGINAL_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_ALLERGENS_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_ALLERGENS_HIERARCHY + " TEXT, " +
                    Column.Companion.TProductes.COL_TRACES + " TEXT, " +
                    Column.Companion.TProductes.COL_NUTRITION_GRADES + " TEXT, " +
                    Column.Companion.TProductes.COL_NUTRITION_DATA_PREPARED_PER + " TEXT, " +
                    Column.Companion.TProductes.COL_NUTRITION_DATA_PER + " TEXT, " +
                    Column.Companion.TProductes.COL_NUTRIENT_LEVELS_TAGS + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_SMALL_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_THUMB_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_SMALL_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_INGREDIENTS_THUMB_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_NUTRITION_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_NUTRITION_SMALL_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_NUTRITION_THUMB_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_FRONT_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_FRONT_SMALL_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_IMAGE_FRONT_THUMB_URL + " TEXT, " +
                    Column.Companion.TProductes.COL_NOVA_GROUP + " TEXT, " +
                    Column.Companion.TProductes.COL_NOVA_GROUPS + " TEXT, " +
                    Column.Companion.TProductes.COL_NUTRISCORE_SCORE + " INTEGER, " +
                    Column.Companion.TProductes.COL_NUTRISCORE_GRADE + " TEXT, " +
                    Column.Companion.TProductes.COL_UNIQUE_SCANS_N + " INTEGER, " +
                    Column.Companion.TProductes.COL_SCANS_N + " INTEGER, " +
                    Column.Companion.TProductes.COL_KEYWORDS + " TEXT) "

        val CREATE_TAULA_PERSONALITZADA =
            "CREATE TABLE " + Column.Companion.TPersonalitzada.T_PERSONALITZADA +
                    "(" + Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + " TEXT PRIMARY KEY, " +
                    Column.Companion.TPersonalitzada.COL_DATA_AFEGIT + " INTEGER, " +
                    Column.Companion.TPersonalitzada.COL_DATA_VIST + " INTEGER, " +
                    Column.Companion.TPersonalitzada.COL_FAVORIT + " INTEGER, " +
                    Column.Companion.TPersonalitzada.COL_GUST + " INTEGER, " +
                    Column.Companion.TPersonalitzada.COL_PUNTUACIO + " REAL) "

        val CREATE_TAULA_LLISTES =
            "CREATE TABLE " + Column.Companion.TLlistes.T_LLISTES +
                    "(" + Column.Companion.TLlistes.COL_LLISTES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Column.Companion.TLlistes.COL_LLISTES_NOM + " TEXT, " +
                    Column.Companion.TLlistes.COL_DATA_CAD + " INTEGER, " +
                    Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD + " INTEGER, " +
                    Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES + " INTEGER ) "

        val INSERT_DEFAULT_LLISTES =
            "INSERT INTO " + Column.Companion.TLlistes.T_LLISTES +
                    "(" + Column.Companion.TLlistes.COL_LLISTES_NOM +
                    "," + Column.Companion.TLlistes.COL_DATA_CAD +
                    "," + Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD +
                    "," + Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES + ")" +
                    "SELECT 'Llista de la Compra','0', '1', '0'"

        val INSERT_DEFAULT_LLISTES2 =
            "INSERT INTO " + Column.Companion.TLlistes.T_LLISTES +
                    "(" + Column.Companion.TLlistes.COL_LLISTES_NOM +
                    "," + Column.Companion.TLlistes.COL_DATA_CAD +
                    "," + Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD +
                    "," + Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES + ")" +
                    "SELECT 'Llista del Rebost', '1', '1', '1'"

        val CREATE_TAULA_PRODUCTES_A_LLISTES =
            "CREATE TABLE " + Column.Companion.TProductesALlista.T_PRODUCTESALLISTA +
                    "(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " TEXT, " +
                    Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " INTEGER, " +
                    Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT + " INTEGER, " +
                    Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " LONG, " +
                    Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO + " INTEGER) "

        val CREATE_TAULA_UBICACIONS =
            "CREATE TABLE " + Column.Companion.TUbicacions.T_UBICACIONS +
                    "(" + Column.Companion.TUbicacions.COL_ID_UBICACIONS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Column.Companion.TUbicacions.COL_NOM_UBICACIONS + " TEXT, " +
                    Column.Companion.TUbicacions.COL_DESCUBICACIONS + " TEXT) "

        val INSERT_UBICACIO = "INSERT INTO " + Column.Companion.TUbicacions.T_UBICACIONS +
                "(" + Column.Companion.TUbicacions.COL_NOM_UBICACIONS + "," +
                Column.Companion.TUbicacions.COL_DESCUBICACIONS + ")" +
                "SELECT 'Ubicació per defecte', 'Ubicació per defecte'"

        val CREATE_TAULA_DATACADUCITAT =
            "CREATE TABLE " + Column.Companion.TDataCad.T_DATACADUCITAT +
                    "(" + Column.Companion.TDataCad.COL_DATACADUCITAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Column.Companion.TDataCad.COL_DATACADUCITAT_LLISTAID + " INTEGER, " +
                    Column.Companion.TDataCad.COL_DATACADUCITAT_CODIBARRES + " TEXT, " +
                    Column.Companion.TDataCad.COL_DATACADUCITAT_DATA + " LONG, " +
                    Column.Companion.TDataCad.COL_DATACADUCITAT_QUANTITAT + " INTEGER)"

        /*val CREATE_TAULA_REBOST =
            "CREATE TABLE " + ProductesContract.Companion.Entrada.NOM_TAULA_REBOST +
                    "(" + ProductesContract.Companion.Entrada.COL_ID_REBOST + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST + " INTEGER, " +
                    ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST + " INTEGER, " +
                    ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST + " BOOLEAN, " +
                    ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST + " INTEGER, " +
                    ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST + " LONG) "

        val CREATE_TAULA_COMPRAR =
            "CREATE TABLE " + ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR +
                    "(" + ProductesContract.Companion.Entrada.COL_ID_COMPRAR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_QUANTITAT_COMPRAR + " INTEGER, " +
                    ProductesContract.Companion.Entrada.COL_DATA_ALTA_COMPRAR + " LONG, " +
                    ProductesContract.Companion.Entrada.COL_COMPRAT_COMPRAR + " BOOLEAN) "*/






        val REMOVE_TAULA_PRODUCTES = "DROP TABLE IF EXISTS " + Column.Companion.TProductes.T_PRODUCTES
        val REMOVE_TAULA_PERSONALITZADA = "DROP TABLE IF EXISTS " + Column.Companion.TPersonalitzada.T_PERSONALITZADA
        val REMOVE_TAULA_LLISTES = "DROP TABLE IF EXISTS " + Column.Companion.TLlistes.T_LLISTES
        val REMOVE_TAULA_PRODUCTES_A_LLISTES = "DROP TABLE IF EXISTS " + Column.Companion.TProductesALlista.T_PRODUCTESALLISTA
        val REMOVE_TAULA_UBICACIONS = "DROP TABLE IF EXISTS " + Column.Companion.TUbicacions.T_UBICACIONS
        val REMOVE_TAULA_DATACADUCITAT = "DROP TABLE IF EXISTS " + Column.Companion.TDataCad.T_DATACADUCITAT
        /*val REMOVE_TAULA_REBOST = "DROP TABLE IF EXISTS " + ProductesContract.Companion.Entrada.NOM_TAULA_REBOST
        val REMOVE_TAULA_COMPRAR = "DROP TABLE IF EXISTS " + ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR
        */
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_TAULA_PRODUCTES)
        p0?.execSQL(CREATE_TAULA_PERSONALITZADA)
        p0?.execSQL(CREATE_TAULA_LLISTES)
        p0?.execSQL(INSERT_DEFAULT_LLISTES)
        p0?.execSQL(INSERT_DEFAULT_LLISTES2)
        p0?.execSQL(CREATE_TAULA_PRODUCTES_A_LLISTES)
        p0?.execSQL(CREATE_TAULA_UBICACIONS)
        p0?.execSQL(INSERT_UBICACIO)
        p0?.execSQL(CREATE_TAULA_DATACADUCITAT)
       /* p0?.execSQL(CREATE_TAULA_REBOST)
        p0?.execSQL(CREATE_TAULA_COMPRAR)
        p0?.execSQL(CREATE_TAULA_UBICACIONS)
        */
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(REMOVE_TAULA_PRODUCTES)
        p0?.execSQL(REMOVE_TAULA_PERSONALITZADA)
        p0?.execSQL(REMOVE_TAULA_LLISTES)
        p0?.execSQL(REMOVE_TAULA_PRODUCTES_A_LLISTES)
        p0?.execSQL(REMOVE_TAULA_UBICACIONS)
        p0?.execSQL(REMOVE_TAULA_DATACADUCITAT)
        /*p0?.execSQL(REMOVE_TAULA_COMPRAR)
        p0?.execSQL(REMOVE_TAULA_UBICACIONS)*/

        onCreate(p0)
    }
}