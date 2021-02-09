package com.ymest.rebost.productesdecategoria

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ymest.rebost.R
import com.ymest.rebost.detallproductesbuscats.DetallProdBuscatsActivity
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.ListaPersonalizadaProducto
import com.ymest.rebost.json.Personalitzat
import com.ymest.rebost.json.ProducteALlista
import com.ymest.rebost.json.ProductesXCategoria.Product
import com.ymest.rebost.json.ProductesXCategoria.ProductosXCategoria
import com.ymest.rebost.json.Producto
import com.ymest.rebost.llistatproductes.AdaptadorCustom
import com.ymest.rebost.scan.ScanActivity
import com.ymest.rebost.sqlite.*
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions
import com.ymest.rebost.utils.Network
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_productes_de_categoria.*
import kotlinx.android.synthetic.main.alert_dialog_nova_llista.view.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*
import kotlinx.android.synthetic.main.layout_fab_submenu_add_producte.*
import java.util.*
import kotlin.collections.ArrayList


class ProductesDeCategoriaActivity : AppCompatActivity(), CellClickListener, CellLongClickListener {

    val network = Network(this)
    lateinit var listaProdCat : ArrayList<Product>
    lateinit var productoAPI: ProductosXCategoria
    lateinit var convertidoAProducto: ArrayList<Producto>
    lateinit var idCat: String
    lateinit var nomCat: String
    var items_pag: Int = 0
    var prod_trobats: Int = 0
    var pag_max: Int = 0
    var pagina: Int = 1
    var busca: Boolean = false
    lateinit var buscaView: SearchView
    var textABuscar: String = ""
    lateinit var vede:String
    lateinit var idLlista: String
    lateinit var idUbic: String
    var idLlistaNum: Int = 0
    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: AdaptadorCustom

    lateinit var crudTProducto: TaulaProductesCrud
    lateinit var productoSeleccionado: Producto
    lateinit var listaProductos: ArrayList<Producto>
    lateinit var listaProductosPersonalizados: ArrayList<ListaPersonalizadaProducto>
    var orden:Int = 0
    lateinit var itemsOrden : Array<String>

    var fabExpanded: Boolean = false
    lateinit var fabAddPorducte: FloatingActionButton
    lateinit var layoutBuscar: ConstraintLayout
    lateinit var layoutScan: ConstraintLayout
    lateinit var layoutAddProducte: ConstraintLayout

