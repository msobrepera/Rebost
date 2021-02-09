package com.ymest.rebost.ubicacions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.R
import com.ymest.rebost.json.Llistes
import com.ymest.rebost.json.Ubicacio
import com.ymest.rebost.productesdecategoria.ProductesDeCategoriaActivity
import com.ymest.rebost.productesdecategoria.ProductesLlistaCompraActivity
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.sqlite.TaulaUbicacionsCrud
import com.ymest.rebost.utils.Constants
import kotlinx.android.synthetic.main.activity_ubicacions.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*

class UbicacionsActivity : AppCompatActivity(), CellClickListener, CellLongClickListener {
    lateinit var layoutManager: RecyclerView.LayoutManager
    //private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewAdapter: Adaptador2linies
    private lateinit var viewAdapterLlistes: Adaptador2linies

    /*private lateinit var viewAdapterLlistes: AdaptadorLlistes*/
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var crudUbicacio: TaulaUbicacionsCrud
    lateinit var llistaUbicacions : ArrayList<Ubicacio>
    lateinit var crudLlistes: TaulaLlistesCrud

    lateinit var llistaLlistes : ArrayList<Llistes>


    var isActionMode = false
    var actionMode: ActionMode? = null
    var callback: ActionMode.Callback? = null
    lateinit var vede: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubicacions)
        vede = intent.getStringExtra("VEDE").toString()
        toolbarUbicacio.title = vede

        setSupportActionBar(toolbarUbicacio)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        CargaRecyclerView()
        Toast.makeText(this, vede, Toast.LENGTH_SHORT).show()


        fabUbicacions.setOnClickListener {
            intent = Intent(this, UbicacionsAddEditActivity::class.java)
            intent.putExtra("ACCIO", "ADD")
            intent.putExtra("ESTEMA", vede)
            Log.d("TAG1", vede)
            startActivity(intent)
        }

        callback = object: ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                when(vede){
                    Constants.UBICACIONS->{
                        viewAdapter.iniciarActionMode()
                    }
                    Constants.LLISTES->{
                        viewAdapterLlistes.iniciarActionMode()
                    }
                    Constants.MISLISTAS->{
                        viewAdapterLlistes.iniciarActionMode()
                    }
                }
                actionMode = mode
                menuInflater.inflate(R.menu.menu_eliminar_ubicaciones, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = "0 Seleccionados"
                toolbarUbicacio.visibility = View.GONE
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when (item?.itemId){
                    R.id.imEliminarUbicaciones ->{
                        when (vede){
                            Constants.UBICACIONS->{
                                for (id in viewAdapter.itemSeleccionados!!){
                                    if(id==1){
                                        Snackbar.make(rvubicacions,getString(R.string.ERROR_NO_SE_PUEDE_ELIMNAR_UBICACION), Snackbar.LENGTH_LONG).show()
                                    }else{
                                        if(TaulaProductesALlistesCrud(applicationContext).getNumProductosUnicosXUbicacion(id) > 0){
                                            mostraADUbicacionNoVacia(id)
                                        }
                                    }
                                }
                                CargaRecyclerView()
                                viewAdapter.terminarActionMode()
                            }
                            Constants.LLISTES, Constants.MISLISTAS->{
                                for (id in viewAdapterLlistes.itemSeleccionados!!){
                                    if(id == 1 || id == 2 || id == 3){
                                        Snackbar.make(rvubicacions,getString(R.string.ERROR_NO_SE_PUEDE_ELIMNAR), Snackbar.LENGTH_LONG).show()
                                    }else{
                                        if(TaulaProductesALlistesCrud(applicationContext).getNumProductosXLista(id)>0){
                                            TaulaProductesALlistesCrud(applicationContext).deleteProductesDeUnaLlista(id)
                                        }
                                        TaulaLlistesCrud(applicationContext).deleteLlista(id)
                                    }
                                }
                                CargaRecyclerView()
                                viewAdapterLlistes.terminarActionMode()
                            }

                        }
                    }
                }
                mode?.finish()
                isActionMode = false
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                when(vede){
                    Constants.UBICACIONS->{
                        viewAdapter.destruirActionMode()
                    }
                    Constants.LLISTES->{
                        viewAdapterLlistes.destruirActionMode()
                    }
                    Constants.MISLISTAS->{
                        viewAdapterLlistes.destruirActionMode()
                    }
                }
                isActionMode = false
                toolbarUbicacio.visibility = View.VISIBLE
            }
        }
    }

    private fun mostraADUbicacionNoVacia(id:Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.existen_productos))
        builder.setMessage(getString(R.string.ubicacion_no_vacía))
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            TaulaProductesALlistesCrud(applicationContext).updateUbicacion(id, 1)
            crudUbicacio = TaulaUbicacionsCrud(applicationContext)
            crudUbicacio.deleteUbicacio(id)
            CargaRecyclerView()
            dialog.dismiss()
        }
        builder.setNegativeButton("CERRAR"){dialog, which->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contextual_listas_ubicaciones, menu)
        var btnCambiarVista = menu?.findItem(R.id.imCambiarVista)

        btnCambiarVista?.isVisible = vede == Constants.MISLISTAS || vede == Constants.MISUBICACIONES

        btnCambiarVista?.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.imCambiarVista -> {
                    when (vede){
                        Constants.MISLISTAS ->{
                            vede = Constants.MISUBICACIONES
                            CargaRecyclerView()
                        }
                        Constants.MISUBICACIONES->{
                            vede = Constants.MISLISTAS
                            CargaRecyclerView()
                        }
                    }
                    toolbarUbicacio.title = vede
                    Log.d("FILTRA", "click en Filtra")

                }

            }
            true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    fun CargaRecyclerView(){
        rvubicacions.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rvubicacions.layoutManager = layoutManager
        when(vede){
            Constants.UBICACIONS, Constants.MISUBICACIONES->{
                crudUbicacio = TaulaUbicacionsCrud(this)
                llistaUbicacions = crudUbicacio.getAllUbicacions()
                viewAdapter = Adaptador2linies(this, llistaUbicacions, null, vede, this, this)
                rvubicacions.adapter = viewAdapter
            }

            Constants.LLISTES, Constants.MISLISTAS ->{
                crudLlistes = TaulaLlistesCrud(this)
                llistaLlistes = crudLlistes.getAllLlista()
                viewAdapterLlistes = Adaptador2linies(this, null, llistaLlistes, vede, this, this)
                /*viewAdapterLlistes = AdaptadorLlistes(this, llistaLlistes, this, this)*/
                rvubicacions.adapter = viewAdapterLlistes
            }
           /* Constants.MISLISTAS ->{
                crudLlistes = TaulaLlistesCrud(this)
                llistaLlistes = crudLlistes.getAllLlista()
                viewAdapterLlistes = Adaptador2linies(this, null, llistaLlistes, vede, this, this)
                *//*viewAdapterLlistes = AdaptadorLlistes(this, llistaLlistes, this, this)*//*
                rvubicacions.adapter = viewAdapterLlistes
            }*/
        }


    }

    override fun onCellClickListener(id: String?) {
        when(vede){
            Constants.UBICACIONS->{
                if (isActionMode){
                    viewAdapter.seleccionarItem(id.toString().toInt())
                    actionMode?.title = viewAdapter.obtenerNumSeleccionados().toString() + " seleccionados"
                } else {
                    Toast.makeText(this,"Cell clicked id: " + id, Toast.LENGTH_SHORT).show()
                    intent = Intent(this, UbicacionsAddEditActivity::class.java)
                    intent.putExtra("ESTEMA", Constants.UBICACIONS)
                    intent.putExtra("ACCIO", "EDIT")
                    intent.putExtra("ID", id)
                    startActivity(intent)
                }
                viewAdapter.notifyDataSetChanged()
            }
            Constants.MISUBICACIONES->{
                if (isActionMode){
                    viewAdapter.seleccionarItem(id.toString().toInt())
                    actionMode?.title = viewAdapter.obtenerNumSeleccionados().toString() + " seleccionados"
                } else {
                    intent = Intent(this, ProductesDeCategoriaActivity::class.java)
                    intent.putExtra("VEDE", vede)
                    intent.putExtra("IDLLISTA", id)
                    startActivity(intent)
                }
                viewAdapter.notifyDataSetChanged()
            }
            Constants.LLISTES ->{
                if (isActionMode){
                    viewAdapterLlistes.seleccionarItem(id.toString().toInt())
                    actionMode?.title = viewAdapterLlistes.obtenerNumSeleccionados().toString() + " seleccionados"
                } else {
                    Toast.makeText(this,"Cell clicked id: " + id, Toast.LENGTH_SHORT).show()
                    intent = Intent(this, UbicacionsAddEditActivity::class.java)
                    intent.putExtra("ESTEMA", vede)
                    intent.putExtra("ACCIO", "EDIT")
                    intent.putExtra("ID", id)
                    startActivity(intent)
                }
                viewAdapterLlistes.notifyDataSetChanged()
            }
            Constants.MISLISTAS->{
                if (isActionMode){
                    viewAdapterLlistes.seleccionarItem(id.toString().toInt())
                    actionMode?.title = viewAdapterLlistes.obtenerNumSeleccionados().toString() + " seleccionados"
                } else {
                    if(id == "1"){
                        intent = Intent(this, ProductesLlistaCompraActivity::class.java)
                        intent.putExtra("VEDE", vede)
                        intent.putExtra("IDLLISTA", id)
                        startActivity(intent)
                    }else{
                        intent = Intent(this, ProductesDeCategoriaActivity::class.java)
                        intent.putExtra("VEDE", vede)
                        intent.putExtra("IDLLISTA", id)
                        startActivity(intent)
                    }

                }
            }
        }

    }

    override fun onCellLongClickListener(id: String?):Boolean {
        when(vede){
            Constants.UBICACIONS ->{
                if(!isActionMode){
                    startSupportActionMode(callback!!)
                    isActionMode = true
                    viewAdapter.seleccionarItem(id.toString().toInt())
                }else{
                    //hacer selecciones o deselecciones
                    viewAdapter.seleccionarItem(id.toString().toInt())
                }
                //inidicamos en el titulo el número de elementos seleccionados
                actionMode?.title = viewAdapter.obtenerNumSeleccionados().toString() + " seleccionados"
            }
            Constants.LLISTES, Constants.MISLISTAS ->{
                if(!isActionMode){
                    startSupportActionMode(callback!!)
                    isActionMode = true
                    viewAdapterLlistes.seleccionarItem(id.toString().toInt())
                }else{
                    //hacer selecciones o deselecciones
                    viewAdapterLlistes.seleccionarItem(id.toString().toInt())
                }
                //inidicamos en el titulo el número de elementos seleccionados
                actionMode?.title = viewAdapterLlistes.obtenerNumSeleccionados().toString() + " seleccionados"
            }
        }


        return true
    }


    override fun onRestart() {
        super.onRestart()
        CargaRecyclerView()
    }


}