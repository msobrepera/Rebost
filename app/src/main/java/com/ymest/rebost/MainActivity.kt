package com.ymest.rebost

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.squareup.picasso.Picasso
import com.ymest.rebost.ajustes.AjustesActivity
import com.ymest.rebost.ajustes.Backup
import com.ymest.rebost.ajustes.BackupActivity
import com.ymest.rebost.categories.CategoriesActivity
import com.ymest.rebost.detallproductesbuscats.DetallProdBuscatsActivity
import com.ymest.rebost.json.ProducteALlista
import com.ymest.rebost.json.Producto
import com.ymest.rebost.login.LoginActivity
import com.ymest.rebost.login.UserAccountActivity
import com.ymest.rebost.productesdecategoria.ProductesDeCategoriaActivity
import com.ymest.rebost.scan.ScanActivity
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesCrud
import com.ymest.rebost.sqlite.old.ProductesCrud
import com.ymest.rebost.ubicacions.UbicacionsActivity
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var firstTime: Boolean = true
    lateinit var sharedPref: SharedPreferences
    var LOGTAG: String = "MainActivity"
    lateinit var crud: ProductesCrud
    lateinit var productoNuevo: Producto
    val CHANNEL_ID: String = "1001"
    val notificationId: Int = 100
    lateinit var crudLlistaProductes: TaulaProductesALlistesCrud
    lateinit var productesCaducats: ArrayList<ProducteALlista>
    lateinit var primerCaducat: ProducteALlista
    private val STORAGE_REQUEST_CODE_IMPORT = 2
    private lateinit var storagePermission: Array<String>
    lateinit var backupFolder: File
    lateinit var backup: Backup

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initVars()

        if(EsPrimeraEjecucion()){
            MarcarComoNoPrimeraEjecucion()
            if(HayBackup()){
                MostrarAlertDialogHayBackup()
            }
        }
        btnStart.visibility = View.GONE
        btnStop.visibility = View.GONE
        /*btnStart.setOnClickListener {
            startService(Intent(applicationContext, MyService::class.java))
        }
        btnStop.setOnClickListener {
            stopService(Intent(applicationContext, MyService::class.java))
        }*/

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

        ivUserLogin.setOnClickListener {
            var mAuth = FirebaseAuth.getInstance()
            if(mAuth.currentUser == null){
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                intent = Intent(this, UserAccountActivity::class.java)
                startActivity(intent)
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.im_home -> {
                    Snackbar.make(etBuscar, "CLICK A: " + it.itemId, Snackbar.LENGTH_LONG).show()
                    //Snackbar.make(etBuscar, "Click en Home", Snackbar.LENGTH_SHORT).show()
                    true
                }
                R.id.im_buscar -> {
                    intent = Intent(this, ProductesDeCategoriaActivity::class.java)
                    intent.putExtra("VEDE", Constants.MAINACT)
                    intent.putExtra("STRINGABUSCAR", etBuscar.text.toString())
                    startActivity(intent)
                    true
                }
                R.id.im_escanea -> {
                    intent = Intent(this, ScanActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.im_mislistas -> {
                    intent = Intent(applicationContext, UbicacionsActivity::class.java)
                    intent.putExtra("VEDE", Constants.MISLISTAS)
                    startActivity(intent)
                    true
                }
                R.id.im_historial -> {
                    intent = Intent(this, ProductesDeCategoriaActivity::class.java)
                    intent.putExtra("VEDE", Constants.HISTORIAL)
                    startActivity(intent)
                    true
                }
                else -> {
                    Snackbar.make(etBuscar, "CLICK A OTRO: " + it.itemId, Snackbar.LENGTH_LONG).show()
                    true
                }

            }

        }
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(LOGTAG, "Token actualizado: $refreshedToken")



        compruebaSiHayUser()

        /*if (compruebaCaducados()){
            muestraNotifiacion()
        }*/


    }

    private fun initVars() {
        backupFolder = File(
            Environment.getExternalStorageDirectory().toString() + "/" + "com.ymest.rebost"
        )
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        backup = Backup(this)
    }

    private fun MostrarAlertDialogHayBackup() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.existe_backup))
        builder.setMessage("Se ha encontrado una copia de seguridad. Quieres recuperar la información?")
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            var intent = Intent(this, BackupActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        builder.setNegativeButton("CANCELAR"){dialog, which->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun HayBackup(): Boolean {
        if(checkStoragePermission()){
            var hijos = backupFolder.listFiles()
            return !hijos.isNullOrEmpty()
        }else{
            requestStoragePermissionImport()
        }
        return false
    }

    private fun requestStoragePermissionImport(){
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE_IMPORT)

    }

    private fun checkStoragePermission(): Boolean{
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
    }

    private fun MarcarComoNoPrimeraEjecucion() {
        with (sharedPref.edit()) {
            putBoolean("FirstTime", false)
            commit()
        }
    }

    private fun EsPrimeraEjecucion():Boolean {
        sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean("FirstTime",true)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        compruebaSiHayUser()
        /*if (compruebaCaducados()){
            muestraNotifiacion()
        }*/
        super.onRestart()
    }

    private fun muestraNotifiacion() {
        createNotificationChannel()
        var detalleprod: Producto
        var detalleProductoCrud : TaulaProductesCrud = TaulaProductesCrud(this)

        detalleprod = detalleProductoCrud.getProducte(primerCaducat.code.toString()).get(0)

        val intent = Intent(this, DetallProdBuscatsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }
        intent.putExtra("ID", primerCaducat.code)
        intent.putExtra("VEDE", Constants.NOTIFICACIO)
        intent.putExtra("IDLL", primerCaducat.idLlista)

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shopping_basket_24px_black)
            .setContentTitle("Producto Caducado!")
            .setContentText(BuscaNomProducte(detalleprod))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val stackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addParentStack(DetallProdBuscatsActivity::class.java)
        stackBuilder.addNextIntent(intent)
        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultPendingIntent)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun BuscaNomProducte(item: Producto): String? {
        var nomProducte: String = ""
        if(!item.product?.productNameEs.isNullOrEmpty()) {
            if (item?.product?.productNameEs?.length!! > 20) {
                nomProducte = item.product.productNameEs.substring(0, 20) + "..."
            } else{
                nomProducte = item.product.productNameEs
            }
        }else if(!item.product?.productName.isNullOrEmpty()){
            nomProducte = item.product?.productName.toString()
        }else if(!item?.product?.genericNameEs.isNullOrEmpty()){
            nomProducte = item.product?.genericNameEs.toString()
        }else if(!item?.product?.genericName.isNullOrEmpty()){
            nomProducte = item.product?.genericName.toString()
        }
        return nomProducte
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun compruebaCaducados(): Boolean {
        crudLlistaProductes = TaulaProductesALlistesCrud(applicationContext)
        productesCaducats = crudLlistaProductes.getCaducats(Funcions.obtenirDataActualMillis())
        if (productesCaducats.size>0){
            primerCaducat = productesCaducats.get(0)
        }
        return productesCaducats.size > 0
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun compruebaSiHayUser() {
        var mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser == null){
            tvNomUser.text = "LogIn"
            ivUserLogin.setImageResource(R.drawable.ic_baseline_account_circle_24_primarycolor)
        } else{
            Log.d("PHOTOURL", mAuth.currentUser?.photoUrl.toString())
            if(!mAuth.currentUser?.displayName.isNullOrEmpty()) tvNomUser.text = mAuth.currentUser?.displayName
            else tvNomUser.text = mAuth.currentUser?.email
            if(!mAuth.currentUser?.photoUrl.toString().isNullOrEmpty() && mAuth.currentUser?.photoUrl.toString() != "null") Picasso.get().load(mAuth.currentUser?.photoUrl).into(ivUserLogin)
            else ivUserLogin.setImageResource(R.drawable.ic_baseline_account_circle_24_primarycolor)
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
        builder.setNegativeButton("SALIR"){ dialog, which ->
            super.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    fun cargarProducto(cb: String){
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