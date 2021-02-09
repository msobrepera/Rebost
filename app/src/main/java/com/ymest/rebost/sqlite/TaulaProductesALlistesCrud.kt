package com.ymest.rebost.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ymest.rebost.json.*
import com.ymest.rebost.sqlite.old.ProductesContract
import com.ymest.rebost.utils.Funcions

class TaulaProductesALlistesCrud(context: Context) {
    private var helper: DataBaseHelper

    init {
        helper = DataBaseHelper(context)
    }

    fun addProducteALlista(item: ProducteALlista) {
        val db = helper.writableDatabase

        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES, item.code)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA, item.idLlista)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT, item.quantitat)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD, item.dataCaducitat)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO, item.idUbicacio)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT, item.comprat)

        val newRowId = db.insert(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, null, values)
        db.close()
    }

   fun getProductesLlista(idLlista: Int):ArrayList<ProducteALlista>{
        var colleccioProductes: ArrayList<ProducteALlista> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT)

       val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? "

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, arrayOf(idLlista.toString()), null, null, null)
        while(c.moveToNext()){
            colleccioProductes.add(
                ProducteALlista(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT)),
                    c.getLong(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT))
                )
            )
        }
       db.close()
        return colleccioProductes
    }

    fun getProductesdeUbicacio(idUbic: Int):ArrayList<ProducteALlista>{
        var colleccioProductes: ArrayList<ProducteALlista> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT)

        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO + " = ? "

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, arrayOf(idUbic.toString()), null, null, null)
        while(c.moveToNext()){
            colleccioProductes.add(
                ProducteALlista(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT)),
                    c.getLong(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT))
                )
            )
        }
        db.close()
        return colleccioProductes
    }

    fun getProductesAllLlistes():ArrayList<ProducteALlista>{
        var colleccioProductes: ArrayList<ProducteALlista> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT)

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, null, null, null, null, null)
        while(c.moveToNext()){
            colleccioProductes.add(
                ProducteALlista(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT)),
                    c.getLong(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT))
                )
            )
        }
        db.close()
        return colleccioProductes
    }

    fun getCaducats(data:Long): ArrayList<ProducteALlista>{
        var colleccioProductes: ArrayList<ProducteALlista> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT)

        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " < ?  AND " +
                           Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " != ? "
        val args = arrayOf(data.toString(), "1")
        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        while(c.moveToNext()){
            colleccioProductes.add(
                ProducteALlista(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT)),
                    c.getLong(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT))
                )
            )
        }
        db.close()
        return colleccioProductes
    }



    fun existeProductoenLlista(idLlista: Int?, codiBarres: String):Boolean{
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)
        val seleccion: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? "
        val args: Array<String> = arrayOf(idLlista.toString(), codiBarres)


        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, seleccion, args, null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe
    }

    fun existeProductoconFechaIgual(idLlista: Int?, codiBarres: String, fecha: Long):Boolean{
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)
        val seleccion: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ?  AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " = ? "
        val args: Array<String> = arrayOf(idLlista.toString(), codiBarres, fecha.toString())
        //Log.d("COMPARANDOFECHAS", "FECHA RECIBIDA: " + fecha.toString())

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, seleccion, args, null, null, null)
        existe = c.moveToFirst()
        //Log.d("COMPARANDOFECHAS", "FECHA EN DB: " + c.getLong(0))
        db.close()
        return existe
    }

    fun getPrimeraDataCaducitat(codibarres: String?):Long{
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)
        val seleccion = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ?  AND " +
                               Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " != ? "
        val args = arrayOf(codibarres, 0.toString())
        var data: Long = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, seleccion, args, null, null, Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " ASC")
        if(c.moveToFirst()) data = c.getLong(0)
        else Log.d("DATACADLLISTA", "no se encuentra")
        Log.d("DATAREBOST", data.toString() )

        db.close()
        return data
    }

    fun getNumProductosXLista(idLlista:Int): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("COUNT(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID +")")
        val seleccion = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? "
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, seleccion, arrayOf(idLlista.toString()), null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getNumProductosUnicosXLista(idLlista:Int): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("COUNT(DISTINCT " + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES +")")
        val seleccion = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? "
        //var group = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, seleccion, arrayOf(idLlista.toString()), null, null, null)
        Log.d("QUERYNUM", c.toString())
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getNumProductosUnicosXUbicacion(idUbic: Int): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("COUNT(DISTINCT " + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES +")")
        val seleccion = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO + " = ? "
        //var group = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, seleccion, arrayOf(idUbic.toString()), null, null, null)
        Log.d("QUERYNUM", c.toString())
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getCaducatsUnics(data:Long): Int{
        var total = 0
        val db = helper.readableDatabase
        val columnas = arrayOf("COUNT(DISTINCT " + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES +")")
        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " < ?  AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " != ? "
        val args = arrayOf(data.toString(), "1")

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }
    fun getCantidadProductoMismaFecha(idLista:Int, cb:String, data:Long): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT +")")
        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " = ? "
        val args = arrayOf(idLista.toString(), cb, data.toString())
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getCantidadDeUnProductoCaducado(cb:String, data:Long): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT +")")
        val where =
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " < ? "
        val args = arrayOf(cb, data.toString())
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getCantidadProducto(idLista:Int?, cb:String): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT +")")
        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? "

        val args = arrayOf(idLista.toString(), cb)
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getCantidadProductoXUbicacion(idUbic:Int?, cb:String): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT +")")
        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? "

        val args = arrayOf(idUbic.toString(), cb)
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }

    fun getLlistatDatesCaducitat(code: String?): ArrayList<ProducteALlista>{
        val llistatDatesCad: ArrayList<ProducteALlista> = arrayListOf()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)

        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ?  AND " +
                           Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " != ? "

        val args = arrayOf(code, 0.toString())
        val order = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " ASC "

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, order)
        while(c.moveToNext()){
            llistatDatesCad.add(
                ProducteALlista(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT)),
                    c.getLong(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)),
                )
            )
        }
        db.close()
        return llistatDatesCad
    }

    fun getLlistatDatesCaducitatXLlista(code: String?, idLlista: Int): ArrayList<ProducteALlista>{
        val llistatDatesCad: ArrayList<ProducteALlista> = arrayListOf()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD,
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)

        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ?  AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " != ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? "
        val args = arrayOf(code, 0.toString(), idLlista.toString())
        val order = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " ASC "

        val c:Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, order)
        while(c.moveToNext()){
            llistatDatesCad.add(
                ProducteALlista(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT)),
                    c.getLong(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD)),
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO)),
                )
            )
        }
        db.close()
        return llistatDatesCad
    }

    fun updateCantidadMismaFecha(q: Int, idLlista: Int, data:Long, cb:String, idUbic:Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT, q)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO, idUbic)

        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ?  AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD + " = ? "

        val args: Array<String> = arrayOf(idLlista.toString(), cb, data.toString())
        db.update(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, values, where, args)
        db.close()
    }

    fun updateCantidad(q: Int, idLlista: Int, cb:String, idUbic: Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT, q)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO, idUbic)
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? "
        val args: Array<String> = arrayOf(idLlista.toString(), cb)
        db.update(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, values, where, args)
        db.close()
    }

    fun updateUbicacion(ubiant: Int, ubinew: Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO, ubinew)
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO + " = ? "
        val args: Array<String> = arrayOf(ubiant.toString())
        db.update(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, values, where, args)
        db.close()
    }

    fun updateComprat(cb: String, comprat:Int){
        var idLlista = 1
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT, comprat)
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? "
        val args: Array<String> = arrayOf(idLlista.toString(), cb)
        db.update(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, values, where, args)
        db.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCanviLlista(idLlistaAntiga:Int, idLlistaNova:Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA, idLlistaNova)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT, 0)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_UBICACIO, 1)
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_DATA_CAD, Funcions.obtenirDataActualMillis())
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT + " = ? "
        val args: Array<String> = arrayOf(idLlistaAntiga.toString(), 1.toString())
        db.update(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, values, where, args)
        db.close()
    }

    fun getSiEstaComprat(cb: String):Int{
        var idLlista = 1
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT)
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? "
        val args: Array<String> = arrayOf(idLlista.toString(), cb)
        var comprat = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        comprat = c.getInt(0)
        db.close()
        return comprat
    }

    /*fun updateLlista(id: Int?, nomLlista: String?){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TLlistes.COL_LLISTES_NOM, nomLlista)
        val where = Column.Companion.TLlistes.COL_LLISTES_ID + "= ?"
        val args = arrayOf(id.toString())
        db.update(Column.Companion.TPersonalitzada.T_PERSONALITZADA, values, where, args)
        db.close()
    }*/

    fun deleteProducteaLlista(id:Int){
        var db = helper.writableDatabase
        var where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID + " =? "
        db.delete(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, where, arrayOf(id.toString()))
        db.close()
    }

    fun deleteUnProducteDeUnaLlista(cb: String, idLlista: Int){
        var db = helper.writableDatabase
        var where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_CODI_BARRES + " = ? AND " +
                           Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? "
        val args = arrayOf(cb, idLlista.toString())
        db.delete(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, where, args)
        db.close()
    }

    fun deleteProductesDeUnaLlista(idLlista:Int){
        var db = helper.writableDatabase
        var where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " =? "
        db.delete(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, where, arrayOf(idLlista.toString()))
        db.close()
    }

    fun deleteProductesComprats(){
        var db = helper.writableDatabase
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID_LLISTA + " = ? AND " +
                Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_COMPRAT + " = ? "
        val args: Array<String> = arrayOf(1.toString(), 1.toString())
        db.delete(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, where, args)
        db.close()
    }

    fun deleteProductesDeAllLlista(){
        var db = helper.writableDatabase
        db.delete(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, null, null)
        db.close()
    }

    fun updateCantidadMenosUna(id:Int){
        var cantidadInicial = getCantidadRow(id)
        var cantidadFinal = cantidadInicial - 1
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT, cantidadFinal)
        val where: String = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID + " = ? "
        val args: Array<String> = arrayOf(id.toString())

        db.update(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, values, where, args)
        db.close()
    }

    fun getCantidadRow(id: Int): Int{
        val db = helper.readableDatabase
        val columnas = arrayOf("SUM(" + Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_QUANTITAT +")")
        val where = Column.Companion.TProductesALlista.COL_PRODUCTESALLISTA_ID + " = ? "

        val args = arrayOf(id.toString())
        var total = 0

        val c: Cursor = db.query(Column.Companion.TProductesALlista.T_PRODUCTESALLISTA, columnas, where, args, null, null, null)
        c.moveToFirst()
        total = c.getInt(0)
        db.close()
        return total
    }
}