package com.ymest.rebost.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.ymest.rebost.json.Ubicacio

class TaulaUbicacionsCrud(context: Context) {
    private var helper: DataBaseHelper

    init{
        helper = DataBaseHelper(context)
    }

    fun addUbicacio(item: Ubicacio){
        val db = helper.writableDatabase


        val values = ContentValues()
        values.put(Column.Companion.TUbicacions.COL_NOM_UBICACIONS, item.nomubicacio)
        values.put(Column.Companion.TUbicacions.COL_DESCUBICACIONS, item.descubicacio)

        val newRowId = db.insert(Column.Companion.TUbicacions.T_UBICACIONS, null, values)

        db.close()
    }

    fun getAllUbicacions():ArrayList<Ubicacio>{
        val items:ArrayList<Ubicacio> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TUbicacions.COL_ID_UBICACIONS,
            Column.Companion.TUbicacions.COL_NOM_UBICACIONS,
            Column.Companion.TUbicacions.COL_DESCUBICACIONS
        )

        val c: Cursor = db.query(Column.Companion.TUbicacions.T_UBICACIONS, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Ubicacio(
                    c.getInt(c.getColumnIndexOrThrow(Column.Companion.TUbicacions .COL_ID_UBICACIONS)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TUbicacions.COL_NOM_UBICACIONS)),
                    c.getString(c.getColumnIndexOrThrow(Column.Companion.TUbicacions.COL_DESCUBICACIONS))))
        }
        db.close()
        return items
    }

    fun existeUbicacion(nomUbic:String):Boolean{
        var existe = false
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TUbicacions.COL_NOM_UBICACIONS)
        val seleccion: String = Column.Companion.TUbicacions.COL_NOM_UBICACIONS + " = ? "

        val c:Cursor = db.query(Column.Companion.TUbicacions.T_UBICACIONS, columnas, seleccion, arrayOf(nomUbic), null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe
    }

    fun getIdUbicacio(nomUbicacio: String): Int{
        var idUbicacio: Int = 0
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TUbicacions.COL_ID_UBICACIONS)
        val where = Column.Companion.TUbicacions.COL_NOM_UBICACIONS + " =? "
        val args = nomUbicacio

        val c:Cursor = db.query(Column.Companion.TUbicacions.T_UBICACIONS, columnas, where, arrayOf(args), null, null, null)
        if(c.moveToFirst()) idUbicacio = c.getInt(c.getColumnIndexOrThrow(Column.Companion.TUbicacions.COL_ID_UBICACIONS))
        else idUbicacio = 0
        db.close()
        return idUbicacio
    }

    fun getUbicacio(id:Int):Ubicacio{
        val item:Ubicacio
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TUbicacions.COL_ID_UBICACIONS,
            Column.Companion.TUbicacions.COL_NOM_UBICACIONS,
            Column.Companion.TUbicacions.COL_DESCUBICACIONS
        )
        val seleccion = Column.Companion.TUbicacions.COL_ID_UBICACIONS + "= ?"

        val c: Cursor = db.query(Column.Companion.TUbicacions.T_UBICACIONS, columnas, seleccion, arrayOf(id.toString()), null, null, null)
        c.moveToFirst()
        item = Ubicacio(
            c.getInt(c.getColumnIndexOrThrow(Column.Companion.TUbicacions.COL_ID_UBICACIONS)),
            c.getString(c.getColumnIndexOrThrow(Column.Companion.TUbicacions.COL_NOM_UBICACIONS)),
            c.getString(c.getColumnIndexOrThrow(Column.Companion.TUbicacions.COL_DESCUBICACIONS)))

        db.close()
        return item
    }

    fun updateUbicacio(item:Ubicacio){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TUbicacions.COL_NOM_UBICACIONS, item.nomubicacio)
        values.put(Column.Companion.TUbicacions.COL_DESCUBICACIONS, item.descubicacio)

        db.update(Column.Companion.TUbicacions.T_UBICACIONS, values, "id=?", arrayOf(item.id.toString()))

        db.close()
    }

    fun deleteUbicacio(id: Int){
        val db = helper.writableDatabase
        Log.d("IDDELETE", id.toString())
        db.delete(Column.Companion.TUbicacions.T_UBICACIONS,"id=?", arrayOf(id.toString()))

        db.close()
    }


}