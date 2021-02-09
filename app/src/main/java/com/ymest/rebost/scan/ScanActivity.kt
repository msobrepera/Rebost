package com.ymest.rebost.scan

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import com.ymest.rebost.detallproductesbuscats.DetallProdBuscatsActivity
import com.ymest.rebost.json.Producto
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Network
import kotlinx.android.synthetic.main.activity_alert_dialog.*
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*

import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ScanActivity : AppCompatActivity() {
    private var surfaceView: SurfaceView? = null
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null
    private var toneGen1: ToneGenerator? = null
    //private var barcodeText: TextView? = null
    private var barcodeData: String? = null
    //lateinit var tab:String
    lateinit var idLlista: String


    val network = Network(this)
    lateinit var producto: Producto


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        if(intent.getStringExtra("IDLLISTA").toString().isNullOrEmpty()){
            idLlista = "0"
        }else{
            idLlista =intent.getStringExtra("IDLLISTA").toString()
        }
        Log.d("IDLLISTA", "SCAN ONCREATE: " + idLlista)

        toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        surfaceView = findViewById(R.id.surface_view)
        //barcodeText = findViewById(R.id.barcode_text)
        barcodeData = ""
        initialiseDetectorsAndSources()
       // tab = intent.getStringExtra("TAB").toString()
        pbScan.visibility = View.GONE
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_CAMERA_PERMISSION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initialiseDetectorsAndSources()
                    cameraSource?.start(surfaceView!!.holder)
                }else{
                    muestraAlertDialogPermiso()
                }
            }
        }
    }

    private fun muestraAlertDialogPermiso() {

        val builder = AlertDialog.Builder(this)
        val factory = LayoutInflater.from(this)
        val view: View = factory.inflate(R.layout.alert_dialog_explicacionpermiso, null)
        builder.setView(view)

        builder.setTitle("Permiso Cámara")
        builder.setIcon(R.drawable.ic_baseline_photo_camera_24)

        builder.setPositiveButton("ACEPTAR PERMISO"){ dialog, which ->
            // Do something when user press the positive button
            if (ActivityCompat.checkSelfPermission(
                    this@ScanActivity,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ){
                dialog.dismiss()
            }else{
                dialog.dismiss()
                ActivityCompat.requestPermissions(
                    this@ScanActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION)
            }
            dialog.dismiss()
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("CERRAR"){ dialog, which ->
            dialog.dismiss()
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    private fun initialiseDetectorsAndSources() {

        //Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            //.setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@ScanActivity,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(surfaceView!!.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@ScanActivity,
                            arrayOf(Manifest.permission.CAMERA),
                            REQUEST_CAMERA_PERMISSION
                        )

                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }



            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })
        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                //TODO Com parar l'escaner al primer codi de barres rebut
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {

                       // surface_view.visibility = View.GONE
                    pbScan.visibility = View.VISIBLE
                    //barcodeText!!.post {
                        if (barcodes.valueAt(0).email != null) {
                            //barcodeText!!.removeCallbacks(null)
                            barcodeData = barcodes.valueAt(0).email.address
                            //barcodeText!!.text = barcodeData
                            toneGen1!!.startTone(ToneGenerator.TONE_CDMA_PIP, 150)

                        } else {
                            barcodeData = barcodes.valueAt(0).displayValue
                            //barcodeText!!.text = barcodeData
                            toneGen1!!.startTone(ToneGenerator.TONE_CDMA_PIP, 150)

                            buscarProducto(barcodeData)
                      //  }
                        barcodeDetector?.release()

                        /*intent = Intent(applicationContext, AlertDialogActivity::class.java)
                        intent.putExtra("CB", barcodeData)
                        intent.putExtra("TAB", tab)
                        startActivity(intent)
                        finish()*/
                        //alertDialogDemo(barcodeData.toString())
                        //TODO Afegir Activity amb la info del producte a afegir
                    }
                }
            }
        })
    }

    private fun buscarProducto(barcodeData: String?) {
        if (network.hayRed()) {
            val queue = Volley.newRequestQueue(this)
            val url = "https://es.openfoodfacts.org/api/v0/product/" + barcodeData + ".json"

            Log.d("ERRDIALOG", url)

            val request = StringRequest(
                Request.Method.GET, url, { response ->
                    // tvNombreProducto.text = "Response is: ${response.substring(0, 500)}"
                    Log.d("ERRDIALOG", response.toString())
                    var gson = Gson()
                    producto = gson.fromJson(response, Producto::class.java)

                    if (producto.statusVerbose != "product not found") {
                        var intent = Intent(this, DetallProdBuscatsActivity::class.java)
                        intent.putExtra("ID", barcodeData)
                        intent.putExtra("VEDE", Constants.SCAN)
                        intent.putExtra("IDLL", idLlista)
                        Log.d("IDLLISTA", "SCAN ACTIVITY: " + idLlista)
                        startActivity(intent)
                        /*cvScan.visibility = View.GONE

                        cvInferior.visibility = View.VISIBLE
                        //ComprovaProducto()
                        tvNombreProductoScan.text = producto.product?.productNameEs
                        tvMarcaProductoScan.text  = producto.product?.brands
                        Picasso.get().load(producto.product?.imageUrl).into(ivProductoScan)*/

                    } else {

                        Snackbar.make(cvScan,getString(R.string.ERR_PRODUCTO_DESCONOCIDO), Snackbar.LENGTH_LONG).show()
                        //finish()
                    }
                },
                { Snackbar.make(cvScan,getString(R.string.ERR_RED), Snackbar.LENGTH_LONG).show() })
            queue.add(request)
        }
    }


    override fun onPause() {
        super.onPause()
        //supportActionBar!!.hide()
        cameraSource!!.release()
    }

    override fun onResume() {
        super.onResume()

       // supportActionBar!!.hide()
        initialiseDetectorsAndSources()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 201
    }
}