package com.ymest.rebost.llistatproductes

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.ymest.rebost.R
import com.ymest.rebost.detallproductes.ProductoDetalleActivity
import com.ymest.rebost.detallproductesbuscats.DetallProdBuscatsActivity
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.Comprar
import com.ymest.rebost.json.Producto
import com.ymest.rebost.json.Rebost
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesCrud
import com.ymest.rebost.sqlite.old.ComprarCrud
import com.ymest.rebost.sqlite.old.ProductesCrud
import com.ymest.rebost.sqlite.old.RebostCrud
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions


class TabsFragment(ctx: Context, tabTitle: String) : Fragment(), CellClickListener,
    CellLongClickListener {

/*
    var ctx:Context
    var tabTitle:String

    lateinit var adaptador: AdaptadorCustom


    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var crudProductes: TaulaProductesCrud
    lateinit var listaProductos: ArrayList<Producto>
    lateinit var crudRebost: RebostCrud
    lateinit var listaProductosRebost: ArrayList<Rebost>
    lateinit var toolbarLlistaProductes: androidx.appcompat.widget.Toolbar
    lateinit var rview: RecyclerView
    lateinit var tabLayout: TabLayout

    var isActionMode = false
    var actionMode: ActionMode? = null
    var callback: ActionMode.Callback? = null

    init{
        this.ctx = ctx
        this.tabTitle = tabTitle
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callback = object: ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                adaptador.iniciarActionMode()
                actionMode = mode
                when(tabTitle){
                    Constants.TAB_PRIMERA -> mode?.menuInflater?.inflate(R.menu.menu_contextual_productos_rebost, menu)
                    Constants.TAB_SEGONA -> mode?.menuInflater?.inflate(R.menu.menu_contextual_productos_comprar, menu)
                    Constants.TAB_TERCERA -> mode?.menuInflater?.inflate(R.menu.menu_contextual_productos_consultats, menu)
                }

                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = "0 Seleccionados"
                toolbarLlistaProductes.visibility = View.GONE
                return false
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                //TODO Canviar estructura pels dos menus
                when (item?.itemId) {
                    //MENU CONTEXTUAL REBOST
                    R.id.imEliminarProductos -> EliminarProducteRebost()
                    R.id.imConsumir ->          ConsumirRebost()
                    R.id.imAfegirComprar -> {
                                                afegirAComprar()
                                                ConsumirRebost()
                    }
                    //MENU CONTEXTUAL COMPRAR
                    R.id.imEliminarProductosComprar -> eliminarProducteComprar()
                    R.id.imComprat ->                  eliminarProducteComprar()
                    R.id.imAfegirARebost -> {
                                                       afegirARebost()
                                                       eliminarProducteComprar()
                            }
                    //MENU CONTEXTUAL CONSULTATS
                    R.id.imAfegirARebostConsultats -> afegirARebost()
                    R.id.imAfegirComprarConsultats -> afegirAComprar()
                    R.id.imEliminarConsultats -> eliminarConsultats()

                    else -> {
                        return true
                    }
                }
                carregaRecyclerViewLlistaProductes()
                adaptador.terminarActionMode()
                mode?.finish()
                isActionMode = false
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                adaptador.destruirActionMode()
                isActionMode = false
                toolbarLlistaProductes.visibility = View.VISIBLE
            }

        }

    }

    private fun eliminarConsultats() {
        for (id in adaptador.itemSeleccionados!!){
            val crudProducto = TaulaProductesCrud(ctx)
            crudProducto.deleteProducte(id)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun afegirARebost() {
        for (id in adaptador.itemSeleccionados!!){
            var crudComprar = ComprarCrud(ctx)
            var suma = crudComprar.getSumaQuantitats(id)
            if(suma.equals(0)) suma = 1
            var producteRebost= Rebost(0, id, Funcions.obtenirDataActualMillis(), suma, 0, 0, Funcions.obtenirDataActualMillis())

            RebostCrud(ctx).addProducteRebost(producteRebost)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun afegirAComprar() {
        for (id in adaptador.itemSeleccionados!!){
            crudRebost = RebostCrud(ctx)
            var suma = crudRebost.getSumaQuantitats(id)
            if(suma.equals(0)) suma = 1
            var producteComprar = Comprar(0, id, suma, Funcions.obtenirDataActualMillis(), 0)

            var crudComprar = ComprarCrud(ctx)
            crudComprar.addProducteComprar(producteComprar)
        }

    }

    private fun ConsumirRebost() {
        for (id in adaptador.itemSeleccionados!!){
            crudRebost = RebostCrud(ctx)
            crudRebost.updateConsumitRebost(id, 1)
        }
    }


    private fun eliminarProducteComprar() {
        for (id in adaptador.itemSeleccionados!!){
            val crudComprar = ComprarCrud(ctx)
            crudComprar.deletecomprar(id)
        }
    }

    private fun EliminarProducteRebost() {
        for (id in adaptador.itemSeleccionados!!){
            crudRebost = RebostCrud(ctx)
            crudRebost.deleterebost(id)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbarLlistaProductes = activity!!.findViewById(R.id.toolbarLlistaProductes)
        tabLayout = activity!!.findViewById(R.id.tab)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> tabTitle = Constants.TAB_PRIMERA
                    1 -> tabTitle = Constants.TAB_SEGONA
                    2 -> tabTitle = Constants.TAB_TERCERA
                }
               carregaRecyclerViewLlistaProductes()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_tabs_fragment, container, false)
        rview = view.findViewById(R.id.rview)
        carregaRecyclerViewLlistaProductes()
        return view
    }

    fun carregaRecyclerViewLlistaProductes(){
        rview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager (ctx)
        rview.layoutManager = layoutManager
        crudProductes = TaulaProductesCrud(ctx)
        listaProductos = arrayListOf()
        when(tabTitle) {
            Constants.TAB_PRIMERA -> {
                var crudLlistaProductes = TaulaProductesALlistesCrud(ctx)
                var productesdelaLlista = crudLlistaProductes.getProductesLlista(2)
                for(i in productesdelaLlista){
                    listaProductos.plusAssign(crudProductes.getProducte(i.code.toString()))
                }
            }
            Constants.TAB_SEGONA ->{
                var crudLlistaProductes = TaulaProductesALlistesCrud(ctx)
                var productesdelaLlista = crudLlistaProductes.getProductesLlista(1)
                for(i in productesdelaLlista){
                    listaProductos.plusAssign(crudProductes.getProducte(i.code.toString()))
                }
            }
            Constants.TAB_TERCERA ->{
                listaProductos = crudProductes.getProductes()
            }
        }
        adaptador = AdaptadorCustom (ctx, listaProductos, null, tabTitle, null, this, this)
        rview.adapter = adaptador
        *//*Log.d("CRRAGANTRV", "Carregant RV")
        rview.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(ctx)
        rview.layoutManager = layoutManager

        crudProductes = ProductesCrud(ctx)

        when(tabTitle){
            Constants.TAB_PRIMERA -> {

                //Carregar primera pestanya
                crudRebost = RebostCrud(ctx)
                listaProductosRebost = crudRebost.getProductesRebostNoConsumits()
                Log.d("LLISTAREBOST", listaProductosRebost.toString())
                listaProductos = crudProductes.getProductesRebost()
                Log.d("LLISTAREBOST", listaProductos.toString())
            }
            Constants.TAB_SEGONA -> {
                listaProductos = crudProductes.getProductesComprar()
            }
            else->{
                listaProductos = crudProductes.getProductes()
            }
        }
        adaptador = AdaptadorCustom(ctx, listaProductos, tabTitle, this, this)
        rview.adapter = adaptador*//*

    }*/

    override fun onCellClickListener(id: String?){
       /* if(!isActionMode) {
            Toast.makeText(ctx, "Click a: " + id, Toast.LENGTH_SHORT).show()
            *//*var intent = Intent(activity, ProductoDetalleActivity::class.java)
            intent.putExtra("DE", tabTitle)
            intent.putExtra("CODI", id)*//*
            var intent = Intent(activity, DetallProdBuscatsActivity::class.java)
            intent.putExtra("ID", id)
            startActivity(intent)
        } else{
            adaptador.seleccionarItem(id)
            actionMode?.title = adaptador.obtenerNumSeleccionados().toString() + " seleccionados"
        }*/
    }

    override fun onCellLongClickListener(id: String?): Boolean{
       /* Toast.makeText(ctx, "Click Llarg a: " + id, Toast.LENGTH_SHORT).show()
        if(!isActionMode){
            (activity as AppCompatActivity).setSupportActionBar(toolbarLlistaProductes)
            (activity as AppCompatActivity).startSupportActionMode(callback!!)
            isActionMode = true
            adaptador.seleccionarItem(id)
        }else{
            //hacer selecciones o deselecciones
            adaptador.seleccionarItem(id)
        }
        //inidicamos en el titulo el número de elementos seleccionados
        actionMode?.title = adaptador.obtenerNumSeleccionados().toString() + " seleccionados"*/
        return true

    }


    override fun onResume() {
        super.onResume()
        /*carregaRecyclerViewLlistaProductes()*/
    }





}