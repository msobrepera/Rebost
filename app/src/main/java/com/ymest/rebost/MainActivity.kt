package com.ymest.rebost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ymest.rebost.ajustes.AjustesActivity
import com.ymest.rebost.categories.CategoriesActivity
import com.ymest.rebost.json.Producto
import com.ymest.rebost.productesdecategoria.ProductesDeCategoriaActivity
import com.ymest.rebost.scan.ScanActivity
import com.ymest.rebost.sqlite.old.ProductesCrud
import com.ymest.rebost.ubicacions.UbicacionsActivity
import com.ymest.rebost.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_productes_de_categoria.*

class MainActivity : AppCompatActivity() {
    lateinit var crud: ProductesCrud
    lateinit var productoNuevo: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBuscar.setOnClickListener {
            intent = Intent(this, ProductesDeCategoriaActivity::class.java)
            intent.putExtra("VEDE", Constants.MAINACT)
            intent.putExtra("STRINGABUSCAR", etBuscar.text.toString())
            startActivity(intent)
        }

        btnBuscarXMarcas.setOnClickListener{
            intent = Intent(applicationContext, CategoriesActivity::class.java)
            intent.putExtra("VEDE", Constants.MARQUES)
            startActivity(intent)
        }

        btnBuscarXCategorias.setOnClickListener {
            intent = Intent(applicationContext, CategoriesActivity::class.java)
            intent.putExtra("VEDE", Constants.CATEGORIES)
            startActivity(intent)
        }

        btnEscaneaMain.setOnClickListener{
            intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

        ivSettingsMain.setOnClickListener {
            intent = Intent(this, AjustesActivity::class.java)
            startActivity(intent)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.im_home ->{
                    Snackbar.make(etBuscar,"CLICK A: " + it.itemId, Snackbar.LENGTH_LONG).show()
                    //Snackbar.make(etBuscar, "Click en Home", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.im_buscar ->{
                    intent = Intent(this, ProductesDeCategoriaActivity::class.java)
                    intent.putExtra("VEDE", Constants.MAINACT)
                    intent.putExtra("STRINGABUSCAR", etBuscar.text.toString())
                    startActivity(intent)
                    true
                }
                R.id.im_escanea ->{
                    intent = Intent(this, ScanActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.im_mislistas ->{
                    intent = Intent(applicationContext, UbicacionsActivity::class.java)
                    intent.putExtra("VEDE", Constants.MISLISTAS)
                    startActivity(intent)
                    true
                }
                R.id.im_historial ->{
                    intent = Intent(this, ProductesDeCategoriaActivity::class.java)
                    intent.putExtra("VEDE", Constants.HISTORIAL)
                    startActivity(intent)
                    true
                }
                else -> {
                    Snackbar.make(etBuscar,"CLICK A OTRO: " + it.itemId, Snackbar.LENGTH_LONG).show()
                    true
                }

            }

        }
    }

    override fun onBackPressed() {
        mostraAlertaFinalitzarAplicacio()
    }

    private fun mostraAlertaFinalitzarAplicacio() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titol_sortir_aplicacio))
        builder.setIcon(R.drawable.ic_senal_de_precaucion_orange_24)
        builder.setMessage("Seguro que quieres salir de la aplicación")
        builder.setPositiveButton("ME QUEDO!"){ dialog, which ->
            dialog.dismiss()
        }
        builder.setNegativeButton("SALIR"){dialog, which ->
            super.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    fun cargarProducto(cb:String){
        /*val queue = Volley.newRequestQueue(this)
        val url = "https://es.openfoodfacts.org/api/v0/product/" + cb + ".json"
        var producto: Producto

        val request = StringRequest(Request.Method.GET, url, Response.Listener<String> {
            response ->
            tvNombreProducto.text = "Response is: ${response.substring(0, 500)}"
            var gson = Gson()
            producto = gson.fromJson(response, Producto::class.java)
            etBuscar.setText(producto.product?.id)
            tvNombreProducto.text = producto.product?.productNameEs
        },
        Response.ErrorListener { tvNombreProducto.text = "No ha funcionat" })
        queue.add(request)*/

    }
}