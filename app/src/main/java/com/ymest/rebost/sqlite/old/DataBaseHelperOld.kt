package com.ymest.rebost.sqlite.old

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelperOld(context: Context):SQLiteOpenHelper(context,
    ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, null,
    ProductesContract.VERSION
) {
    companion object{
        val CREATE_TAULA_PRODUCTES =
            "CREATE TABLE " + ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +
                    "(" + ProductesContract.Companion.Entrada.COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProductesContract.Companion.Entrada.COL_CODI_BARRES + " TEXT UNIQUE, " +
                    ProductesContract.Companion.Entrada.COL_NOM + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_NOM_ES + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_MARCA + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_IMG_URL + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_STATUS + " INTEGER, " +
                    ProductesContract.Companion.Entrada.COL_STORE + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_QUANTITAT + " TEXT) "

        val CREATE_TAULA_REBOST =
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
                    ProductesContract.Companion.Entrada.COL_COMPRAT_COMPRAR + " BOOLEAN) "

        val CREATE_TAULA_UBICACIONS =
            "CREATE TABLE " + ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS +
                    "(" + ProductesContract.Companion.Entrada.COL_ID_UBICACIONS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS + " TEXT, " +
                    ProductesContract.Companion.Entrada.COL_DESCUBICACIONS + " TEXT) "
        val INSERT_UBICACIO = "INSERT INTO " + ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS +
                "(" + ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS + "," +
                ProductesContract.Companion.Entrada.COL_DESCUBICACIONS + ")" +
                "SELECT 'Ubicació per defecte', 'Ubicació per defecte'"



        val REMOVE_TAULA_PRODUCTES = "DROP TABLE IF EXISTS " + ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES
        val REMOVE_TAULA_REBOST = "DROP TABLE IF EXISTS " + ProductesContract.Companion.Entrada.NOM_TAULA_REBOST
        val REMOVE_TAULA_COMPRAR = "DROP TABLE IF EXISTS " + ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR
        val REMOVE_TAULA_UBICACIONS = "DROP TABLE IF EXISTS " + ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(CREATE_TAULA_PRODUCTES)
        p0?.execSQL(CREATE_TAULA_REBOST)
        p0?.execSQL(CREATE_TAULA_COMPRAR)
        p0?.execSQL(CREATE_TAULA_UBICACIONS)
        p0?.execSQL(INSERT_UBICACIO)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(REMOVE_TAULA_PRODUCTES)
        p0?.execSQL(REMOVE_TAULA_REBOST)
        p0?.execSQL(REMOVE_TAULA_COMPRAR)
        p0?.execSQL(REMOVE_TAULA_UBICACIONS)

        onCreate(p0)
    }
}