package com.ymest.rebost.sqlite.old

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.ymest.rebost.json.Producto

class ProductesCrud(context: Context) {
    private var helper: DataBaseHelperOld

    init{
        helper = DataBaseHelperOld(context)
    }

    fun addProducte(item: Producto){
        val db = helper.writableDatabase

        val values = ContentValues()
        values.put(ProductesContract.Companion.Entrada.COL_CODI_BARRES, item.code)
        values.put(ProductesContract.Companion.Entrada.COL_NOM, item.product?.productName)
        values.put(ProductesContract.Companion.Entrada.COL_NOM_ES, item.product?.productNameEs)
        values.put(ProductesContract.Companion.Entrada.COL_MARCA, item.product?.brands)
        values.put(ProductesContract.Companion.Entrada.COL_STORE, item.product?.stores)
        values.put(ProductesContract.Companion.Entrada.COL_IMG_URL, item.product?.imageUrl)
        values.put(ProductesContract.Companion.Entrada.COL_STATUS, item.status)
        values.put(ProductesContract.Companion.Entrada.COL_QUANTITAT, item.product?.quantity)

        val newRowId = db.insert(ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, null, values)

        db.close()
    }

    /*fun getProductes(): ArrayList<Producto>{
        val items:ArrayList<Producto> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID,
            ProductesContract.Companion.Entrada.COL_NOM,
            ProductesContract.Companion.Entrada.COL_NOM_ES,
            ProductesContract.Companion.Entrada.COL_MARCA,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES,
            ProductesContract.Companion.Entrada.COL_IMG_URL,
            ProductesContract.Companion.Entrada.COL_STORE,
            ProductesContract.Companion.Entrada.COL_STATUS,
            ProductesContract.Companion.Entrada.COL_QUANTITAT)

        val c:Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Producto(
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES)),
                    Product(
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                        arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA))),
                        "ES",
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM)),

                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_IMG_URL)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM_ES)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE)),
                        arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE))),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT))),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STATUS)),
                    "OK"))
        }
        db.close()
        return items
    }*/

    /*fun getProducte(codi:String): Producto{
        lateinit var producte: Producto
        val db = helper.readableDatabase
        val columnas = arrayOf(
            ProductesContract.Companion.Entrada.COL_ID,
            ProductesContract.Companion.Entrada.COL_NOM,
            ProductesContract.Companion.Entrada.COL_NOM_ES,
            ProductesContract.Companion.Entrada.COL_MARCA,
            ProductesContract.Companion.Entrada.COL_CODI_BARRES,
            ProductesContract.Companion.Entrada.COL_IMG_URL,
            ProductesContract.Companion.Entrada.COL_STORE,
            ProductesContract.Companion.Entrada.COL_STATUS,
            ProductesContract.Companion.Entrada.COL_QUANTITAT)
        val where = ProductesContract.Companion.Entrada.COL_CODI_BARRES + " =? "
        val args = codi

        val c:Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, columnas, where, arrayOf(args), null, null, null)

        while(c.moveToNext()){
            producte = Producto(
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES)),
                        Product(
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                            arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA))),
                            "ES",
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM)),
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID)),
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_IMG_URL)),
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM_ES)),
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE)),
                            arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE))),
                            c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT))),
                        c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STATUS)),
                    "OK")
        }
        db.close()
        return producte
    }
*/
   /* fun getProductesRebost(): ArrayList<Producto>{
        val items:ArrayList<Producto> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase

        val sqlQuery = "SELECT " +
                ProductesContract.Companion.Entrada.NOM_TAULA_REBOST + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_REBOST + "." + ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_ID + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_NOM + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_NOM_ES + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_MARCA + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_CODI_BARRES + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_IMG_URL + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_STORE + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_STATUS + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_QUANTITAT +
                " FROM " + ProductesContract.Companion.Entrada.NOM_TAULA_REBOST +
                " LEFT JOIN " + ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES + " ON " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES + " = " +
                ProductesContract.Companion.Entrada.NOM_TAULA_REBOST + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES_REBOST  +
                " WHERE " + ProductesContract.Companion.Entrada.NOM_TAULA_REBOST + "." + ProductesContract.Companion.Entrada.COL_CONSUMIT_REBOST + " = 0 " +
                " GROUP BY " + ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES


        val c:Cursor = db.rawQuery(sqlQuery, null)
       *//* val c:Cursor = db.rawQuery("SELECT productes.id, productes.codibarres, productes.nom, productes.marca, productes.img, productes.status, " +
                "productes.store, productes.quantity from productes INNER JOIN rebost ON rebost.codibarres = productes.codibarres GROUP BY productes.codibarres", null)*//*

        //val c:Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Producto(
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES)),
                    Product(
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                        arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA))),
                        "ES",
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_IMG_URL)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM_ES)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE)),
                        arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE))),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT))),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STATUS)),
                    "OK"))
        }
        db.close()
        return items
    }*/

    /*fun getProductesComprar(): ArrayList<Producto>{
        val items:ArrayList<Producto> = ArrayList()
        //Abrir base de datos para leer
        val db = helper.readableDatabase

        val sqlQuery = "SELECT " +
                ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_ID + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_NOM + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_NOM_ES + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_MARCA + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_CODI_BARRES + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_IMG_URL + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_STORE + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_STATUS + ", " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES +"."+ ProductesContract.Companion.Entrada.COL_QUANTITAT +
                " FROM " + ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR +
                " LEFT JOIN " + ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES + " ON " +
                ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES + " = " +
                ProductesContract.Companion.Entrada.NOM_TAULA_COMPRAR + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES_COMPRAR  +
                " GROUP BY " + ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES + "." + ProductesContract.Companion.Entrada.COL_CODI_BARRES


        val c:Cursor = db.rawQuery(sqlQuery, null)
        *//* val c:Cursor = db.rawQuery("SELECT productes.id, productes.codibarres, productes.nom, productes.marca, productes.img, productes.status, " +
                 "productes.store, productes.quantity from productes INNER JOIN rebost ON rebost.codibarres = productes.codibarres GROUP BY productes.codibarres", null)*//*

        //val c:Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, columnas, null, null, null, null, null)

        while(c.moveToNext()){
            items.add(
                Producto(
                    c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_CODI_BARRES)),
                    Product(
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                        arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA))),
                        "ES",
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_ID)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_IMG_URL)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_MARCA)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_NOM_ES)),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE)),
                        arrayListOf(c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STORE))),
                        c.getString(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_QUANTITAT))),
                    c.getInt(c.getColumnIndexOrThrow(ProductesContract.Companion.Entrada.COL_STATUS)),
                    "OK"))
        }
        db.close()
        return items
    }*/

    fun existeProducto(codibarres: String):Boolean{
        val items:ArrayList<Producto> = ArrayList()
        var existe: Boolean = false
        //Abrir base de datos para leer
        val db = helper.readableDatabase
        val columnas = arrayOf(ProductesContract.Companion.Entrada.COL_CODI_BARRES)
        val seleccion: String = ProductesContract.Companion.Entrada.COL_CODI_BARRES + "= ?"

        val c:Cursor = db.query(ProductesContract.Companion.Entrada.NOM_TAULA_PRODUCTES, columnas, seleccion, arrayOf(codibarres), null, null, null)
        existe = c.moveToFirst()
        db.close()
        return existe

    }

    fun deleteproducte(id:String){
        val db = helper.writableDatabase
        db.close()
    }
}