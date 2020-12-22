package com.ymest.rebost.scan

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import com.ymest.rebost.json.Comprar
import com.ymest.rebost.json.Producto
import com.ymest.rebost.json.Rebost
import com.ymest.rebost.json.Ubicacio
import com.ymest.rebost.sqlite.old.ComprarCrud
import com.ymest.rebost.sqlite.old.ProductesCrud
import com.ymest.rebost.sqlite.old.RebostCrud
import com.ymest.rebost.sqlite.old.UbicacionsCrud
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions
import com.ymest.rebost.utils.Network
import kotlinx.android.synthetic.main.activity_alert_dialog.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AlertDialogActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    lateinit var codi: String
    lateinit var tab: String
    lateinit var crudRebost: RebostCrud
    lateinit var crudComprar: ComprarCrud
    lateinit var crudProducto: ProductesCrud
    lateinit var crudUbicacions: UbicacionsCrud
    lateinit var producto: Producto
    lateinit var LlistaUbicacions: ArrayList<Ubicacio>
    lateinit var ubicacionsNom: Array<String>
    var day: Int  = 0
    var month: Int = 0
    var year: Int = 0
    lateinit var dataActual: String
    var dataActualMillis: Long = 0

    var q: Int = 0
    val network = Network(this)

    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    lateinit var alertDialog: AlertDialog.Builder
    lateinit var dialog: AlertDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_dialog)

        codi = intent.getStringExtra("CB").toString()
        tab = intent.getStringExtra("TAB").toString()
        Log.d("ERRDIALOG", codi)
        btnAddAlert.setText("Afegir a " + tab)
        cargaProducto(codi)

        tvDataCaducitatAlert.setOnClickListener{
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()
        }


        q = etQAlert.text.toString().toInt()
        etQAlert.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!etQAlert.text.toString().isEmpty()) {
                    q = etQAlert.text.toString().toInt()
                    if (q < 1) {
                        etQAlert.setText("1")
                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {
                if (!etQAlert.text.toString().isEmpty()) {
                    q = etQAlert.text.toString().toInt()
                }

            }

        })
        ivAddQAlert.setOnClickListener {
            q++
            etQAlert.setText(q.toString())
        }
        ivDelQAlert.setOnClickListener {
            if (q>1){
                q--
                etQAlert.setText(q.toString())
            }
        }

        tvUbicacioAlert.setOnClickListener {
            preparaDialogUbicacions(it)
        }

        btnCancelarAlert.setOnClickListener {
            finish()
        }

        btnAddAlert.setOnClickListener {
            if(!etQAlert.text.toString().isEmpty()) {
                q = etQAlert.text.toString().toInt()
            } else {
                q = 0
            }
            if(q > 0){
                when(tab){
                    Constants.TAB_PRIMERA -> {
                        afegirProducto()
                        afegirRebost()
                    }
                    Constants.TAB_SEGONA -> {
                        afegirProducto()
                        afegirComprar()
                    }
                    Constants.TAB_TERCERA -> {

                    }
                }
                finish()
            }else{
                Toast.makeText(this, "Has d'afegir com a mínim 1 unitat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun afegirProducto(){
        crudProducto = ProductesCrud(this)
        if (!crudProducto.existeProducto(producto.code.toString())) {
            //Toast.makeText(this, "No existe Producto", Toast.LENGTH_SHORT).show()
            crudProducto.addProducte(producto)
        } else {
            //Toast.makeText(this, "SI existe Producto", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun afegirRebost(){

        val dataCadMillis =  Funcions.DataToMillis(tvDataCaducitatAlert.text.toString())
        crudRebost = RebostCrud(this)
        crudRebost.addProducteRebost(Rebost(0, producto.code.toString(), dataCadMillis, q,0,0, Funcions.obtenirDataActualMillis()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun afegirComprar(){
        crudComprar = ComprarCrud(this)
        crudComprar.addProducteComprar(Comprar(0, producto.code.toString(), q, Funcions.obtenirDataActualMillis(), 0))
    }

    private fun preparaDialogUbicacions(it: View?) {
        val builder = AlertDialog.Builder(this)
        ubicacionsNom = arrayOf()
        builder.setTitle("Escull Ubicació")
        crudUbicacions = UbicacionsCrud(this)
        LlistaUbicacions = crudUbicacions.getAllUbicacions()
        for(ubicacio in LlistaUbicacions){
            ubicacionsNom += ubicacio.nomubicacio
        }

        builder.setSingleChoiceItems(
            ubicacionsNom, // array
            -1 // initial selection (-1 none)
        ){ dialog, i ->}

        builder.setPositiveButton("Acceptar"){ dialog, which->
            val position = (dialog as AlertDialog).listView.checkedItemPosition
            // if selected, then get item text
            if (position !=-1){
                val selectedItem = ubicacionsNom[position]
                var idseleccionat:Int = position + 1
                Toast.makeText(this, "Seleccionat: " + idseleccionat, Toast.LENGTH_SHORT).show()
                tvUbicacioAlert.text = selectedItem
                lblUbicacio.tag = idseleccionat.toString()
                dialog.dismiss()
            } else {
                tvUbicacioAlert.text = "Ubicació per defecte"
                lblUbicacio.tag = 1
                dialog.dismiss()
            }
        }

        builder.setNegativeButton("Cancelar"){ dialog, which->
            tvUbicacioAlert.text = "Ubicació per defecte"
            lblUbicacio.tag = 1
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()

        dialog.listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "id: " + id.toInt() + 1, Toast.LENGTH_SHORT).show()

        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun cargaProducto(cb: String) {
        if (network.hayRed()) {
            val queue = Volley.newRequestQueue(this)
            val url = "https://es.openfoodfacts.org/api/v0/product/" + cb + ".json"
            val dataActual = LocalDate.now().plusDays(4)
            val formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            Log.d("ERRDIALOG", url)

            val request = StringRequest(
                Request.Method.GET, url, { response ->
                    // tvNombreProducto.text = "Response is: ${response.substring(0, 500)}"
                    Log.d("ERRDIALOG", response.toString())
                    var gson = Gson()
                    producto = gson.fromJson(response, Producto::class.java)

                    if (producto.statusVerbose != "product not found") {
                        //ComprovaProducto()
                        tvNombreAlert.text = ComprovaNomProducte(producto)
                        //tvNombreAlert.text = producto.product.product_name_es
                        tvTamanoAlert.text = ComprovaTamany(producto)
                        // tvTamanoAlert.text = producto.product.quantity
                        tvCodiBarresAlert.text = producto.code
                        insertaImatge(producto)
                        //Picasso.get().load(producto.product.image_url).into(ivFotoAlert)
                        when(tab){
                            Constants.TAB_PRIMERA ->{
                                tvDataCaducitatAlert.setText(dataActual.format(formatData))
                            }
                            Constants.TAB_SEGONA -> {
                                lblFechaCad.visibility = View.GONE
                                lblUbicacio.visibility = View.GONE
                                tvDataCaducitatAlert.visibility = View.GONE
                                tvUbicacioAlert.visibility = View.GONE
                            }
                        }


                    } else {
                        Toast.makeText(this, "Producte no trobat", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                },
                { tvNombreAlert.text = "No ha funcionat" })
            queue.add(request)
        }
    }

    /*private fun ComprovaProducto() {
        if(producto.product.imageUrl.isNullOrEmpty()) producto.product.imageUrl = ""
        if(producto.product.quantity.isNullOrEmpty()) producto.product.quantity = ""
        if(producto.product.product_name.isNullOrEmpty()) producto.product.product_name = ""
        if(producto.product.product_name_es.isNullOrEmpty()) producto.product.product_name_es = ""
        if(producto.product.stores.isNullOrEmpty()) producto.product.stores = ""
        //if(producto.product.stores_tags.isNullOrEmpty()) producto.product.stores_tags = List<String>()
        if(producto.product.brands.isNullOrEmpty()) producto.product.brands = ""
    }*/

    private fun insertaImatge(producto: Producto) {

        if(producto.product?.imageUrl!!.isNotEmpty()){
            Picasso.get().load(producto.product.imageUrl).into(ivFotoAlert)
        }else{
            Picasso.get().load(R.drawable.ic_shopping_basket_24px_black).into(ivFotoAlert)
        }
    }

    private fun ComprovaTamany(producto: Producto): String {
        var tamany: String = ""
        if(producto.product?.quantity!!.isNotEmpty()){
            tamany = producto.product.quantity
        } else{
            tamany = "Tamany Desconegut"
        }
        return tamany
    }

    private fun ComprovaNomProducte(producto: Producto): String {
        var nomproducte: String = ""
        if(!producto.product?.productNameEs.isNullOrEmpty()){
            nomproducte = producto.product?.productNameEs.toString()
        } else if(!producto.product?.productName.isNullOrEmpty()){
            nomproducte = producto.product?.productName.toString()
        } else{
            nomproducte = "Nom Desconegut"
        }
        return nomproducte
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val data = dayOfMonth.toString() + "/" + (month+1).toString() + "/" + year.toString()
        Log.d("DATEPICKER", year.toString())
        Log.d("DATEPICKER", (month + 1).toString())
        Log.d("DATEPICKER", dayOfMonth.toString())
        tvDataCaducitatAlert.setText(data)
    }


}
