package com.ymest.rebost.ubicacions

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.ymest.rebost.R
import com.ymest.rebost.json.Llistes
import com.ymest.rebost.json.Ubicacio
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import com.ymest.rebost.sqlite.TaulaUbicacionsCrud
import com.ymest.rebost.utils.Constants
import kotlinx.android.synthetic.main.activity_ubicacions_add_edit.*


class UbicacionsAddEditActivity : AppCompatActivity() {
    lateinit var crudUbicacio: TaulaUbicacionsCrud
    lateinit var crudLlistes: TaulaLlistesCrud
    lateinit var id: String
    lateinit var accio:String
    lateinit var estema: String
    var hayCambios: Boolean = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacions_add_edit)

        setSupportActionBar(toolbarAddEditUbicacio)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        accio = intent.getStringExtra("ACCIO").toString()
        id = intent.getStringExtra("ID").toString()
        estema = intent.getStringExtra("ESTEMA").toString()

        Toast.makeText(this, accio, Toast.LENGTH_SHORT).show()
        when (estema){
           Constants.UBICACIONS, Constants.MISUBICACIONES ->{
               when (accio){
                   "ADD"->{
                       toolbarAddEditUbicacio.title = "Añadir Ubicación"
                       ivCalendarAddEdit.visibility = View.GONE
                       cbDatesCad.visibility = View.GONE
                       ivHelpCantidad.visibility = View.GONE
                       ivHelpFechas.visibility = View.GONE
                       ivHastagAddEdit.visibility = View.GONE

                   }
                   "EDIT"->{
                       toolbarAddEditUbicacio.title = "Editar Ubicación"
                       ivCalendarAddEdit.visibility = View.GONE
                       cbDatesCad.visibility = View.GONE
                       ivHastagAddEdit.visibility = View.GONE
                       cbGestionaCantidadAdd.visibility = View.GONE
                       ivHelpCantidad.visibility = View.GONE
                       ivHelpFechas.visibility = View.GONE
                       cbGestionaUbicaciones.visibility = View.GONE
                       ivUbicacionAddEdit.visibility = View.GONE
                       ivHelpUbicaciones.visibility = View.GONE
                       CarregaInfoUbicacio()
                   }
               }
           }
            Constants.LLISTES, Constants.MISLISTAS ->{
                when (accio){
                    "ADD"->{
                        toolbarAddEditUbicacio.title = "Añadir Listas"
                        etNomUbicacio.hint = "Nombre de la Lista"
                        etDescripcioUbicacio.visibility = View.GONE
                    }
                    "EDIT"->{
                        toolbarAddEditUbicacio.title = "Editar Listas"
                        etDescripcioUbicacio.visibility = View.GONE
                        CarregaInfoListas()
                    }
                }
            }

        }
        cargaEventos()


    }

    private fun cargaEventos() {
        ivHelpFechas.setOnClickListener {
            MuestraAyudaFechas()
        }
        ivHelpCantidad.setOnClickListener {
            MuestraAyudaCantidad()
        }
        ivHelpUbicaciones.setOnClickListener {
            MuestraAyudaUbicacion()
        }
        cbDatesCad.setOnCheckedChangeListener { buttonView, isChecked ->
            hayCambios = true
        }
        cbGestionaCantidadAdd.setOnCheckedChangeListener { buttonView, isChecked ->
            hayCambios = true
        }
        cbGestionaUbicaciones.setOnCheckedChangeListener { buttonView, isChecked ->
            hayCambios = true
        }

        etNomUbicacio.addTextChangedListener(object:TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                hayCambios = true
            }
        })
        etDescripcioUbicacio.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                hayCambios = true
            }

        })
    }


    override fun onBackPressed() {
        if(hayCambios){
            muestraAvisoGuardarCambios()
        } else{
            super.onBackPressed()
        }
    }

    private fun muestraAvisoGuardarCambios() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titol_guardar_cambios))
        builder.setIcon(R.drawable.ic_senal_de_precaucion_orange_24)
        builder.setMessage("Hay cambios sin guardar. Quieres guardarlos ahora?")
        builder.setPositiveButton("GUARDAR"){ dialog, which ->
            Toast.makeText(this, "GUARDAR", Toast.LENGTH_SHORT).show()
            guardarCambios()
        }
        builder.setNegativeButton("CERRAR"){dialog, which ->
            dialog.dismiss()
            super.onBackPressed()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun guardarCambios() {
        when(estema){
            Constants.UBICACIONS, Constants.MISUBICACIONES->{
                when(accio){
                    "ADD"-> AfegirUbicacio()
                    "EDIT"-> UpdateUbicacio()
                }
            }
            Constants.LLISTES, Constants.MISLISTAS ->{
                when(accio){
                    "ADD"-> afegirLlista()
                    "EDIT"-> updateLlista()
                }
            }
        }
    }

    private fun MuestraAyudaUbicacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titol_gestiona_ubicacion))
        builder.setIcon(R.drawable.ic_baseline_help_outline_24)
        builder.setMessage("Permite indicar la ubicación del producto a guardar en la lista. Muy útil para Listas de productos en stock.")
        builder.setPositiveButton("CERRAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun MuestraAyudaCantidad() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titol_gestiona_cantidad))
        builder.setIcon(R.drawable.ic_baseline_help_outline_24)
        builder.setMessage("Permite indicar la cantidad de producto a guardar en la lista. Muy útil para Listas de la Compra o Listas de productos en stock.")
        builder.setPositiveButton("CERRAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun MuestraAyudaFechas() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.titol_gestiona_fechas))
        builder.setIcon(R.drawable.ic_baseline_help_outline_24)
        builder.setMessage("Permite indicar la fecha de caducidad del producto a guardar en la lista. Muy útil para Listas de productos en stock.")
        builder.setPositiveButton("CERRAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_addedit_ubicacio, menu)
        var menuItemSave = menu?.findItem(R.id.imSave)
        var menuItemAdd = menu?.findItem(R.id.imAdd)
        when(accio){
            "ADD"->{
                menuItemSave?.setVisible(false)
                menuItemAdd?.setVisible(true)
            }
            "EDIT"->{
                menuItemSave?.setVisible(true)
                menuItemAdd?.setVisible(false)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.imAdd -> {
                if(estema==Constants.UBICACIONS || estema==Constants.MISUBICACIONES) AfegirUbicacio() else afegirLlista()
            }
            R.id.imSave -> {
                if(estema==Constants.UBICACIONS || estema==Constants.MISUBICACIONES) UpdateUbicacio() else updateLlista()
                //Toast.makeText(this, "Ubicació modificada", Toast.LENGTH_SHORT).show()
                    }
            else ->{
                return super.onOptionsItemSelected(item)
                }
            }

        return true
    }




    private fun UpdateUbicacio() {
        if(ComprovaCamps()){
            var ubicacioActualitzada: Ubicacio
            ubicacioActualitzada = Ubicacio(etNomUbicacio.tag.toString().toInt(), etNomUbicacio.text.toString(), etDescripcioUbicacio.text.toString())
            crudUbicacio = TaulaUbicacionsCrud(this)
            crudUbicacio.updateUbicacio(ubicacioActualitzada)
            Toast.makeText(this, "Ubicació Actualitzada", Toast.LENGTH_SHORT).show()
            hayCambios = false
            finish()
        } else{
            etNomUbicacio.setError(getString(R.string.ERR_NOM_UBICACIO_OBLIGATORI))
            etNomUbicacio.selectAll()
            //Toast.makeText(this, "El Nom de la Ubicació és obligatori", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLlista() {
        var datacad:Int = 0
        var gestionaCant: Int = 0
        var gestUbic:Int = 0
        if(ComprovaCamps()){
            crudLlistes = TaulaLlistesCrud(this)
            if(!crudLlistes.existeOtraLlistaConMismoNombre(etNomUbicacio.tag.toString(), etNomUbicacio.text.toString())){
                if(cbDatesCad.isChecked) datacad = 1 else datacad = 0
                if(cbGestionaCantidadAdd.isChecked) gestionaCant = 1 else gestionaCant = 0
                if(cbGestionaUbicaciones.isChecked) gestUbic = 1 else gestUbic = 0
                crudLlistes.updateLlista(etNomUbicacio.tag.toString().toInt(), etNomUbicacio.text.toString(), datacad, gestionaCant, gestUbic)
                Snackbar.make(etNomUbicacio,getString(R.string.SB_LlistaActualizada), Snackbar.LENGTH_LONG).show()
                hayCambios = false
                finish()
            }else{
                etNomUbicacio.setError(getString(R.string.ERR_TEXTO_LISTA_DUPLICADO))
                etNomUbicacio.selectAll()
                //Snackbar.make(etNomUbicacio,getString(R.string.ERR_TEXTO_LISTA_DUPLICADO), Snackbar.LENGTH_LONG).show()
            }
        } else{
            etNomUbicacio.setError(getString(R.string.ERR_TEXTO_LISTA_VACIO))
            etNomUbicacio.selectAll()
            //Snackbar.make(etNomUbicacio,getString(R.string.ERR_TEXTO_LISTA_VACIO), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun CarregaInfoUbicacio() {
        var item: Ubicacio
        crudUbicacio = TaulaUbicacionsCrud(this)
        item = crudUbicacio.getUbicacio(id.toInt())
        etNomUbicacio.setText(item.nomubicacio)
        etNomUbicacio.setTag(item.id)
        etDescripcioUbicacio.setText(item.descubicacio)
    }

    private fun CarregaInfoListas() {
        var item: Llistes
        crudLlistes = TaulaLlistesCrud(this)
        item = crudLlistes.getLlista(id.toInt())
        etNomUbicacio.setText(item.nomLlista)
        etNomUbicacio.setTag(item.id)
        cbDatesCad.isChecked = item.gestion_dataCad == 1
        cbGestionaCantidadAdd.isChecked = item.gestiona_cantidad == 1
        cbGestionaUbicaciones.isChecked = item.gestiona_ubicaciones == 1
    }

    private fun AfegirUbicacio() {
        if(ComprovaCamps()){
            var nouId = TaulaUbicacionsCrud(this).getNextIDUbicacions()
            var novaUbicacio = Ubicacio(nouId ,etNomUbicacio.text.toString(), etDescripcioUbicacio.text.toString())
            crudUbicacio = TaulaUbicacionsCrud(this)
            crudUbicacio.addUbicacio(novaUbicacio)
            Toast.makeText(this, "Ubicació Afegida", Toast.LENGTH_SHORT).show()
            hayCambios = false
            finish()
        } else {
            etNomUbicacio.setError(getString(R.string.ERR_NOM_UBICACIO_OBLIGATORI))
            etNomUbicacio.selectAll()
        }
    }



    private fun afegirLlista() {
        var datacad: Int = 0
        var gestCant:Int = 0
        var gestUbic: Int = 0
        if(ComprovaCamps()){
            crudLlistes = TaulaLlistesCrud(this)
            if(!crudLlistes.existeLlista(etNomUbicacio.text.toString())){
                if(cbDatesCad.isChecked) datacad = 1 else datacad = 0
                if(cbGestionaCantidadAdd.isChecked) gestCant = 1 else gestCant = 0
                if (cbGestionaUbicaciones.isChecked) gestUbic = 1 else gestUbic = 0
                var newid = crudLlistes.getNextIDLlista()
                crudLlistes.addLlista(newid, etNomUbicacio.text.toString(), datacad, gestCant, gestUbic)
                Snackbar.make(etNomUbicacio,getString(R.string.SB_LlistaAfegida), Snackbar.LENGTH_LONG).show()
                hayCambios = false
                finish()
            }else{
                etNomUbicacio.setError(getString(R.string.ERR_TEXTO_LISTA_DUPLICADO))
                etNomUbicacio.selectAll()
                //Snackbar.make(etNomUbicacio,getString(R.string.ERR_TEXTO_LISTA_DUPLICADO), Snackbar.LENGTH_LONG).show()
            }

        } else {
            etNomUbicacio.setError(getString(R.string.ERR_TEXTO_LISTA_VACIO))
            etNomUbicacio.selectAll()
            //Snackbar.make(etNomUbicacio,getString(R.string.ERR_TEXTO_LISTA_VACIO), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun ComprovaCamps(): Boolean {
        return etNomUbicacio.text.isNotEmpty()
    }
}