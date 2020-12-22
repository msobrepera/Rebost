package com.ymest.rebost.sqlite.old

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.ymest.rebost.json.Ubicacio

class UbicacionsCrud(context: Context) {
    private var helper: DataBaseHelperOld

    init{
        helper = DataBaseHelperOld(context)
    }

    fun addUbicacio(item: Ubicacio){
        val db = helper.writableDatabase


        val values = ContentValues()
        values.put(ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS, item.nomubicacio)
        values.put(ProductesContract.Companion.Entrada.COL_DESCUBICACIONS, item.descubicacio)

        val newRowId = db.insert(ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS, null, values)

        db.close()
    }

    fun getAllUbicacions():ArrayList<Ubicacio>{
        val items:ArrayList<Ubicacio> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_UBICACIONS,
            ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS,
            ProductesContract.Companion.Entrada.COL_DESCUBICACIONS
        )

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Ubicacio(
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_UBICACIONS)),
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS)),
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DESCUBICACIONS))))
        }
        db.close()
        return items
    }

    fun getUbicacio(id:Int):Ubicacio{
        val item:Ubicacio
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID_UBICACIONS,
            ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS,
            ProductesContract.Companion.Entrada.COL_DESCUBICACIONS
        )
        val seleccion = ProductesContract.Companion.Entrada.COL_ID_UBICACIONS + "= ?"

        val c: Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS, columnas, seleccion, arrayOf(id.toString()), null, null, null)
        c.moveToFirst()
        item = Ubicacio(
            c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID_UBICACIONS)),
            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS)),
            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_DESCUBICACIONS)))

        db.close()
        return item
    }

    fun updateUbicacio(item:Ubicacio){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(ProductesContract.Companion.Entrada.COL_NOM_UBICACIONS, item.nomubicacio)
        values.put(ProductesContract.Companion.Entrada.COL_DESCUBICACIONS, item.descubicacio)

        db.update(ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS, values, "id=?", arrayOf(item.id.toString()))

        db.close()
    }

    fun deleteUbicacio(id: Int){
        val db = helper.writableDatabase
        Log.d("IDDELETE", id.toString())
        db.delete(ProductesContract.Companion.Entrada.NOM_TAULA_UBICACIONS,"id=?", arrayOf(id.toString()))

        db.close()
    }


}