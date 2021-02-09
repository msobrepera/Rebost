package com.ymest.rebost.productesdecategoria

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListenerCompra
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.Llistes
import com.ymest.rebost.json.ProducteALlista
import com.ymest.rebost.json.Producto
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesCrud
import kotlinx.android.synthetic.main.activity_productes_llista_compra.*


class ProductesLlistaCompraActivity : AppCompatActivity(), CellClickListenerCompra, CellLongClickListener {
    var TAG = "ProductesLlistaCompraActivity"
    lateinit var idLlista: String
    var orden:Int = 0
    var textABuscar:String = ""
    //lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: AdaptadorLlistaCompra
    lateinit var crudTProducto: TaulaProductesCrud
    lateinit var productoSeleccionado: Producto
    lateinit var listaProductos: ArrayList<Producto>
    lateinit var itemsFiltro : Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productes_llista_compra)
        initViews()
        idLlista = intent.getStringExtra("IDLLISTA").toString()
        when(idLlista){
            "1" -> {
                toolbarProductesLlistaCompra.title =
                    TaulaLlistesCrud(this).getLlista(idLlista.toInt()).nomLlista
                carregaProductesLlista(idLlista, orden, textABuscar)
            }
        }
    }

    private fun carregaProductesLlista(idLlista: String, orden: Any, textABuscar: Any) {
        rvProducteLlistaCompra.setHasFixedSize(true)
        rvProducteLlistaCompra.layoutManager = LinearLayoutManager(this)
        crudTProducto = TaulaProductesCrud(this)
        listaProductos = arrayListOf()
        var productesdelaLlista: ArrayList<ProducteALlista> = TaulaProductesALlistesCrud(this).getProductesLlista(
            idLlista.toInt()
        )
        productesdelaLlista.sortBy {it.comprat}
        var filtrado:ArrayList<ProducteALlista> = ArrayList()
        //arrayOf("Ver Tot", "Pendientes de comprar", "Comprados")
        when(orden){
            0 -> {
                filtrado = productesdelaLlista
            }
            1 -> {
                filtrado = productesdelaLlista.filter { it ->
                    it.comprat == 0
                } as ArrayList<ProducteALlista>
            }
            2 -> {
                filtrado = productesdelaLlista.filter { it ->
                    it.comprat == 1
                } as ArrayList<ProducteALlista>
            }
        }


        for(i in filtrado){
            if (!listaProductos.contains(crudTProducto.getProducte(i.code.toString())))
                listaProductos.plusAssign(crudTProducto.getProducte(i.code.toString()))
        }


        viewAdapter = AdaptadorLlistaCompra(this, listaProductos, idLlista.toInt(), this, this)
        rvProducteLlistaCompra.adapter = viewAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contextual_prod_lista_compra, menu)
        var btnFiltro = menu?.findItem(R.id.imFiltro)
        var btnLimpiar = menu?.findItem(R.id.imCambiarVista)
        var btnEnviar  = menu?.findItem(R.id.imEnviarALista)

        btnFiltro?.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.imFiltro -> {
                    Log.d("FILTRA", "click en Filtra")
                    muestraADFiltraListaCompra()
                }

            }
            true
        }
        btnLimpiar?.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.imCambiarVista -> {
                    Log.d("FILTRA", "click en Limpiar")
                    muestraADEliminarComprados()
                }

            }
            true
        }

        btnEnviar?.setOnMenuItemClickListener {
            when (it?.itemId) {
                R.id.imEnviarALista -> {
                    Log.d("FILTRA", "click en enviar")
                    muestraADEnviarALista()
                }

            }
            true
        }

        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun muestraADEnviarALista() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Enviar productos a Lista")
        //alertDialog.setMessage("Los productos marcados como Comprados se van a enviar a la Lista seleccionada")
        var listas: ArrayList<Llistes> = arrayListOf()
        var llistesNom : Array<String> = arrayOf()
        listas = TaulaLlistesCrud(this).getAllLlista()
        for(llista in listas){
            if(llista.id!=3 && llista.id!=1) llistesNom += llista.nomLlista!!
        }
        llistesNom += getString(R.string.titulo_AfegirLlista)
        Log.d("LLISTES", llistesNom.size.toString())
        alertDialog.setItems(llistesNom, DialogInterface.OnClickListener { dialog, which ->
            if (which == llistesNom.size - 1) {
                Toast.makeText(this, "Click a Afegir: " + which, Toast.LENGTH_SHORT).show()
                //mostraAlertDialogNovaLlista()
            } else {
                var idLlistaNova = TaulaLlistesCrud(this).getIdLlista(llistesNom[which])
                TaulaProductesALlistesCrud(this).updateCanviLlista(1, idLlistaNova)
                carregaProductesLlista("1", 0,"")
                /*if (TaulaLlistesCrud(this).gestionaCantidad(idLlista)) {
                    mostraAlertDialogQuantitat(llistesNom[which])
                } else if (TaulaLlistesCrud(ctx).gestionaFechas(idLlista)) {
                    mostraAlertDialogCalendar(llistesNom[which], 0)
                } else {
                    afegirProducteALlista(llistesNom[which], 0, 0, 0)
                }*/

            }
        })

        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    private fun muestraADEliminarComprados() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.limpiar_comprados))
        builder.setMessage("Se van a eliminar de la lista los artículos marcados como comprados. Estás seguro?")
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            viewAdapter.deleteComprats()
            carregaProductesLlista("1", 0, "")
            dialog.dismiss()
        }
        builder.setNegativeButton("CERRAR"){dialog, which->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun muestraADFiltraListaCompra() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Filtrar por:")
        //val items = arrayOf("Puntuación", "Sabor", "Favorito", "Nombre", "Marca", "Popularidad")
        //val items = resources.getStringArray(R.array.array_orden)
        val checkedItem = 0
        itemsFiltro = resources.getStringArray(R.array.array_filtro_lista_compra)
        alertDialog.setSingleChoiceItems(itemsFiltro, checkedItem) { dialog, which ->
            orden = which
            carregaProductesLlista(idLlista, orden, textABuscar)
            Toast.makeText(
                this,
                "Clicked on: " + which + " - " + itemsFiltro.get(which),
                Toast.LENGTH_LONG
            ).show()
            dialog.dismiss()
        }
            val alert = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()
    }



    private fun initViews() {
        setSupportActionBar(toolbarProductesLlistaCompra)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        pbProductesLlistaCompra.visibility = View.GONE
    }

    override fun onCellClickListener(id: String?, view: View) {
        when(view.id){
            R.id.ivMarcarComprado -> {
                viewAdapter.marcarComoComprado(id.toString())
            }
            R.id.tvQuantityLlistaCompra -> {
                viewAdapter.mostraADNumberPicker(id.toString())
            }
        }
    }



    override fun onCellLongClickListener(id: String?): Boolean {
        TODO("Not yet implemented")
    }
}