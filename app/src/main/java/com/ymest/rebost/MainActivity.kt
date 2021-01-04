package com.ymest.rebost

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ymest.rebost.ajustes.AjustesActivity
import com.ymest.rebost.categories.CategoriesActivity
import com.ymest.rebost.detallproductes.ProductoDetalleActivity
import com.ymest.rebost.detallproductesbuscats.DetallProdBuscatsActivity
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
    val CHANNEL_ID: String = "1001"
    val notificationId: Int = 100

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
        createNotificationChannel()

        val intent = Intent(this, DetallProdBuscatsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }
        intent.putExtra("ID", "3560070820306")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shopping_basket_24px_black)
            .setContentTitle("Titol de la notificació")
            .setContentText("Tens un producte a punt de caducar!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNEL_PROVA_NAME"//getString(R.string.channel_name)
            val descriptionText = "CHANNEL_PROVA_DESCRIPTION"//getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
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