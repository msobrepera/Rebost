package com.ymest.rebost.sqlite.old

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ymest.rebost.json.Rebost
import kotlin.collections.ArrayList

class RebostCrud(context: Context) {
    private var helper: DataBaseHelperOld

    init{
        helper = DataBaseHelperOld(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addProducteRebost(item: Rebost){
        val db = helper.writableDatabase


        val values = ContentValues()
        values.put(ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST, item.codibarres)
        values.put(ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST, item.datacaducitat)
        values.put(ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST, item.quantitat)
        values.put(ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST, 0)
        values.put(ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST, item.idubicacio)
        values.put(ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST, item.dataalta)

        val newRowId = db.insert(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, null, values)

        db.close()
    }

    fun getProductesRebost():ArrayList<Rebost>{
        val items:ArrayList<Rebost> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_REBOST,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST,
            ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST
        )

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Rebost(
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_REBOST)),
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST))))
        }
        db.close()
        return items
    }

    fun getProducteRebost(codibarres: String):ArrayList<Rebost>{
        val items:ArrayList<Rebost> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_REBOST,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST,
            ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST
        )

        val where = ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + " =? "
        val args = arrayOf(codibarres)

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, columnas, where, args, null, null, null)

        while(c.moveToNext()){
            items.add(
                Rebost(
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_REBOST)),
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST))))
        }
        db.close()
        return items
    }

    fun getProductesRebostNoConsumits():ArrayList<Rebost>{
        val items:ArrayList<Rebost> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_REBOST,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST,
            ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST
        )

        val where = ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST + " =? "
        val args = arrayOf(0).toString()

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, columnas, where, arrayOf(args), null, null, null)

        while(c.moveToNext()){
            items.add(
                Rebost(
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_REBOST)),
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST)),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST)),
                    c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST))))
        }
        db.close()
        return items
    }


    fun getLlistaCaducitats(codi: String): ArrayList<Rebost>{
        val items:ArrayList<Rebost> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_REBOST,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST,
            ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST,
            ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST,
            ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST
        )
        val where = ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + " =? "
        val args = codi
        val order = ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST + " ASC "

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, columnas, where, arrayOf(args), null, null, order)

            while (c.moveToNext()) {
                items.add(
                    Rebost(
                        c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_REBOST)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST)),
                        c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST)),
                        c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST)),
                        c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST)),
                        c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_UBICACIO_REBOST)),
                        c.getLong(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DATA_ALTA_REBOST))
                    )
                )
            }
        db.close()
        return items
    }

    fun getPrimeraDataCaducitat(codibarres: String?):Long{
        val db = helper.readableDatabase
        val columnas = arrayOf(ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST)
        val seleccion = ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + " = ? "
        var data: Long = 0

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, columnas, seleccion, arrayOf(codibarres), null, null, ProductesContract.Companion.Entrada.COL_DATA_CADUCITAT_REBOST + " ASC")
        c.moveToFirst()
        data = c.getLong(0)
        Log.d("DATAREBOST", data.toString() )

        db.close()
        return data
    }

    fun getSumaQuantitats(codibarres:String?):Int{
        Log.d("SUMREBOST", codibarres.toString())
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + ProductesContract.Companion.Entrada.COL_QUANTITAT_REBOST +")")
        val seleccion = ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + " = ? AND " + ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST + "= ?"
        val args = arrayOf(codibarres, "0")
        var suma = 0

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, columnas, seleccion, args, null, null, null)
        c.moveToFirst()
        suma = c.getInt(0)
        Log.d("SUMREBOST", suma.toString() )

        db.close()
        return suma
    }

    fun updateConsumitRebost(codibarres: String, consumit: Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST, consumit)

        val where = ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + "= ?"
        val args = arrayOf(codibarres)

        db.update(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, values, where, args)

        db.close()
    }

    fun deleterebost(codibarres: String){
        var db = helper.writableDatabase
        var where = ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + "=?"
        db.delete(ProductesContract.Companion.Entrada.NOM_TAULA_REBOST, where, arrayOf(codibarres))
        db.close()
    }

}