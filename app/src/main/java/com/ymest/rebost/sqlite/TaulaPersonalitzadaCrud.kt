package com.ymest.rebost.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ymest.rebost.json.Personalitzat
import com.ymest.rebost.json.Product
import com.ymest.rebost.json.Producto
import com.ymest.rebost.sqlite.old.ProductesContract

class TaulaPersonalitzadaCrud(context: Context) {
    private var helper: DataBaseHelper

    init {
        helper = DataBaseHelper(context)
    }

    fun addProductePersonalitzat(item: Personalitzat) {
        val db = helper.writableDatabase

        val values = ContentValues()
        values.put(Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE, item.code)
        values.put(Column.Companion.TPersonalitzada.COL_DATA_AFEGIT, item.dataAfegit)
        values.put(Column.Companion.TPersonalitzada.COL_DATA_VIST, item.dataVist)
        values.put(Column.Companion.TPersonalitzada.COL_FAVORIT, item.favorit)
        values.put(Column.Companion.TPersonalitzada.COL_GUST, item.gust)
        values.put(Column.Companion.TPersonalitzada.COL_PUNTUACIO, item.puntuacio)


        val newRowId = db.insert(Column.Companion.TPersonalitzada.T_PERSONALITZADA, null, values)
        db.close()
    }

   fun getProductePersonalitzat(codibarres: String?): Personalitzat{
       lateinit var productePersonalitzat: Personalitzat
       val db = helper.readableDatabase
       val columnas = arrayOf(
           Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE,
           Column.Companion.TPersonalitzada.COL_DATA_AFEGIT,
           Column.Companion.TPersonalitzada.COL_DATA_VIST,
           Column.Companion.TPersonalitzada.COL_FAVORIT,
           Column.Companion.TPersonalitzada.COL_GUST,
           Column.Companion.TPersonalitzada.COL_PUNTUACIO)
       val where = Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + " =? "
       val args = codibarres

       val c:Cursor = db.query(Column.Companion.TPersonalitzada.T_PERSONALITZADA, columnas, where, arrayOf(args), null, null, null)
       while(c.moveToNext()){
           productePersonalitzat = Personalitzat(
               c.getString(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE)),
               c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_DATA_AFEGIT)),
               c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_DATA_VIST)),
               c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_FAVORIT)),
               c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_GUST)),
               c.getDouble(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_PUNTUACIO)),
           )
       }
       return productePersonalitzat
   }

    fun getAllProductePersonalitzat(): ArrayList<Personalitzat>{
        var productePersonalitzat: ArrayList<Personalitzat> = ArrayList()
        val db = helper.readableDatabase
        val columnas = arrayOf(
            Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE,
            Column.Companion.TPersonalitzada.COL_DATA_AFEGIT,
            Column.Companion.TPersonalitzada.COL_DATA_VIST,
            Column.Companion.TPersonalitzada.COL_FAVORIT,
            Column.Companion.TPersonalitzada.COL_GUST,
            Column.Companion.TPersonalitzada.COL_PUNTUACIO)

        val c:Cursor = db.query(Column.Companion.TPersonalitzada.T_PERSONALITZADA, columnas, null, null, null, null, null)
        while(c.moveToNext()){
            productePersonalitzat.add(Personalitzat(
                c.getString(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE)),
                c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_DATA_AFEGIT)),
                c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_DATA_VIST)),
                c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_FAVORIT)),
                c.getInt(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_GUST)),
                c.getDouble(c.getColumnIndexOrThrow(Column.Companion.TPersonalitzada.COL_PUNTUACIO)),
            ))
        }
        return productePersonalitzat
    }

    fun existeProductoPersonalitzat(codibarres: String?):Boolean{
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE)
        val seleccion: String = Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + " = ? "

        val c:Cursor = db.query(Column.Companion.TPersonalitzada.T_PERSONALITZADA, columnas, seleccion, arrayOf(codibarres), null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe
    }

    fun updateFavorito(codibarres: String?, fav: Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TPersonalitzada.COL_FAVORIT, fav)
        val where = Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + "= ?"
        val args = arrayOf(codibarres)
        db.update(Column.Companion.TPersonalitzada.T_PERSONALITZADA, values, where, args)
        db.close()
    }

    fun updateSabor(codibarres: String?, gust: Int){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TPersonalitzada.COL_GUST, gust)
        val where = Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + "= ?"
        val args = arrayOf(codibarres)
        db.update(Column.Companion.TPersonalitzada.T_PERSONALITZADA, values, where, args)
        db.close()
    }

    fun updatePuntuacio(codibarres: String?, puntuacio: Float){
        val db = helper.writableDatabase
        val values = ContentValues()
        values.put(Column.Companion.TPersonalitzada.COL_PUNTUACIO, puntuacio)
        val where = Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + "= ?"
        val args = arrayOf(codibarres)
        db.update(Column.Companion.TPersonalitzada.T_PERSONALITZADA, values, where, args)
        db.close()
    }

    fun deleteProductePersonalitzat(codibarres: String){
        var db = helper.writableDatabase
        var where = Column.Companion.TPersonalitzada.COL_PERSONALITZADA_CODE + " =? "
        db.delete(Column.Companion.TPersonalitzada.T_PERSONALITZADA, where, arrayOf(codibarres))
        db.close()
    }

    fun deleteAllProductePersonalitzat(){
        var db = helper.writableDatabase
        db.delete(Column.Companion.TPersonalitzada.T_PERSONALITZADA, null, null)
        db.close()
    }
}