    var isActionMode = false
    var actionMode: ActionMode? = null
    var callback: ActionMode.Callback? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_productes_de_categoria)

        callback = object: ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                viewAdapter.iniciarActionMode()
                actionMode = mode
                mode?.menuInflater?.inflate(R.menu.menu_contextual_lista_productos, menu)
                if(vede == Constants.HISTORIAL
                    || vede == Constants.MAINACT
                    || vede == Constants.CATEGORIES
                    || vede == Constants.MARQUES){
                    var btnEliminar = menu?.findItem(R.id.imDeleteDeLista)
                    btnEliminar?.isVisible = false
                }
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = "0 Seleccionados"
                toolbarProductesDeCategoria.visibility = View.GONE
                return false
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.im_addToList ->{

                    }
                    R.id.imDeleteDeLista->{
                        mostraADEliminarArticulo()
                    }
                }
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                viewAdapter.destruirActionMode()
                isActionMode = false
                toolbarProductesDeCategoria.visibility = View.VISIBLE
            }

        }

        fabAddPorducte = findViewById(R.id.fabAddProducte)
        layoutBuscar = findViewById(R.id.layoutBuscar)
        layoutScan = findViewById(R.id.layoutEscaner)

        initViews()
        initVars()

        vede = intent.getStringExtra("VEDE").toString()
        when (vede){
            Constants.MISLISTAS -> {
                idLlista = intent.getStringExtra("IDLLISTA").toString()
                if (idLlista=="3") fabAddPorducte.visibility = View.GONE
                nomCat = ""
                idCat = ""
                toolbarProductesDeCategoria.title =
                    TaulaLlistesCrud(this).getLlista(idLlista.toInt()).nomLlista
                carregaProductesLlista(idLlista, orden, textABuscar)
            }
            Constants.MISUBICACIONES->{
                idUbic = intent.getStringExtra("IDLLISTA").toString()
                nomCat = ""
                idCat = ""
                toolbarProductesDeCategoria.title =  TaulaUbicacionsCrud(this).getUbicacio(idUbic.toInt()).nomubicacio
                carregaProductesLlista(idUbic, orden, textABuscar)
            }
            Constants.CATEGORIES, Constants.MARQUES -> {
                idLlista = "0"
                nomCat = intent.getStringExtra("NOM").toString()
                idCat = nomCat.replace(" ", "-").toLowerCase(Locale.ROOT)
                toolbarProductesDeCategoria.title = nomCat
                carregaProductesCategoria(pagina, 0, busca, textABuscar)
            }
            Constants.MAINACT ->{
                textABuscar = intent.getStringExtra("STRINGABUSCAR").toString()
                if(intent.getStringExtra("IDLLISTA").toString().isNullOrEmpty()){
                    idLlista = "0"
                }else{
                    idLlista = intent.getStringExtra("IDLLISTA").toString()
                }
                Log.d("IDLLISTA", "VEDE MAINACT: " + idLlista)
                busca = true
                idCat = ""
                carregaProductesCategoria(pagina, 0, busca, textABuscar)
            }
            Constants.HISTORIAL ->{
                idLlista = "0"
                toolbarProductesDeCategoria.title = Constants.HISTORIAL
                carregaProductesHistorial()
            }
        }

        rvProducteDeCategoria.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recarregaRecyclerView(recyclerView)
            }
        })

        fabAddPorducte.setOnClickListener {
            if(fabExpanded) tancarSubMenuFAB() else obrirSubMenuFAB()
        }

        fabSubmenuBusca.setOnClickListener {
            intent = Intent(this, ProductesDeCategoriaActivity::class.java)
            intent.putExtra("VEDE", Constants.MAINACT)
            intent.putExtra("STRINGABUSCAR", "")
            intent.putExtra("IDLLISTA", idLlista)
            Log.d("IDLLISTA", " FABSUBMENUBUSCA: " + idLlista)

            startActivity(intent)
        }

        fabSubmenuScan.setOnClickListener {
            intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("IDLLISTA", idLlista)
            startActivity(intent)
        }

        tancarSubMenuFAB()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mostraADEliminarArticulo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.EliminardeLista))
        builder.setMessage("Se van a eliminar todos los artículos seleccionados de la lista. Quieres continuar?")
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            viewAdapter.deleteSelccionados()
            carregaProductesLlista(idLlista, orden, textABuscar)

            dialog.dismiss()
        }
        builder.setNegativeButton("CERRAR"){dialog, which->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    fun tancarSubMenuFAB(){
        layoutBuscar.visibility = View.GONE
        layoutScan.visibility = View.GONE
        fabAddPorducte.setImageResource(R.drawable.ic_baseline_add_24)
        fabExpanded = false
    }

    fun obrirSubMenuFAB(){
        layoutBuscar.visibility = View.VISIBLE
        layoutScan.visibility = View.VISIBLE
        fabAddPorducte.setImageResource(R.drawable.ic_baseline_close_24)
        fabExpanded = true
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRestart() {
        when(vede){
            Constants.MISLISTAS ->{
                carregaProductesLlista(idLlista, orden, textABuscar)
            }
            Constants.MISUBICACIONES ->{
                carregaProductesLlista(idUbic, orden, textABuscar)
            }

            Constants.HISTORIAL ->{
                carregaProductesHistorial()
            }
            else->{
                recarregaRecyclerView(rvProducteDeCategoria)
            }
        }

        super.onRestart()
    }

    private fun carregaProductesHistorial() {
        Log.d("TAG1", "carregaProductesHistorial")
        rvProducteDeCategoria.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rvProducteDeCategoria.layoutManager = layoutManager
        crudTProducto = TaulaProductesCrud(this)
        listaProductos = arrayListOf()
        listaProductos = crudTProducto.getProductes()
        viewAdapter = AdaptadorCustom(this, listaProductos, null, Constants.HISTORIAL, null, this, this)
        Log.d("TAG1", listaProductos.toString())
        rvProducteDeCategoria.adapter = viewAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun carregaProductesLlista(idLlista: String, orden:Int, txtABuscar: String) {
        rvProducteDeCategoria.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rvProducteDeCategoria.layoutManager = layoutManager
        crudTProducto = TaulaProductesCrud(this)
        listaProductos = arrayListOf()
        listaProductosPersonalizados = arrayListOf()
        var crudLlistaProductes = TaulaProductesALlistesCrud(this)
        var productesdelaLlista: ArrayList<ProducteALlista> = arrayListOf()
        when (vede){
            Constants.MISLISTAS->{
                if (idLlista.toInt() == 3){
                    productesdelaLlista = crudLlistaProductes.getCaducats(Funcions.obtenirDataActualMillis())
                }else{
                    productesdelaLlista = crudLlistaProductes.getProductesLlista(idLlista.toInt())
                }
            }
            Constants.MISUBICACIONES->{
                productesdelaLlista = crudLlistaProductes.getProductesdeUbicacio(idLlista.toInt())
            }
        }

        for(i in productesdelaLlista){
            if (!listaProductos.contains(crudTProducto.getProducte(i.code.toString()))) listaProductos.plusAssign(crudTProducto.getProducte(i.code.toString()))

        }
        for(i in listaProductos){
            var producto=  i.product
            var prodPers = TaulaPersonalitzadaCrud(this).getProductePersonalitzat(i.code)
            if(!listaProductosPersonalizados.contains(ListaPersonalizadaProducto(producto, i.code, "", 0, prodPers))){
                listaProductosPersonalizados.add(ListaPersonalizadaProducto(producto, i.code, "", 0, prodPers))
            }

        }
        //arrayOf("Puntuación", "Sabor", "Favorito", "Nombre", "Marca", "Popularidad")
        when(orden){
            0 -> listaProductosPersonalizados.sortByDescending { it.personalitzat?.puntuacio }
            1 -> listaProductosPersonalizados.sortBy { it.personalitzat?.gust }
            2 -> listaProductosPersonalizados.sortByDescending { it.personalitzat?.favorit }
            3 -> listaProductosPersonalizados.sortBy { it.product?.productNameEs }
            4 -> listaProductosPersonalizados.sortBy { it.product?.brands }
            5 -> listaProductosPersonalizados.sortBy { it.product?.uniqueScansN }
        }

        var filtrado = listaProductosPersonalizados.filter {
                s -> !s.product?.productNameEs.isNullOrEmpty() && s.product?.productNameEs?.toLowerCase(Locale.ROOT)!!.contains(txtABuscar.toLowerCase(Locale.ROOT))||
                                             !s.product?.productName.isNullOrEmpty() && s.product?.productName?.toLowerCase(Locale.ROOT)!!.contains(txtABuscar.toLowerCase(Locale.ROOT))||
                                             !s.product?.genericNameEs.isNullOrEmpty() && s.product?.genericNameEs?.toLowerCase(Locale.ROOT)!!.contains(txtABuscar.toLowerCase(Locale.ROOT))||
                                             !s.product?.genericName.isNullOrEmpty() && s.product?.genericName?.toLowerCase(Locale.ROOT)!!.contains(txtABuscar.toLowerCase(Locale.ROOT))||
                                             !s.product?.brands.isNullOrEmpty() && s.product?.brands?.toLowerCase(Locale.ROOT)!!.contains(txtABuscar.toLowerCase(Locale.ROOT)) ||
                                             !s.product?.code.isNullOrEmpty() && s.product?.code!!.contains(txtABuscar) ||
                                             !s.product?.stores.isNullOrEmpty() && s.product?.stores?.toLowerCase(Locale.ROOT)!!.contains(txtABuscar.toLowerCase(Locale.ROOT))
        } as ArrayList<ListaPersonalizadaProducto>
        viewAdapter = AdaptadorCustom(this, null, filtrado, vede, idLlista.toInt(), this, this)
        rvProducteDeCategoria.adapter = viewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contextual_prod_categories, menu)
        var btnbusca = menu?.findItem(R.id.imSearchProdCategories)
        var btnFiltro = menu?.findItem(R.id.imFiltro)
        if(vede==Constants.MISUBICACIONES) idLlista = idUbic
        when (vede){

            Constants.MISLISTAS, Constants.MISUBICACIONES -> {
                btnFiltro?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when (item?.itemId) {
                            R.id.imFiltro -> {
                                Log.d("CLICKA: ", item?.itemId.toString())
                                Snackbar.make(
                                    toolbarProductesDeCategoria,
                                    "CLICK A: " + item.title,
                                    Snackbar.LENGTH_LONG
                                ).show()
                                muestraAlertDialogOrdenar()
                            }

                        }
                        return true
                    }
                })
                buscaView = btnbusca?.actionView as android.widget.SearchView
                buscaView.queryHint = "Producto, marca, código, ..."

                buscaView.setOnCloseListener(object : SearchView.OnCloseListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onClose(): Boolean {
                        Log.d("BuscaView", "Busqueda cerrada")
                        busca = false
                        textABuscar = ""
                        carregaProductesLlista(idLlista, orden, textABuscar)
                        return false
                    }
                })
                buscaView.setOnQueryTextListener(object :
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText?.length!! > 2) {
                            textABuscar = newText
                            busca = true
                            carregaProductesLlista(idLlista, orden, textABuscar)
                        }else{
                            textABuscar = ""
                            busca = true
                            carregaProductesLlista(idLlista, orden, textABuscar)
                        }
                        return true
                    }
                })
            }

            Constants.MARQUES,
            Constants.CATEGORIES,
            Constants.MAINACT -> {

                btnFiltro?.isVisible = false
                buscaView = btnbusca?.actionView as android.widget.SearchView
                buscaView.setQuery(textABuscar,false)
                buscaView.queryHint = "Escribe el producto..."

                buscaView.setOnCloseListener(object : SearchView.OnCloseListener {
                    override fun onClose(): Boolean {
                        Log.d("BuscaView", "Busqueda cerrada")
                        busca = false
                        pagina = 1
                        convertidoAProducto.clear()
                        textABuscar = ""
                        carregaProductesCategoria(pagina, 0, busca, textABuscar)
                        return false
                    }

                })

                buscaView.setOnQueryTextListener(object :
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText?.length!! > 2) {
                            textABuscar = newText
                            busca = true
                            convertidoAProducto.clear()
                            carregaProductesCategoria(pagina, 0, busca, textABuscar)
                        }
                        return true
                    }
                })
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun muestraAlertDialogOrdenar() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Ordenar por:")
        //val items = arrayOf("Puntuación", "Sabor", "Favorito", "Nombre", "Marca", "Popularidad")
        //val items = resources.getStringArray(R.array.array_orden)
        val checkedItem = 0
        itemsOrden = resources.getStringArray(R.array.array_orden)
        alertDialog.setSingleChoiceItems(
            itemsOrden, checkedItem
        ) { dialog, which ->
            orden = which
            carregaProductesLlista(idLlista, orden, textABuscar)
            Toast.makeText(this, "Clicked on: " + which + " - " + itemsOrden.get(which) , Toast.LENGTH_LONG).show()
            dialog.dismiss()

            //FER UN SELECT ON HI HAGI TOTS ELS CAMPS NECESSARIS PER PODER ORDENAR

            /*when (which) {
                0 ->
                1 -> Toast.makeText(this, "Clicked on android", Toast.LENGTH_LONG)
                    .show()
                2 -> Toast.makeText(
                    this,
                    "Clicked on Data Structures",
                    Toast.LENGTH_LONG
                ).show()
                3 -> Toast.makeText(this, "Clicked on HTML", Toast.LENGTH_LONG).show()
                4 -> Toast.makeText(this, "Clicked on CSS", Toast.LENGTH_LONG).show()
            }*/
        }
        /*alertDialog.setPositiveButton("ACEPTAR"){ dialog, which ->
            Snackbar.make(toolbarProductesDeCategoria,"Clicked on: " + which + " - " + items.get(which),Snackbar.LENGTH_LONG).show()
        }*/
        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

    private fun recarregaRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        if (layoutManager!!.findLastCompletelyVisibleItemPosition() >= layoutManager.itemCount -1 ) {
            pagina++
            if (pagina <= pag_max) {
                carregaProductesCategoria(
                    pagina,
                    layoutManager.findFirstCompletelyVisibleItemPosition(),
                    busca,
                    textABuscar
                )
                pbProductesCategoria.visibility = View.GONE
            } else {
                Toast.makeText(applicationContext, "No hi ha més elements", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initVars() {
        listaProdCat = ArrayList()
        convertidoAProducto = ArrayList()

    }

    private fun initViews() {
        setSupportActionBar(toolbarProductesDeCategoria)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        pbProductesCategoria.visibility = View.GONE
    }

    private fun carregaProductesCategoria(pag: Int, pos: Int, busca: Boolean, newText: String) {
        pbProductesCategoria.visibility = View.VISIBLE
            if (network.hayRed()) {
                val queue = Volley.newRequestQueue(this)
                var url: String = ""
                var tagtype0: String = ""
                when(vede) {
                    Constants.CATEGORIES -> tagtype0 = "categories"
                    Constants.MARQUES -> tagtype0 = "brands"
                    Constants.MAINACT -> tagtype0 = ""
                }
                if (busca){
                    when(vede){
                        Constants.CATEGORIES, Constants.MARQUES ->{
                            url = "https://es.openfoodfacts.org/cgi/search.pl?search_terms=$newText&search_simple=1&tagtype_0=$tagtype0&tag_contains_0=contains&tag_0=$idCat&page=$pagina&action=process&json=1"
                        }
                        Constants.MAINACT ->{
                            url =  "https://es.openfoodfacts.org/cgi/search.pl?search_terms=$newText&search_simple=1&action=process&page=$pagina&json=1"
                        }
                    }

                }else{
                    when(vede){
                        Constants.CATEGORIES -> url =
                            Constants.URL_API_PRODUCTES_CATEGORIA + idCat + ".json/" + pag.toString()
                        Constants.MARQUES -> url =
                            Constants.URL_API_PRODUCTES_MARCA + idCat + ".json/" + pag.toString()
                        Constants.MAINACT -> url = "https://es.openfoodfacts.org/cgi/search.pl?search_terms=&search_simple=1&action=process&page=$pagina&json=1"
                    }
                }
                Log.d("VARURL", url)


                val request = StringRequest(
                    Request.Method.GET, url, { response ->
                        val gson = Gson()
                        productoAPI = gson.fromJson(response, ProductosXCategoria::class.java)
                        prod_trobats = productoAPI.count
                        items_pag = productoAPI.page_size
                        pag_max =
                            kotlin.math.ceil(prod_trobats.toDouble() / items_pag.toDouble()).toInt()
                        if (prod_trobats > 0) {
                            for (p in productoAPI.products) {
                                comprovaArray(p)
                                if(!p.id.isNullOrEmpty()){

                                    listaProdCat.add(p)
                                    convertidoAProducto.add(
                                        Producto(
                                            com.ymest.rebost.json.Product(
                                                productNameEs = p.product_name_es,
                                                productName = p.product_name,
                                                genericName = p.generic_name,
                                                genericNameEs = p.generic_name_es,
                                                imageUrl = p.image_url,
                                                brands = p.brands
                                            ), p.id, "", 0
                                        )
                                    )
                                }
                            }
                            carregaRecyclerViewProdCategories(convertidoAProducto, pos, newText)
                        } else {
                            //Toast.makeText(applicationContext, "Sense resultats", Toast.LENGTH_SHORT).show()
                            carregaRecyclerViewProdCategories(convertidoAProducto, pos, newText)
                        }
                    },
                    {
                        Toast.makeText(this, "No hi ha resposta del servidor", Toast.LENGTH_SHORT)
                            .show()
                        pbProductesCategoria.visibility = View.GONE
                    })
                queue.add(request)
            } else {
                Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show()
                pbProductesCategoria.visibility = View.GONE
            }
        }

    private fun comprovaArray(p: Product) {
        if(p._keywords.isNullOrEmpty())     p._keywords = listOf()
        if(p.brands.isNullOrEmpty())              p.brands = ""
        if(p.categories.isNullOrEmpty())          p.categories = ""
        if(p.generic_name.isNullOrEmpty())        p.generic_name = ""
        if(p.generic_name_es.isNullOrEmpty())     p.generic_name_es = ""
        if(p.image_front_url.isNullOrEmpty())     p.image_front_url = ""
        if(p.image_nutrition_url.isNullOrEmpty()) p.image_nutrition_url = ""
        if(p.image_thumb_url.isNullOrEmpty())     p.image_thumb_url = ""
        if(p.image_url.isNullOrEmpty())           p.image_url = ""
        if(p.pnns_groups_1.isNullOrEmpty())       p.pnns_groups_1 = ""
        if(p.pnns_groups_2.isNullOrEmpty())       p.pnns_groups_2 = ""
        if(p.product_name.isNullOrEmpty())        p.product_name = ""
        if(p.product_name_es.isNullOrEmpty())     p.product_name_es = ""
        if(p.purchase_places.isNullOrEmpty())     p.purchase_places = ""
        if(p.url.isNullOrEmpty())                 p.url = ""
        if(p.stores_tags.isNullOrEmpty())   p.stores_tags = listOf()
    }

    private fun carregaRecyclerViewProdCategories(lista: ArrayList<Producto>, pos: Int, txtABuscar: String) {
        rvProducteDeCategoria.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rvProducteDeCategoria.layoutManager = layoutManager
        viewAdapter = AdaptadorCustom(this, lista, null,"LISTPRODCAT", null,this, this)
        //viewAdapter = AdaptadorCustom(this, lista, null,Constants.MAINACT, null,this, this)
        rvProducteDeCategoria.adapter = viewAdapter
        rvProducteDeCategoria.scrollToPosition(pos)
        pbProductesCategoria.visibility = View.GONE

        //arrayOf("Puntuación", "Sabor", "Favorito", "Nombre", "Marca", "Popularidad")
        when(orden){
            3 -> lista.sortBy { it.product?.productNameEs }
            4 -> lista.sortBy { it.product?.brands }
            5 -> lista.sortBy { it.product?.uniqueScansN }
        }

        /*//listaProductosPersonalizados.filter { listaProductosPersonalizados -> listaProductosPersonalizados.personalitzat?.gust!!.equals(1) }
        var filtrado = lista.filter {
                s -> s.product?.productNameEs!!.toLowerCase().contains(txtABuscar.toLowerCase()) ||
                s.product?.brands!!.toLowerCase().contains(txtABuscar.toLowerCase()) ||
                s.product?.code!!.contains(txtABuscar) ||
                s.product?.stores!!.toLowerCase().contains(txtABuscar.toLowerCase())
        } as ArrayList<Producto>*/

    }

    override fun onCellClickListener(id: String?) {
        if(!isActionMode){
            Toast.makeText(this, "Click en: " + id, Toast.LENGTH_SHORT).show()
            intent = Intent(this, DetallProdBuscatsActivity::class.java)
            intent.putExtra("ID", id)
            intent.putExtra("VEDE", vede)
            intent.putExtra("IDLL", idLlista)

            Log.d("VARS", "ID: " + id)
            Log.d("VARS", "VEDE: " + vede)
            Log.d("IDLLISTA", " INTENT PUT EXTRA IDLLISTA: " + idLlista)
            startActivity(intent)
        }else{
            viewAdapter.seleccionarItem(id)
            actionMode?.title = viewAdapter.obtenerNumSeleccionados().toString() + " seleccionados"
        }

    }

    override fun onCellLongClickListener(id: String?): Boolean {
        Toast.makeText(this, "Click LLARG en: " + id, Toast.LENGTH_SHORT).show()
        Log.d("VEDE", vede)

        if(!isActionMode){
            this.setSupportActionBar(toolbarProductesDeCategoria)
            this.startSupportActionMode(callback!!)
            isActionMode = true
            viewAdapter.seleccionarItem(id)
        }else{
            //hacer selecciones o deselecciones
            viewAdapter.seleccionarItem(id)
        }
        //inidicamos en el titulo el número de elementos seleccionados
        actionMode?.title = viewAdapter.obtenerNumSeleccionados().toString() + " seleccionados"

        return true
    }

}




