package com.ymest.rebost.sqlite.old

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.ymest.rebost.json.Comprar

class ComprarCrud(context: Context) {
    private var helper: DataBaseHelperOld

    init{
        helper = DataBaseHelperOld(context)
    }

    fun addProducteComprar(item: Comprar){
        val db = helper.writableDatabase

        val values = ContentValues()
        values.put(ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR, item.codibarres)
        values.put(ProductesContract.Companion.Entrada.COL_QUANTITAT_COMPRAR, item.quantitat)
        values.put(ProductesContract.Companion.Entrada.COL_DATA_ALTA_COMPRAR, item.dataAlta)
        values.put(ProductesContract.Companion.Entrada.COL_COMPRAT_COMPRAR, item.comprat)

        val newRowId = db.insert(ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR, null, values)

        db.close()
    }

    fun getProductesComprar():ArrayList<Comprar>{
        val items:ArrayList<Comprar> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_COMPRAR,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR,
            ProductesContract.Companion.Entrada.COL_QUANTITAT_COMPRAR,
            ProductesContract.Companion.Entrada.COL_DATA_ALTA_COMPRAR,
            ProductesContract.Companion.Entrada.COL_COMPRAT_COMPRAR
        )

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Comprar(
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_COMPRAR)),
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT_COMPRAR)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_ALTA_COMPRAR)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_COMPRAT_COMPRAR))))
        }
        db.close()
        return items
    }

    fun getSumaQuantitats(codibarres:String?):Int{
        Log.d("SUMCOMPRAR", codibarres.toString())

        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + ProductesContract.Companion.Entrada.COL_QUANTITAT_COMPRAR +")")
        val seleccion = ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR + "= ?"
        var suma = 0

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR, columnas, seleccion, arrayOf(codibarres), null, null, null)
        c.moveToFirst()
        suma = c.getInt(0)
        Log.d("SUMCOMPRAR", suma.toString() )

        db.close()
        return suma
    }

    fun deletecomprar(codibarres: String){
        var db = helper.writableDatabase
        var where = ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR + "=?"
        db.delete(ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR, where, arrayOf(codibarres))
        db.close()
    }

}