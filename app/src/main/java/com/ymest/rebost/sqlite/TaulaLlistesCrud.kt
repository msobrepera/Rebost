package com.ymest.rebost.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.ymest.rebost.backup.CustomBackupAgent
import com.ymest.rebost.json.Llistes
import com.ymest.rebost.json.Personalitzat
import com.ymest.rebost.json.Product
import com.ymest.rebost.json.Producto
import com.ymest.rebost.sqlite.old.ProductesContract

class TaulaLlistesCrud(context: Context) {
    private var helper: DataBaseHelper

    init {
        helper = DataBaseHelper(context)
    }

    fun addLlista(id:Int, item: String, datacad:Int, gestionaCantidad:Int, gestionaUbicacion:Int) {
        val db = helper.writableDatabase

        val values = ContentValues()
        values.put(Column.Companion.TLlistes.COL_LLISTES_ID, id)
        values.put(Column.Companion.TLlistes.COL_LLISTES_NOM, item)
        values.put(Column.Companion.TLlistes.COL_DATA_CAD, datacad)
        values.put(Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD, gestionaCantidad)
        values.put(Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES, gestionaUbicacion)

        val newRowId = db.insert(Column.Companion.TLlistes.T_LLISTES, null, values)
        db.close()
        var backup: CustomBackupAgent = CustomBackupAgent()

    }

   fun getLlista(id: Int?): Llistes{
       lateinit var llistaSeleccionada: Llistes
       val db = helper.readableDatabase
       val columnas = arrayOf(
           Column.Companion.TLlistes.COL_LLISTES_ID,
           Column.Companion.TLlistes.COL_LLISTES_NOM,
           Column.Companion.TLlistes.COL_DATA_CAD,
           Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD,
           Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES)

       val where = Column.Companion.TLlistes.COL_LLISTES_ID + " =? "
       val args = id.toString()

       val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, where, arrayOf(args), null, null, null)
       while(c.moveToNext()){
           llistaSeleccionada = Llistes(c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_LLISTES_ID)),
                                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_LLISTES_NOM)),
                                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_DATA_CAD)),
                                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD)),
                                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES)))
       }
       db.close()
       return llistaSeleccionada

   }

    fun getIdLlista(nomLlista: String): Int{
        var idLlista: Int = 0
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TLlistes.COL_LLISTES_ID)
        val where = Column.Companion.TLlistes.COL_LLISTES_NOM + " =? "
        val args = nomLlista

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, where, arrayOf(args), null, null, null)
        if(c.moveToFirst()) idLlista = c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_LLISTES_ID))
        else idLlista = 0
        db.close()
        return idLlista
    }

    fun getNextID():Int{
        var nextID = 0
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TLlistes.COL_LLISTES_ID)
        val order = Column.Companion.TLlistes.COL_LLISTES_ID + " DESC"
        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, null, null, null, null, order)
        if(c.moveToFirst()) nextID = c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_LLISTES_ID))
        else nextID = 0
        nextID++
        db.close()
        return nextID
    }

    fun getAllLlista():ArrayList<Llistes>{
        var colleccioLlistes: ArrayList<Llistes> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TLlistes.COL_LLISTES_ID,
            Column.Companion.TLlistes.COL_LLISTES_NOM,
            Column.Companion.TLlistes.COL_DATA_CAD,
            Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD,
            Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES)

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, null,null, null, null, null)
        while(c.moveToNext()){
            colleccioLlistes.add(Llistes(c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_LLISTES_ID)),
                                        c.getString(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_LLISTES_NOM)),
                                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_DATA_CAD)),
                                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD)),
                                        c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES))))
        }
        db.close()
        return colleccioLlistes
    }



    fun existeLlista(nomLlista: String?):Boolean{
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TLlistes.COL_LLISTES_NOM)
        val seleccion: String = Column.Companion.TLlistes.COL_LLISTES_NOM + " = ? "

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, seleccion, arrayOf(nomLlista), null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe
    }

    fun existeOtraLlistaConMismoNombre(idLlista:String, nomLlista: String?):Boolean{
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TLlistes.COL_LLISTES_NOM)
        val seleccion: String = Column.Companion.TLlistes.COL_LLISTES_NOM + " = ?  AND " +
                                Column.Companion.TLlistes.COL_LLISTES_ID + " != ?"
        val args = arrayOf(nomLlista, idLlista)

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, seleccion, args, null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe
    }

    fun updateLlista(id: Int?, nomLlista: String?, datacad: Int, gestcant:Int, gestUbic: Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TLlistes.COL_LLISTES_NOM, nomLlista)
        values.put(Column.Companion.TLlistes.COL_DATA_CAD, datacad)
        values.put(Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD, gestcant)
        values.put(Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES, gestUbic)
        val where = Column.Companion.TLlistes.COL_LLISTES_ID + "= ?"
        val args = arrayOf(id.toString())
        db.update(Column.Companion.TLlistes.T_LLISTES, values, where, args)
        db.close()
    }

    fun gestionaCantidad(idLlista:Int?): Boolean{
        var gestiona: Boolean = false
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD)
        val seleccion: String = Column.Companion.TLlistes.COL_LLISTES_ID + " = ? "

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, seleccion, arrayOf(idLlista.toString()), null, null, null)
        c.moveToFirst()
        gestiona = c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_GESTIONA_CANTIDAD)) == 1
        db.close()
        return gestiona
    }

    fun gestionaFechas(idLlista:Int?): Boolean{
        var gestiona: Boolean = false
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TLlistes.COL_DATA_CAD)
        val seleccion: String = Column.Companion.TLlistes.COL_LLISTES_ID + " = ? "

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, seleccion, arrayOf(idLlista.toString()), null, null, null)
        c.moveToFirst()
        gestiona = c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_DATA_CAD)) == 1
        db.close()
        return gestiona
    }

    fun gestionaUbicaciones(idLlista:Int?): Boolean{
        var gestiona: Boolean = false
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES)
        val seleccion: String = Column.Companion.TLlistes.COL_LLISTES_ID + " = ? "

        val c:Cursor = db.query(Column.Companion.TLlistes.T_LLISTES, columnas, seleccion, arrayOf(idLlista.toString()), null, null, null)
        c.moveToFirst()
        gestiona = c.getInt(c.getColumnIndexOrThrow(Column.Companion.TLlistes.COL_GESTIONA_UBICACIONES)) == 1
        db.close()
        return gestiona
    }

    fun deleteLlista(id:Int){
        var db = helper.writableDatabase
        var where = Column.Companion.TLlistes.COL_LLISTES_ID + " =? "
        db.delete(Column.Companion.TLlistes.T_LLISTES, where, arrayOf(id.toString()))
        db.close()
    }

    fun deleteAllLlista(){
        var db = helper.writableDatabase
        db.delete(Column.Companion.TLlistes.T_LLISTES,null,null)
        db.close()
    }
}