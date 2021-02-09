package com.ymest.rebost.detallproductesbuscats.ui.main

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.imatges.CarregaLlistaImatges
import com.ymest.rebost.json.Personalitzat
import com.ymest.rebost.json.ProducteALlista
import com.ymest.rebost.json.Producto
import com.ymest.rebost.json.Ubicacio
import com.ymest.rebost.nutricio.CarregaLlistaNutrients
import com.ymest.rebost.nutricio.Nutricio
import com.ymest.rebost.productesdecategoria.ProductesDeCategoriaActivity
import com.ymest.rebost.sqlite.*
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions
import kotlinx.android.synthetic.main.alert_dialog_add_producte_llista.view.*
import kotlinx.android.synthetic.main.alert_dialog_nova_llista.view.*
import kotlinx.android.synthetic.main.alert_dialog_nova_ubicacio.view.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment(var ctx: Context) : Fragment(), View.OnClickListener, CellClickListener{
    lateinit var producto: Producto
    lateinit var nomProducte : TextView
    lateinit var marcaProducte : TextView
    lateinit var codiProducte : TextView
    lateinit var nomLlista : TextView
    lateinit var imatgeProducte : ImageView
    lateinit var imatgeNova: ImageView
    lateinit var txtNova: TextView
    lateinit var ivHelpNova: ImageView
    lateinit var imatgeNutriScore : ImageView
    lateinit var txtNutriScore: TextView
    lateinit var ivHelpNutriScore: ImageView
    lateinit var txtCantidad: TextView
    lateinit var txtCategoria: TextView
    lateinit var txtIngredients: TextView
    lateinit var imatgeIconoCantidad : ImageView
    lateinit var cvIngredients : CardView

    lateinit var ivSemaforoGrasas: ImageView
    lateinit var ivSemaforoGrasasSat: ImageView
    lateinit var ivSemaforoSucre: ImageView
    lateinit var ivSemaforoSal: ImageView
    lateinit var tvSemaforoGrasas: TextView
    lateinit var tvSemaforoGrasasSat: TextView
    lateinit var tvSemaforoSucre: TextView
    lateinit var tvSemaforoSal: TextView
    lateinit var cvInfoNutricion: CardView

    lateinit var ivFavorito: ImageView
    var esFavorito: Boolean = false

    lateinit var ivSabor: ImageView


    lateinit var cvCategories: CardView
    lateinit var tvCategories: TextView

    var codi: String = ""
    var idLlista:Int =0

    private lateinit var pageViewModel: PageViewModel
    private var pestanya: Int = 1
    var prod : ArrayList<Producto> = ArrayList()

    lateinit var nomProducteIngredients: TextView
    lateinit var imatgeProducteIngredients: ImageView
    lateinit var marcaProducteIngredients: TextView
    lateinit var codiProducteIngredients:TextView
    lateinit var ivNovaIngredients: ImageView
    lateinit var tvNovaIngredients: TextView
    lateinit var tvIngredientsIngredients: TextView
    lateinit var btnAddFechaCad: Button
    //lateinit var ivDeleteFecha:ImageView


    lateinit var cvTablaNutricional: CardView
    lateinit var tvNutriente: TextView
    lateinit var ivNutricioBuscats: ImageView
    lateinit var tvNomArticleNutricioBuscats: TextView
    lateinit var tvMarcaNutricioBuscats: TextView
    lateinit var tvCodiNutricioBuscats: TextView
    lateinit var rvNutricioBuscats: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adaptador: AdaptadorNutricio
    lateinit var llistaNutrients: ArrayList<Nutricio>

    lateinit var tvNomArticleImatgesBuscats: TextView
    lateinit var tvMarcaImatgesBuscats: TextView
    lateinit var tvCodiImatgesBuscats: TextView
    lateinit var rvImatgesBuscats: RecyclerView
    lateinit var llistaImatges: ArrayList<String>
    //lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adaptadorImatges: AdaptadorImatges

    lateinit var crudTProducto: TaulaProductesCrud
    lateinit var crudTProductoPersonalitzat: TaulaPersonalitzadaCrud
    lateinit var productePersonalitzat: Personalitzat
    lateinit var crudLlistes: TaulaLlistesCrud

    lateinit var cvDatesCaducitat: CardView
    lateinit var rvDatesCaducitat: RecyclerView
    lateinit var adaptadorCad: AdaptadorCaducitat

    lateinit var btnpositive: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            pestanya = arguments?.getInt(ARG_SECTION_NUMBER) ?: 1
            codi = arguments?.getString("CODIBARRES").toString()
            idLlista = arguments?.getInt("IDLL", 0)!!.toInt()
            Log.d("IDLLISTA", "ONCREATE IDLLISTA: " + idLlista)
            setIndex(pestanya)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root: View
        when(pestanya){
            1 -> root = inflater.inflate(R.layout.fragment_detall_prod_buscats, container, false)
            2 -> root = inflater.inflate(R.layout.fragment_detall_prod_buscats_2, container, false)
            3 -> root = inflater.inflate(R.layout.fragment_detall_prod_buscats_3, container,false)
            4 -> root = inflater.inflate(R.layout.fragment_detall_prod_buscats_4, container,false)
            else -> root = inflater.inflate(R.layout.fragment_detall_prod_buscats, container, false)
        }

        initViews(root)
        if(codi.isNotEmpty()){
            cargaInfoProducto(codi)
        }
        return root
    }

    private fun AfegirATaulaProductes() {
        Log.d("SUMREBOST", producto.toString())
        crudTProducto = TaulaProductesCrud(ctx)
        crudTProducto.addProducte(producto)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun AfegirATaulaPersonalitzada() {
        productePersonalitzat = Personalitzat(producto.code, Funcions.obtenirDataActualMillis().toInt(), Funcions.obtenirDataActualMillis().toInt(), 0,2,3.5 )
        crudTProductoPersonalitzat = TaulaPersonalitzadaCrud(ctx)
        crudTProductoPersonalitzat.addProductePersonalitzat(productePersonalitzat)
    }

    fun initViews(v: View){
        when(pestanya) {
            1 -> {
                nomProducte = v.findViewById(R.id.tvNomArticleDetallBuscats)
                imatgeProducte = v.findViewById(R.id.ivDetallBuscats)
                marcaProducte = v.findViewById(R.id.tvMarcaDetallBuscats)
                codiProducte = v.findViewById(R.id.tvCodiDetallBuscats)
                nomLlista = v.findViewById(R.id.tvNomLlistaStock)
                imatgeNova = v.findViewById(R.id.ivNovaDetallBuscats)
                txtNova = v.findViewById(R.id.tvNovaDetallBuscats)
                imatgeNutriScore = v.findViewById(R.id.ivNutriScore)
                txtNutriScore = v.findViewById(R.id.tvNutriScore)
                txtCantidad = v.findViewById(R.id.tvCantidadDetallBuscats)
                txtCategoria = v.findViewById(R.id.tvCategoriaDetallBuscats)
                txtIngredients = v.findViewById(R.id.tvIngredientsDetallBuscats)
                imatgeIconoCantidad = v.findViewById(R.id.ivIconoCantidad)
                cvIngredients = v.findViewById(R.id.cvIngredientsDetallBuscats)
                ivSemaforoGrasas = v.findViewById(R.id.ivSemaforoGrasas)
                ivSemaforoGrasasSat = v.findViewById(R.id.ivSemaforoGrasasSat)
                ivSemaforoSucre = v.findViewById(R.id.ivSemaforoSucre)
                ivSemaforoSal = v.findViewById(R.id.ivSemaforoSal)
                tvSemaforoGrasas = v.findViewById(R.id.tvSemaforoGrasas)
                tvSemaforoGrasasSat = v.findViewById(R.id.tvSemaforoGrasasSat)
                tvSemaforoSucre = v.findViewById(R.id.tvSemaforoSucre)
                tvSemaforoSal = v.findViewById(R.id.tvSemaforoSal)
                cvInfoNutricion = v.findViewById(R.id.cvInfoNutricionalDetallBuscats)
                cvCategories = v.findViewById(R.id.cvCategoriesDetallBuscats)
                tvCategories = v.findViewById(R.id.tvCategoriesDetallBuscats)
                ivSabor = v.findViewById(R.id.ivSaborDetallBuscats)
                ivSabor.tag = 2
                ivHelpNova = v.findViewById(R.id.ivHelpNova)
                ivHelpNutriScore = v.findViewById(R.id.ivHelpNutriScore)
                ivFavorito = v.findViewById(R.id.ivFavoritoDetallBuscats)
                cvDatesCaducitat = v.findViewById(R.id.cvDatesCaducitat)
                rvDatesCaducitat = v.findViewById(R.id.rvDatesCad)
                btnAddFechaCad = v.findViewById(R.id.btnAddFechaCad)
                //ivDeleteFecha = v.findViewById(R.id.ivDeleteFechaCad)


            }
            2 -> {
                nomProducteIngredients = v.findViewById(R.id.tvNomArticleIngredientsBuscats)
                imatgeProducteIngredients = v.findViewById(R.id.ivDetallIngredientsBuscats)
                marcaProducteIngredients = v.findViewById(R.id.tvMarcaIngredientsBuscats)
                codiProducteIngredients = v.findViewById(R.id.tvCodiIngredientsBuscats)
                ivNovaIngredients = v.findViewById(R.id.ivNovaIngredientsBuscats)
                tvNovaIngredients = v.findViewById(R.id.tvNovaIngredientsBuscats)
                tvIngredientsIngredients = v.findViewById(R.id.tvLlistaIngredientsBuscats)

            }
            3 -> {
                tvNutriente = v.findViewById(R.id.tvNutriente)
                tvNomArticleNutricioBuscats = v.findViewById(R.id.tvNomArticleNutricioBuscats)
                ivNutricioBuscats = v.findViewById(R.id.ivNutricioBuscats)
                tvMarcaNutricioBuscats = v.findViewById(R.id.tvMarcaNutricioBuscats)
                tvCodiNutricioBuscats = v.findViewById(R.id.tvCodiNutricioBuscats)
                rvNutricioBuscats = v.findViewById(R.id.rvNutricioBuscats)
                cvTablaNutricional = v.findViewById(R.id.cvTablaNutricional)
            }
            4 -> {
                tvNomArticleImatgesBuscats = v.findViewById(R.id.tvNomArticleImatgesBuscats)
                tvMarcaImatgesBuscats = v.findViewById(R.id.tvMarcaImatgesBuscats)
                tvCodiImatgesBuscats = v.findViewById(R.id.tvCodiImatgesBuscats)
                rvImatgesBuscats = v.findViewById(R.id.rvImatgesBuscats)
            }
        }


    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun cargaInfoProducto(codi: String) {
        var nombre: String = ""
        val queue = Volley.newRequestQueue(context)
        val url = "https://es.openfoodfacts.org/api/v0/product/" + codi + ".json"
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                var gson = Gson()
                Log.d("RESPVOLLEY", response.toString())
                producto = gson.fromJson(response, Producto::class.java)
                crudTProducto = TaulaProductesCrud(ctx)
                crudTProductoPersonalitzat = TaulaPersonalitzadaCrud(ctx)
                if(!crudTProducto.existeProducto(codi)) AfegirATaulaProductes()
                if(!crudTProductoPersonalitzat.existeProductoPersonalitzat(codi))
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) AfegirATaulaPersonalitzada()

                when (pestanya) {
                    1 -> carregaPrimeraPestanya(producto)
                    //1 -> nomProducte.text = producto.product.product_name_es
                    2 -> carregaSegonaPestanya(producto)
                    //2 -> nomProducte.text = producto.product?.brands
                    3 -> carregaTerceraPestanya(producto)
                    //3 -> nomProducte.text = producto.product?.imageUrl
                    4 -> carregaQuartaPestanya(producto)
                }
                //nombre = producto.product.product_name_es
            },
            {
                Log.d("ERRCARGA", it.toString())
            }
        )

        queue.add(request)
        //return nombre

    }



    private fun carregaQuartaPestanya(producto: Producto) {
        //tvNomArticleImatgesBuscats.text = producto.product?.productNameEs
        tvNomArticleImatgesBuscats.text = BuscaNomProducte(producto)?.capitalize()
        tvMarcaImatgesBuscats.text = producto.product?.brands
        tvCodiImatgesBuscats.text = producto.code
        carregaRVImatges()
    }

    private fun carregaRVImatges() {
        llistaImatges = ArrayList()
        CarregaLlistaImatges(ctx, producto, llistaImatges).comprovaImatges()
        if(llistaImatges.size>0){
            Log.d("SIZE", "NUM IMATGES: "+ llistaImatges.size)
            rvImatgesBuscats.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(ctx)
            rvImatgesBuscats.layoutManager = layoutManager
            adaptadorImatges = AdaptadorImatges(ctx, llistaImatges)
            rvImatgesBuscats.adapter = adaptadorImatges
        }
    }

    private fun carregaTerceraPestanya(producto: Producto) {
        seleccionaImatgeNutricio()
        //tvNomArticleNutricioBuscats.text = producto.product?.productNameEs?.capitalize()
        tvNomArticleNutricioBuscats.text = BuscaNomProducte(producto)?.capitalize()
        tvMarcaNutricioBuscats.text = producto.product?.brands?.capitalize()
        tvCodiNutricioBuscats.text = producto.code
        carregaRVNutricio()
    }

    private fun seleccionaImatgeNutricio() {
        if(!producto.product?.imageNutritionUrl?.toString().isNullOrEmpty()){
            Picasso.get().load(producto.product?.imageNutritionUrl).into(ivNutricioBuscats)
        }else if(!producto.product?.imageNutritionSmallUrl?.toString().isNullOrEmpty()){
            Picasso.get().load(producto.product?.imageNutritionSmallUrl).into(ivNutricioBuscats)
        }else if(!producto.product?.imageNutritionThumbUrl?.toString().isNullOrEmpty()){
            Picasso.get().load(producto.product?.imageNutritionThumbUrl).into(ivNutricioBuscats)
        }else{
            ivNutricioBuscats.setImageResource(R.drawable.ic_shopping_basket_24px_black)
        }
    }

    private fun carregaRVNutricio() {
        llistaNutrients = ArrayList()
        llistaNutrients.clear()
        CarregaLlistaNutrients(ctx, producto.product?.nutriments!!, llistaNutrients).comprovaNutrients()

        if(llistaNutrients.size>0){
            rvNutricioBuscats.setHasFixedSize(true)
            rvNutricioBuscats.isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(ctx)
            rvNutricioBuscats.layoutManager = layoutManager
            adaptador = AdaptadorNutricio(ctx, llistaNutrients)
            rvNutricioBuscats.adapter = adaptador
        } else{
            tvNutriente.text = getString(R.string.txt_sin_nutrientes)
            tvNutriente.gravity = Gravity.LEFT

        }
    }

    /*private fun ComprovaEnergia() {
        var nutri = producto.product?.nutriments
        if(producto.product?.nutriments?.energy100g.toString().isNotEmpty()){
            llistaNutrients.add(Nutricio(getString(R.string.valor_energetico), nutri?.energyKcal100g.toString(), nutri?.energyKcalUnit.toString()))
        }
    }
*/
    private fun carregaSegonaPestanya(producto: Producto) {
        seleccionaImatgeIngredients()
        nomProducteIngredients.text = BuscaNomProducte(producto)?.capitalize()
        //nomProducteIngredients.text = producto.product?.productNameEs?.capitalize()
        marcaProducteIngredients.text = producto.product?.brands?.capitalize()
        codiProducteIngredients.text = producto.code
        if(BuscaIngredients(producto).toString().isNotEmpty()) tvIngredientsIngredients.text = BuscaIngredients(producto)
        else tvIngredientsIngredients.text = getString(R.string.txt_sin_ingredientes)

        //

        when(producto.product?.novaGroup){
            1 -> {
                ivNovaIngredients.setImageResource(R.drawable.ic_nova_group_1)
                tvNovaIngredients.text = getString(R.string.nova_group_1)
            }
            2 -> {
                ivNovaIngredients.setImageResource(R.drawable.ic_nova_group_2)
                tvNovaIngredients.text = getString(R.string.nova_group_2)
            }
            3 -> {
                ivNovaIngredients.setImageResource(R.drawable.ic_nova_group_3)
                tvNovaIngredients.text = getString(R.string.nova_group_3)
            }
            4 -> {
                ivNovaIngredients.setImageResource(R.drawable.ic_nova_group_4)
                tvNovaIngredients.text = getString(R.string.nova_group_4)
            }
            else->{
                ivNovaIngredients.alpha = 0.3f
                tvNovaIngredients.text = getString(R.string.nova_group_0)
            }
        }
    }



    private fun seleccionaImatgeIngredients() {

        if(!producto.product?.imageIngredientsUrl?.toString().isNullOrEmpty()){
            Picasso.get().load(producto.product?.imageIngredientsUrl).into(imatgeProducteIngredients)
        }else if(!producto.product?.imageIngredientsSmallUrl?.toString().isNullOrEmpty()){
            Picasso.get().load(producto.product?.imageIngredientsSmallUrl).into(imatgeProducteIngredients)
        }else if(!producto.product?.imageIngredientsThumbUrl?.toString().isNullOrEmpty()){
            Picasso.get().load(producto.product?.imageIngredientsThumbUrl).into(imatgeProducteIngredients)
        }else{
            imatgeProducteIngredients.setImageResource(R.drawable.ic_shopping_basket_24px_black)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun carregaPrimeraPestanya(producto: Producto) {
        crudTProductoPersonalitzat = TaulaPersonalitzadaCrud(ctx)
        if(crudTProductoPersonalitzat.existeProductoPersonalitzat(producto.code)) carregaCampsPersonalitzats()
        nomProducte.text = BuscaNomProducte(producto)?.capitalize()
        //nomProducte.text = producto.product?.productNameEs?.capitalize()
        marcaProducte.text = producto.product?.brands?.capitalize()
        codiProducte.text = producto.code
        Picasso.get().load(producto.product?.imageUrl).into(imatgeProducte)
        txtCantidad.text = BuscaCantidad(producto)
        txtCategoria.text = BuscaCategoria(producto)
        if (BuscaIngredients(producto).toString().isNotEmpty()) txtIngredients.text = BuscaIngredients(producto)
        else cvIngredients.visibility = View.GONE
        tvCategories.text = BuscaCategories(producto)
        CarregaNutrients100gr(producto)
        rbPuntuaDetallBuscats.setOnRatingBarChangeListener(object : RatingBar.OnRatingBarChangeListener{
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                crudTProductoPersonalitzat.updatePuntuacio(producto.code, rating)
            }
        })

        ivAddaListaDetallBuscats.setOnClickListener(this)
        ivSabor.setOnClickListener(this)
        ivHelpNova.setOnClickListener(this)
        ivHelpNutriScore.setOnClickListener(this)
        ivFavorito.setOnClickListener(this)
        ivCompartirDetallBuscats.setOnClickListener(this)
        if(idLlista != 0){
            if(!TaulaLlistesCrud(ctx).gestionaCantidad(idLlista) &&
                !TaulaLlistesCrud(ctx).gestionaFechas(idLlista) &&
                !TaulaLlistesCrud(ctx).gestionaUbicaciones(idLlista)){
                cvDatesCaducitat.visibility = View.GONE
            }else{
                nomLlista.text = TaulaLlistesCrud(ctx).getLlista(idLlista).nomLlista
                CarregaRVDatesCaducitat()
            }
        }else{
            cvDatesCaducitat.visibility = View.GONE
        }


        btnAddFechaCad.setOnClickListener(this)

        Log.d("NOVA", producto.product?.novaGroup.toString())
        when(producto.product?.novaGroup){
            1 -> {
                imatgeNova.setImageResource(R.drawable.ic_nova_group_1)
                txtNova.text = getString(R.string.nova_group_1)
            }
            2 -> {
                imatgeNova.setImageResource(R.drawable.ic_nova_group_2)
                txtNova.text = getString(R.string.nova_group_2)
            }
            3 -> {
                imatgeNova.setImageResource(R.drawable.ic_nova_group_3)
                txtNova.text = getString(R.string.nova_group_3)
            }
            4 -> {
                imatgeNova.setImageResource(R.drawable.ic_nova_group_4)
                txtNova.text = getString(R.string.nova_group_4)
            }
            else->{
                imatgeNova.alpha = 0.3f
                txtNova.text = getString(R.string.nova_group_0)
            }
        }
        txtNutriScore.text = producto.product?.nutriscoreScore.toString() + " /100"
        when(producto.product?.nutriscoreGrade){
            "a" -> imatgeNutriScore.setImageResource(R.drawable.ic_nutriscore_a)
            "b" -> imatgeNutriScore.setImageResource(R.drawable.ic_nutriscore_b)
            "c" -> imatgeNutriScore.setImageResource(R.drawable.ic_nutriscore_c)
            "d" -> imatgeNutriScore.setImageResource(R.drawable.ic_nutriscore_d)
            "e" -> imatgeNutriScore.setImageResource(R.drawable.ic_nutriscore_e)
            else ->{
                imatgeNutriScore.alpha = 0.3f
                txtNutriScore.text = getString(R.string.nutriscore_0)
            }
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

    private fun CarregaRVDatesCaducitat() {

        var liniacad : ArrayList<ProducteALlista> = arrayListOf()
        var datesCadTrobades : ArrayList<ProducteALlista> = arrayListOf()

        liniacad = TaulaProductesALlistesCrud(ctx).getLlistatDatesCaducitatXLlista(producto.code, idLlista)
        for (l in liniacad){
            datesCadTrobades.add(l)
        }
        Log.d("DATESTROBADES", datesCadTrobades.size.toString())

        rvDatesCaducitat.setHasFixedSize(true)
        rvDatesCaducitat.isNestedScrollingEnabled = false
        layoutManager = LinearLayoutManager(ctx)
        rvDatesCaducitat.layoutManager = layoutManager
        adaptadorCad = AdaptadorCaducitat(ctx, datesCadTrobades, this)
        rvDatesCaducitat.adapter = adaptadorCad


    }

    private fun carregaCampsPersonalitzats() {
        ivFavorito.tag = crudTProductoPersonalitzat.getProductePersonalitzat(producto.code).favorit
        when (ivFavorito.tag){
            1 -> ivFavorito.setImageResource(R.drawable.ic_baseline_favorite_24)
            else -> ivFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        ivSabor.tag = crudTProductoPersonalitzat.getProductePersonalitzat(producto.code).gust
        when(ivSabor.tag){
            1 -> ivSabor.setImageResource(R.drawable.ic_cara_feliz)
            2 -> ivSabor.setImageResource(R.drawable.ic_cara_esceptico)
            3 -> ivSabor.setImageResource(R.drawable.ic_cara_triste)
        }

        rbPuntuaDetallBuscats.rating = crudTProductoPersonalitzat.getProductePersonalitzat(producto.code).puntuacio.toFloat()
    }

    private fun BuscaCategories(producto: Producto): String? {
        Log.d("CATS", "categoriesTags: " + producto.product?.categoriesTags.toString())
        Log.d("CATS", "categoriesHierarchy: " + producto.product?.categoriesHierarchy.toString())
        Log.d("CATS", "categories: " + producto.product?.categories.toString())

        return producto.product?.categories.toString().capitalize()

    }


    //@SuppressLint("SetTextI18n")
    private fun CarregaNutrients100gr(producto: Producto) {

        val fat = producto.product?.nutrientLevels?.fat
        val sat_fat = producto.product?.nutrientLevels?.saturatedFat
        val sucre = producto.product?.nutrientLevels?.sugars
        val sal = producto.product?.nutrientLevels?.salt
        val nutriment = producto.product?.nutriments
        var cvNutricionVisible: Int = 4

        /*if (fat.isNullOrEmpty() && sat_fat.isNullOrEmpty() && sucre.isNullOrEmpty() && sal.isNullOrEmpty()) {
            cvInfoNutricion.visibility = View.GONE
            return
        }*/
        tvSemaforoGrasas.text = String.format("%.2f",nutriment?.fat100g) + nutriment?.fatUnit.toString() + " " + getString(
            R.string.grasas
        ) + " "
        tvSemaforoGrasasSat.text = String.format("%.2f",nutriment?.saturatedFat100g) + nutriment?.saturatedFatUnit.toString() + " " + getString(
            R.string.grasas_sat
        ) + " "
        tvSemaforoSucre.text = String.format("%.2f",nutriment?.sugars100g) + nutriment?.sugarsUnit.toString() + " " + getString(
            R.string.azucar
        ) + " "
        tvSemaforoSal.text = String.format("%.2f",nutriment?.salt100g) + nutriment?.saltUnit.toString() + " " + getString(
            R.string.sal
        ) + " "

        SeleccionaNivelNutriente(producto)

        /*if(producto.product?.nutriments?.fat100g.toString() == "null") {
            tvSemaforoGrasas.visibility = View.GONE
        }*/
        if(nutriment?.fat100g.toString().isNullOrEmpty() || nutriment?.fat100g.toString() == "null") {
            amagaCamps(tvSemaforoGrasas, ivSemaforoGrasas)
            cvNutricionVisible --
        }
        if(nutriment?.saturatedFat100g.toString().isNullOrEmpty() || nutriment?.saturatedFat100g.toString() == "null") {
            amagaCamps(tvSemaforoGrasasSat,ivSemaforoGrasasSat)
            cvNutricionVisible --
        }
        if(nutriment?.sugars100g.toString().isEmpty() || nutriment?.sugars100g.toString() == "null"){
            amagaCamps(tvSemaforoSucre, ivSemaforoSucre)
            cvNutricionVisible --
        }
        if(nutriment?.salt100g.toString().isEmpty() || nutriment?.salt100g.toString() == "null") {
            amagaCamps(tvSemaforoSal, ivSemaforoSal)
            cvNutricionVisible --
        }

        if (cvNutricionVisible == 0) {
            //cvInfoNutricion.visibility = View.GONE
            tvSemaforoGrasas.visibility = View.VISIBLE
            ivSemaforoGrasas.visibility = View.VISIBLE
            ivSemaforoGrasas.setImageResource(R.drawable.ic_senal_de_precaucion_orange_24)
            tvSemaforoGrasas.text = "Sin información de nutrientes"
        }
        /*if(nutriment?.fat100g.toString().isEmpty() && nutriment?.saturatedFat100g.toString().isEmpty() &&
            nutriment?.sugars100g.toString().isEmpty() && nutriment?.salt100g.toString().isEmpty()){
                cvInfoNutricion.visibility = View.GONE
        }*/
        Log.d("NUTRI100", "GRASAS: " +  producto.product?.nutriments?.fat100g.toString())
        Log.d("NUTRI100", "GRASAS UNIT: " +  producto.product?.nutriments?.fatUnit.toString())



    }

    private fun amagaCamps(txt: TextView, img: ImageView) {
        txt.visibility = View.GONE
        img.visibility = View.GONE
    }

    private fun SeleccionaNivelNutriente(producto: Producto) {
        var prova = arrayOf(producto.product?.nutrientLevels)
        Log.d("NUTRIENTS", producto.product?.nutrientLevels.toString())
        Log.d("NUTRIENTS", prova.get(0).toString())


        when (producto.product?.nutrientLevels?.fat){
            "low" -> {
                tvSemaforoGrasas.append(getString(R.string.low))
                ivSemaforoGrasas.setImageResource(R.drawable.ic_baseline_semaforo_verde)
            }
            "moderate" -> {
                tvSemaforoGrasas.append(getString(R.string.moderate))
                ivSemaforoGrasas.setImageResource(R.drawable.ic_baseline_semaforo_naranja)
            }
            "high" -> {
                tvSemaforoGrasas.append(getString(R.string.high))
                ivSemaforoGrasas.setImageResource(R.drawable.ic_baseline_semaforo_rojo)
            }
            null -> {
                ivSemaforoGrasas.visibility = View.INVISIBLE
            }
        }
        when (producto.product?.nutrientLevels?.saturatedFat){
            "low" -> {
                tvSemaforoGrasasSat.append(getString(R.string.low))
                ivSemaforoGrasasSat.setImageResource(R.drawable.ic_baseline_semaforo_verde)
            }
            "moderate" -> {
                tvSemaforoGrasasSat.append(getString(R.string.moderate))
                ivSemaforoGrasasSat.setImageResource(R.drawable.ic_baseline_semaforo_naranja)
            }
            "high" -> {
                tvSemaforoGrasasSat.append(getString(R.string.high))
                ivSemaforoGrasasSat.setImageResource(R.drawable.ic_baseline_semaforo_rojo)
            }
            null -> {
                ivSemaforoGrasasSat.visibility = View.INVISIBLE
            }
        }
        when (producto.product?.nutrientLevels?.sugars){
            "low" -> {
                tvSemaforoSucre.append(getString(R.string.low))
                ivSemaforoSucre.setImageResource(R.drawable.ic_baseline_semaforo_verde)
            }
            "moderate" -> {
                tvSemaforoSucre.append(getString(R.string.moderate))
                ivSemaforoSucre.setImageResource(R.drawable.ic_baseline_semaforo_naranja)
            }
            "high" -> {
                tvSemaforoSucre.append(getString(R.string.high))
                ivSemaforoSucre.setImageResource(R.drawable.ic_baseline_semaforo_rojo)
            }
            null -> {
                ivSemaforoSucre.visibility = View.INVISIBLE
            }
        }
        when (producto.product?.nutrientLevels?.salt){
            "low" -> {
                tvSemaforoSal.append(getString(R.string.low))
                ivSemaforoSal.setImageResource(R.drawable.ic_baseline_semaforo_verde)
            }
            "moderate" -> {
                tvSemaforoSal.append(getString(R.string.moderate))
                ivSemaforoSal.setImageResource(R.drawable.ic_baseline_semaforo_naranja)
            }
            "high" -> {
                tvSemaforoSal.append(getString(R.string.high))
                ivSemaforoSal.setImageResource(R.drawable.ic_baseline_semaforo_rojo)
            }
            null -> {
                ivSemaforoSal.visibility = View.INVISIBLE
            }
        }
    }

    private fun BuscaIngredients(producto: Producto): String? {
        var ingredients: String = ""
        if(!producto.product?.ingredientsTextEs.isNullOrEmpty()){
            ingredients = producto.product?.ingredientsTextEs.toString()
        } else if (!producto.product?.ingredientsTextEn.isNullOrEmpty()){
            ingredients = producto.product?.ingredientsTextEn.toString()
        } else if (!producto.product?.ingredientsText.isNullOrEmpty()){
            ingredients = producto.product?.ingredientsText.toString()
        } else {

            ingredients = ""
        }
        return ingredients.capitalize()
    }

    private fun BuscaCantidad(producto: Producto): String? {
        var cantidad: String = ""
        if(!producto.product?.quantity.isNullOrEmpty()){
            cantidad = producto.product?.quantity.toString()
        } else if(!producto.product?.servingSize.isNullOrEmpty()){
            cantidad = producto.product?.servingSize.toString()
        } else if(!producto.product?.servingQuantity.isNullOrEmpty()){
            cantidad = producto.product?.servingQuantity.toString()
        } else {
            imatgeIconoCantidad.visibility = View.GONE
            txtCantidad.visibility = View.GONE
            cantidad = ""
        }
        return cantidad
    }

    private fun BuscaCategoria(producto: Producto): String {
        var categoria: String = ""
        var categoriaFitxa = producto.product?.categories.toString()
        if(categoriaFitxa.isNotEmpty()){
            if(categoriaFitxa.length>17) categoria = categoriaFitxa.substring(0,17) + "..." else categoria = categoriaFitxa
        } else {
            txtCategoria.visibility = View.GONE
            ivIconoCategoriaDetallBuscats.visibility = View.GONE
        }
        return categoria.capitalize()
        /*var url: String = ""
        var categoria: String = ""
        var categoriaFitxa = producto.product?.categories.toString()

        if(categoriaFitxa.isNotEmpty()){
            val queue = Volley.newRequestQueue(ctx)
            url = Constants.URL_API_CATEGORIES
            val request = StringRequest(Request.Method.GET, url, { response ->
                var gson = Gson()
                var categoriaAPI = gson.fromJson(response, Categories::class.java)
                for (etiqueta in categoriaAPI.tags) {
                    Log.d("PROVA", "ID: " + etiqueta.id + "NOM: " + etiqueta.name)
                    if (etiqueta.id.equals(categoriaFitxa, ignoreCase = true)) {
                        if (etiqueta.name.length > 20) categoria = etiqueta.name.substring(0, 20)
                        else categoria = etiqueta.name
                    }*//* else {
                        if(categoriaFitxa.length > 20) categoria = categoriaFitxa.substring(0,20)
                        else categoria = categoriaFitxa
                    }*//*
                }

            },
                { responseError ->
                    Toast.makeText(ctx, "No hi ha resposta del servidor", Toast.LENGTH_SHORT).show()
                    Log.d("SINRESPUESTA", responseError.toString())
                })
            queue.add(request)
        }else categoria = ""*/

       /* if(categoria.isEmpty() && categoriaFitxa.isNotEmpty()){
            categoria = categoriaFitxa
        }else if(categoria.isEmpty()){
            txtCategoria.visibility = View.GONE
            ivIconoCategoriaDetallBuscats.visibility = View.GONE
        }*/
        //txtCategoria.text = categoria
       // return categoria
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        crudTProductoPersonalitzat = TaulaPersonalitzadaCrud(ctx)
        when(v?.id){
            R.id.ivSaborDetallBuscats -> {
                when (ivSabor.tag) {
                    1 -> {
                        ivSabor.tag = 2
                        ivSabor.setImageResource(R.drawable.ic_cara_esceptico)
                    }
                    2 -> {
                        ivSabor.tag = 3
                        ivSabor.setImageResource(R.drawable.ic_cara_triste)
                    }
                    else -> {
                        ivSabor.tag = 1
                        ivSabor.setImageResource(R.drawable.ic_cara_feliz)
                    }
                }
                crudTProductoPersonalitzat.updateSabor(producto.code, (ivSabor.tag as Int))
            }
            R.id.ivHelpNova -> {
               muestraAlertDialogHelp(v)
            }
            R.id.ivHelpNutriScore ->{
                muestraAlertDialogHelp(v)
            }
            R.id.ivFavoritoDetallBuscats ->{

                if(esFavorito){
                    esFavorito = false
                    ivFavorito.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    crudTProductoPersonalitzat.updateFavorito(producto.code, 0)

                }else{
                    esFavorito = true
                    ivFavorito.setImageResource(R.drawable.ic_baseline_favorite_24)
                    crudTProductoPersonalitzat.updateFavorito(producto.code, 1)
                }
            }
            R.id.ivCompartirDetallBuscats ->{
                abrirCompartir()
            }
            R.id.ivAddaListaDetallBuscats ->{
                //alertsDialog.muestraAlertDialogListas(ctx)
                //muestraAlertDialogListas()
                muestraNewADAnadirALista(idLlista)
                Log.d("IDLLISTA", "ONCLICKAÑADIR IDLISTA: " + idLlista)
            }
            R.id.btnAddFechaCad->{
                muestraNewADAnadirALista(idLlista)
                //muestraAlertDialogListas()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun muestraNewADAnadirALista(idLlista: Int) {

        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alert_dialog_add_producte_llista, null)

        rellenaCamposPorDefecto(idLlista, view)


        ConfiguraVisiondeBotones(idLlista, view)

        view.cvSeleccionaLista_ad_add_a_llista.setOnClickListener {
            muestraAlertDialogListas(view)
            //ConfiguraVisiondeBotones(idLlista, view)
        }

        view.cvSeleccionaUbicacion_ad_add_a_llista.setOnClickListener {
            muestraAlertDialogUbicaciones(view)
        }
        view.cvFechaCad_ad_add_a_llista.setOnClickListener {
            muestraAlertDialogFechaCad(view)
        }
        view.cvSeleccionaCantidad_ad_add_a_llista.setOnClickListener {
            muestraAlertDialogCantidad(view)
        }


        builder.setView(view)
        builder.setTitle("Añadir producto a Lista: ")
        builder.setPositiveButton("AÑADIR"){ dialog, which ->
            Toast.makeText(ctx, "Producto añadido", Toast.LENGTH_SHORT).show()
            if(comprovaTotsElsCamps(view)){
                NewafegirProducteALlista(view)
            } else {
                Toast.makeText(ctx, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }

            /*var idLlista:Int = TaulaLlistesCrud(ctx).getIdLlista(nomLlista)
            if(TaulaLlistesCrud(ctx).gestionaFechas(idLlista)){
                mostraAlertDialogCalendar(nomLlista, cantidadSeleccionada)
            } else if(TaulaLlistesCrud(ctx).gestionaUbicaciones(idLlista)){
                mostraAlertDialogUbicaciones(nomLlista, cantidadSeleccionada, 0)
            } else{
                afegirProducteALlista(nomLlista, cantidadSeleccionada, 0, 0)
                Snackbar.make(ivAddaListaDetallBuscats,getString(R.string.SB_ProducteAfegitaLlista) + nomLlista, Snackbar.LENGTH_LONG).show()
            }*/

            /*if(view.etNomNovaLlista.text.isNotEmpty()){
                addNovaLlista(view.etNomNovaLlista.text.toString())
                afegirALlista(0, arrayOf(view.etNomNovaLlista.text.toString()))
            }else{
                Snackbar.make(ivAddaListaDetallBuscats ,getString(R.string.ERR_TEXTO_LISTA_VACIO),Snackbar.LENGTH_LONG).show()
            }*/

        }
        builder.setNegativeButton("CANCELAR"){ dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.setOnShowListener {
            ConfiguraVisiondeBotones(idLlista, view)
            btnpositive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            if(!comprovaTotsElsCamps(view)){
                btnpositive.isEnabled = false
            } else {
                btnpositive.isEnabled = true
            }
        }

        dialog.show()
    }

    private fun comprovaTotsElsCamps(v:View): Boolean {
        var campsok: Boolean = true
        if(v.tvNomLista_ad_add_a_llista.tag.toString() == "0"){
            campsok = false
            return campsok
        }
        if(TaulaLlistesCrud(ctx).gestionaUbicaciones(v.tvNomLista_ad_add_a_llista.tag.toString().toInt())){
            if(v.tvNomUbicacio_ad_add_a_llista.tag == 0 && v.tvNomUbicacio_ad_add_a_llista.tag.toString().isNullOrEmpty()){
                campsok = false
                return campsok
            }
        }else {
            v.tvNomUbicacio_ad_add_a_llista.tag = 0
        }
        if(TaulaLlistesCrud(ctx).gestionaFechas(v.tvNomLista_ad_add_a_llista.tag.toString().toInt())){
            if(v.tvFechaCad_ad_add_a_llista.tag == 0 && v.tvFechaCad_ad_add_a_llista.tag.toString().isNullOrEmpty()){
                campsok = false
                return campsok
            }
        }else {
            v.tvFechaCad_ad_add_a_llista.tag = 0
        }
        if(TaulaLlistesCrud(ctx).gestionaCantidad(v.tvNomLista_ad_add_a_llista.tag.toString().toInt())){
            if(v.tvCantidad_ad_add_a_llista.text == "0" && v.tvCantidad_ad_add_a_llista.text.toString().isNullOrEmpty()){
                campsok = false
                return campsok
            }
        }else {
            v.tvCantidad_ad_add_a_llista.text = "0"
        }
        if(campsok) btnpositive.isEnabled = true
        Log.d("CAMPSOK", campsok.toString())
        return campsok
    }

    private fun NewafegirProducteALlista(v:View) {
        var item: ProducteALlista
        item = ProducteALlista(0,
            producto.code,
            v.tvNomLista_ad_add_a_llista.tag.toString().toInt(),
            v.tvCantidad_ad_add_a_llista.text.toString().toInt(),
            v.tvFechaCad_ad_add_a_llista.tag.toString().toLong(),
            v.tvNomUbicacio_ad_add_a_llista.tag.toString().toInt(),
            0)
        TaulaProductesALlistesCrud(ctx).addProducteALlista(item)
        CarregaRVDatesCaducitat()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun rellenaCamposPorDefecto(idLlista: Int, v: View) {
        if(idLlista!=0) {
            v.tvNomLista_ad_add_a_llista.text = TaulaLlistesCrud(ctx).getLlista(idLlista).nomLlista
            v.tvNomLista_ad_add_a_llista.tag = idLlista.toString()
            v.tvNomUbicacio_ad_add_a_llista.tag = 1
            v.tvNomUbicacio_ad_add_a_llista.text =
                TaulaUbicacionsCrud(ctx).getUbicacio(1).nomubicacio
            v.tvFechaCad_ad_add_a_llista.tag =
                Funcions.obtenirDataActualMillis() + (7 * 24 * 3600000)
            v.tvFechaCad_ad_add_a_llista.text =
                Funcions.MillisToDate(v.tvFechaCad_ad_add_a_llista.tag.toString().toLong())
            v.tvCantidad_ad_add_a_llista.text = "1"
        } else {
            v.tvNomLista_ad_add_a_llista.tag = idLlista.toString()
            v.cvSeleccionaUbicacion_ad_add_a_llista.visibility = View.GONE
            v.cvFechaCad_ad_add_a_llista.visibility = View.GONE
            v.cvSeleccionaCantidad_ad_add_a_llista.visibility = View.GONE
        }

    }

    private fun muestraAlertDialogCantidad(v: View) {
        val numberPicker = NumberPicker(ctx)
        var cantidadSeleccionada: Int = 0
        numberPicker.maxValue = 30
        numberPicker.minValue = 1
        numberPicker.value = 1

        val builder = AlertDialog.Builder(ctx)
        builder.setView(numberPicker)
        builder.setTitle("Selecciona cantidad")
        builder.setMessage("")
        builder.setPositiveButton("ACEPTAR") { dialog, which ->
            cantidadSeleccionada = numberPicker.value
            v.tvCantidad_ad_add_a_llista.text = cantidadSeleccionada.toString()
        }
        builder.setNegativeButton(
            "CANCELAR"
        ) { dialog, which -> dialog.dismiss() }
        builder.create()
        builder.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun muestraAlertDialogFechaCad(v: View) {
        var fechaSeleccionada : Long = Funcions.obtenirDataActualMillis() + (7*24*3600000)
        val calendar = CalendarView(ctx)
        calendar.firstDayOfWeek = 2
        calendar.setDate(Funcions.obtenirDataActualMillis() + (7*24*3600000))

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            fechaSeleccionada = Funcions.DataToMillis( dayOfMonth.toString() + "/" + (month+1).toString() + "/" + year.toString())
        }

        val builder = AlertDialog.Builder(ctx)
        builder.setView(calendar)
        builder.setTitle("Selecciona Fecha Caducidad")
        builder.setMessage("")
        builder.setPositiveButton("OK") { dialog, which ->
            v.tvFechaCad_ad_add_a_llista.text = Funcions.MillisToDate(fechaSeleccionada)
            v.tvFechaCad_ad_add_a_llista.tag = fechaSeleccionada
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> dialog.dismiss() }
        builder.create()
        builder.show()
    }

    private fun muestraAlertDialogUbicaciones(v:View) {
        var ubicacionsNom: Array<String>
        ubicacionsNom = arrayOf()
        var crudUbicacions = TaulaUbicacionsCrud(ctx)

        for(ubicacio in crudUbicacions.getAllUbicacions()){
            ubicacionsNom += ubicacio.nomubicacio
        }
        ubicacionsNom += getString(R.string.titulo_AfegirUbicacio)

        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(getString(R.string.titulo_ubicacion))

        builder.setItems(ubicacionsNom, DialogInterface.OnClickListener { dialog, which ->
            if (which == ubicacionsNom.size - 1) {
                /*Toast.makeText(ctx, "Click a Afegir: " + which, Toast.LENGTH_SHORT).show()*/
                mostraAlertDialogNovaUbicacio(v)
            } else {
                var idUbicacio = TaulaUbicacionsCrud(ctx).getIdUbicacio(ubicacionsNom[which])
                v.tvNomUbicacio_ad_add_a_llista.text = ubicacionsNom[which]
                v.tvNomUbicacio_ad_add_a_llista.tag = idUbicacio
                //afegirProducteALlista(nomLlista, i, millis, idUbicacio)


            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ConfiguraVisiondeBotones(idLlista: Int, view: View) {
        if(idLlista != 0) {
            if (TaulaLlistesCrud(ctx).gestionaUbicaciones(idLlista)) {
                Log.d("VISION", "UBICACIONES: TRUE")
                view?.cvSeleccionaUbicacion_ad_add_a_llista?.visibility = View.VISIBLE
                view.tvNomUbicacio_ad_add_a_llista.tag = "1"
                view.tvNomUbicacio_ad_add_a_llista.text = TaulaUbicacionsCrud(ctx).getUbicacio(1).nomubicacio
            } else {
                Log.d("VISION", "UBICACIONES: FALSE")
                view?.cvSeleccionaUbicacion_ad_add_a_llista?.visibility = View.GONE
                view.tvNomUbicacio_ad_add_a_llista.tag = "0"
            }
            if (TaulaLlistesCrud(ctx).gestionaFechas(idLlista)) {
                Log.d("VISION", "FECHAS: TRUE")
                view?.cvFechaCad_ad_add_a_llista?.visibility = View.VISIBLE
                view.tvFechaCad_ad_add_a_llista.tag = Funcions.obtenirDataActualMillis() + (7*24*3600000)
                view.tvFechaCad_ad_add_a_llista.text = Funcions.MillisToDate(view.tvFechaCad_ad_add_a_llista.tag.toString().toLong())
            } else {
                Log.d("VISION", "FECHAS: FALSE")
                view?.cvFechaCad_ad_add_a_llista?.visibility = View.GONE
                view.tvFechaCad_ad_add_a_llista.tag = 0
            }
            if (TaulaLlistesCrud(ctx).gestionaCantidad(idLlista)) {
                Log.d("VISION", "CANTIDAD: TRUE")
                view?.cvSeleccionaCantidad_ad_add_a_llista?.visibility = View.VISIBLE
                view.tvCantidad_ad_add_a_llista.text = "1"
            } else {
                Log.d("VISION", "CANTIDAD: FALSE")
                view?.cvSeleccionaCantidad_ad_add_a_llista?.visibility = View.GONE
                view.tvCantidad_ad_add_a_llista.text = "0"
            }
        }
    }


    private fun muestraAlertDialogListas(v:View) {

        var llistesNom: Array<String>
        llistesNom = arrayOf()
        crudLlistes = TaulaLlistesCrud(ctx)

        for(llista in crudLlistes.getAllLlista()){
            if(llista.id!=3) llistesNom += llista.nomLlista!!
        }
        llistesNom += getString(R.string.titulo_AfegirLlista)

        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(getString(R.string.titulo_Llistes))

        builder.setItems(llistesNom, DialogInterface.OnClickListener { dialog, which ->
            if (which == llistesNom.size - 1) {
                Toast.makeText(ctx, "Click a Afegir: " + which, Toast.LENGTH_SHORT).show()
               mostraAlertDialogNovaLlista(v)
            } else {
                var idL = TaulaLlistesCrud(ctx).getIdLlista(llistesNom[which])
                v.tvNomLista_ad_add_a_llista.text = llistesNom[which]
                v.tvNomLista_ad_add_a_llista.tag = idL
               ConfiguraVisiondeBotones(TaulaLlistesCrud(ctx).getIdLlista(llistesNom[which]), v)
                comprovaTotsElsCamps(v)
            }
        })

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

   /* private fun mostraAlertDialogQuantitat(nomLlista:String) {
        var cantidadSeleccionada: Int = 0
        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alert_dialog_cantidad, null)
        view.npCantidad.minValue = 1
        view.npCantidad.maxValue = 20
        view.npCantidad.value = 1
        cantidadSeleccionada = view.npCantidad.value
        view.npCantidad.wrapSelectorWheel = false
        view.npCantidad.setOnValueChangedListener { picker, oldVal, newVal ->
             cantidadSeleccionada = newVal
        }
        builder.setView(view)
        builder.setTitle("Cantidad: ")
        builder.setPositiveButton("SIGUIENTE"){ dialog, which ->
            var idLlista:Int = TaulaLlistesCrud(ctx).getIdLlista(nomLlista)
            if(TaulaLlistesCrud(ctx).gestionaFechas(idLlista)){
                mostraAlertDialogCalendar(nomLlista, cantidadSeleccionada)
            } else if(TaulaLlistesCrud(ctx).gestionaUbicaciones(idLlista)){
                mostraAlertDialogUbicaciones(nomLlista, cantidadSeleccionada, 0)
            } else{
                afegirProducteALlista(nomLlista, cantidadSeleccionada, 0, 0)
                Snackbar.make(ivAddaListaDetallBuscats,getString(R.string.SB_ProducteAfegitaLlista) + nomLlista, Snackbar.LENGTH_LONG).show()
            }

            *//*if(view.etNomNovaLlista.text.isNotEmpty()){
                addNovaLlista(view.etNomNovaLlista.text.toString())
                afegirALlista(0, arrayOf(view.etNomNovaLlista.text.toString()))
            }else{
                Snackbar.make(ivAddaListaDetallBuscats ,getString(R.string.ERR_TEXTO_LISTA_VACIO),Snackbar.LENGTH_LONG).show()
            }*//*

        }
        builder.setNegativeButton("CANCELAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }*/

    /*private fun mostraAlertDialogCalendar(nomLlista:String, q: Int) {
        var fecha:String = ""
        var millis: Long = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Funcions.obtenirDataActualMillis()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alert_dialog_calendar, null)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            view.dpCalendar.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
               millis = Funcions.DataToMillis(dayOfMonth.toString() + "/" + (monthOfYear+1).toString() + "/" + year.toString())
            }
        }

        builder.setView(view)
        builder.setTitle("Fecha de Caducidad: ")
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            Snackbar.make(ivAddaListaDetallBuscats, "ACEPTAR: " + q + " - " + fecha , Snackbar.LENGTH_LONG).show()
            if (TaulaLlistesCrud(ctx).gestionaUbicaciones(TaulaLlistesCrud(ctx).getIdLlista(nomLlista))){
                mostraAlertDialogUbicaciones(nomLlista, q, millis)
            } else {
                afegirProducteALlista(nomLlista, q, millis, 0)
            }
            //Snackbar.make(ivAddaListaDetallBuscats,getString(R.string.SB_ProducteAfegitaLlista) + nomLlista, Snackbar.LENGTH_LONG).show()
            *//*if(view.etNomNovaLlista.text.isNotEmpty()){
                addNovaLlista(view.etNomNovaLlista.text.toString())
                afegirALlista(0, arrayOf(view.etNomNovaLlista.text.toString()))
            }else{
                Snackbar.make(ivAddaListaDetallBuscats ,getString(R.string.ERR_TEXTO_LISTA_VACIO),Snackbar.LENGTH_LONG).show()
            }*//*

        }
        builder.setNegativeButton("CANCELAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }*/

    private fun mostraAlertDialogNovaLlista(v:View) {
        var gestionadates:Int = 0
        var gestionaCantidad:Int = 0
        var gestionaUbicacion:Int = 0
        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alert_dialog_nova_llista, null)

        builder.setView(view)
        builder.setTitle("Añadir Lista: ")
        builder.setPositiveButton("AÑADIR"){ dialog, which ->
            if(view.etNomNovaLlista.text.isNotEmpty()){
                if (view.cbDataCad.isChecked) gestionadates = 1 else gestionadates = 0
                if (view.cbGestionaCantidad.isChecked) gestionaCantidad = 1 else gestionaCantidad = 0
                if (view.cbGestionaUbicacion.isChecked) gestionaUbicacion = 1 else gestionaUbicacion = 0
                addNovaLlista(view.etNomNovaLlista.text.toString(), gestionadates, gestionaCantidad, gestionaUbicacion)
                v.tvNomLista_ad_add_a_llista.text = view.etNomNovaLlista.text.toString()
                v.tvNomLista_ad_add_a_llista.tag = TaulaLlistesCrud(ctx).getIdLlista(view.etNomNovaLlista.text.toString())
                ConfiguraVisiondeBotones(TaulaLlistesCrud(ctx).getIdLlista(view.etNomNovaLlista.text.toString()), v)
            }else{
                Snackbar.make(ivAddaListaDetallBuscats ,getString(R.string.ERR_TEXTO_LISTA_VACIO),Snackbar.LENGTH_LONG).show()
            }

        }
        builder.setNegativeButton("CANCELAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun mostraAlertDialogUbicaciones(nomLlista: String, i: Int, millis:Long) {
       /* var ubicacionsNom: Array<String>
        ubicacionsNom = arrayOf()
        var crudUbicacions = TaulaUbicacionsCrud(ctx)

        for(ubicacio in crudUbicacions.getAllUbicacions()){
            ubicacionsNom += ubicacio.nomubicacio
        }
        ubicacionsNom += getString(R.string.titulo_AfegirUbicacio)

        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(getString(R.string.titulo_ubicacion))

        builder.setItems(ubicacionsNom, DialogInterface.OnClickListener { dialog, which ->
            if (which == ubicacionsNom.size - 1) {
                Toast.makeText(ctx, "Click a Afegir: " + which, Toast.LENGTH_SHORT).show()
                mostraAlertDialogNovaUbicacio(nomLlista, i, millis, "")
            } else {
                var idUbicacio = TaulaUbicacionsCrud(ctx).getIdUbicacio(ubicacionsNom[which])
                afegirProducteALlista(nomLlista, i, millis, idUbicacio)


            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()*/

    }

    private fun mostraAlertDialogNovaUbicacio(v:View) {
        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alert_dialog_nova_ubicacio, null)

        builder.setView(view)
        builder.setTitle("Añadir Ubicación: ")
        builder.setPositiveButton("AÑADIR"){ dialog, which ->
            if(view.etNomUbicacioAlert.text.isNotEmpty()){
                if(!TaulaUbicacionsCrud(ctx).existeUbicacion(view.etNomUbicacioAlert.text.toString())){
                    addNovaUbicacio(view.etNomUbicacioAlert.text.toString(), view.etDescripcionUbicacionAlert.text.toString())
                    var idUbicacio = TaulaUbicacionsCrud(ctx).getIdUbicacio(view.etNomUbicacioAlert.text.toString())
                    v.tvNomUbicacio_ad_add_a_llista.text = view.etNomUbicacioAlert.text.toString()
                    v.tvNomUbicacio_ad_add_a_llista.tag = idUbicacio
                    //afegirProducteALlista(nomLlista, i, millis, idUbicacio)

                } else {
                    view.etNomUbicacioAlert.setError(getString(R.string.ERR_UBICACION_EXISTE))
                    Snackbar.make(view.etNomUbicacioAlert ,getString(R.string.ERR_UBICACION_EXISTE), Snackbar.LENGTH_LONG).show()
                    //mostraAlertDialogNovaUbicacio(v)
                }

            }else{
                view.etNomUbicacioAlert.setError(getString(R.string.ERR_TEXTO_UBIC_VACIO))
                Snackbar.make(view.etNomUbicacioAlert ,getString(R.string.ERR_TEXTO_UBIC_VACIO),Snackbar.LENGTH_LONG).show()
                //mostraAlertDialogNovaUbicacio(nomLlista, i, millis, getString(R.string.ERR_TEXTO_UBIC_VACIO))
            }

        }
        builder.setNegativeButton("CANCELAR"){ dialog, which ->
            //mostraAlertDialogUbicaciones(nomLlista, i, millis)
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun addNovaUbicacio(nomUbicacio: String, descUbic:String) {
        var crudUbic = TaulaUbicacionsCrud(ctx)
        var newId = crudUbic.getNextIDUbicacions()
        crudUbic.addUbicacio(Ubicacio(newId, nomUbicacio, descUbic))
    }


    public fun addNovaLlista(titulo:String, gestionaDates:Int, gestionaCantidad:Int, gestionaUbicacion: Int) {
        var crudLlista: TaulaLlistesCrud = TaulaLlistesCrud(ctx)
        var nextID = crudLlista.getNextIDLlista()
        crudLlista.addLlista(nextID, titulo, gestionaDates, gestionaCantidad, gestionaUbicacion)
    }

    private fun afegirProducteALlista(llistesNom: String, q:Int, datemillis: Long, idUbic: Int ) {
        var idLlista = crudLlistes.getIdLlista(llistesNom)
        var crudProdALlista = TaulaProductesALlistesCrud(ctx)
        when(true){
            //GESTIONA CANTIDAD Y FECHAS
            TaulaLlistesCrud(ctx).gestionaCantidad(idLlista) && TaulaLlistesCrud(ctx).gestionaFechas(idLlista) ->{
                if(crudProdALlista.existeProductoconFechaIgual(idLlista, producto.code.toString(), datemillis)){
                    MostrarAlertActulizarCantidadConFecha(llistesNom, q, idLlista, datemillis, idUbic)
                } else {
                    crudProdALlista.addProducteALlista(ProducteALlista(0, producto.code, idLlista, q, datemillis, idUbic))
                    var sb = Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_ProducteAfegitaLlista) + " " + llistesNom, Snackbar.LENGTH_LONG)
                    sb.setAction(R.string.ver_lista, View.OnClickListener {
                        var intent = Intent(ctx, ProductesDeCategoriaActivity::class.java)
                        intent.putExtra("VEDE", Constants.MISLISTAS)
                        intent.putExtra("IDLLISTA", idLlista.toString())
                        startActivity(intent)
                    })
                    sb.show()
                }
                CarregaRVDatesCaducitat()
            }
            //GESTIONA SÓLO CANTIDAD
            TaulaLlistesCrud(ctx).gestionaCantidad(idLlista) && !TaulaLlistesCrud(ctx).gestionaFechas(idLlista) ->{
                if(crudProdALlista.existeProductoenLlista(idLlista, producto.code.toString())){
                    MostrarAlertActulizarCantidad(llistesNom, q, idLlista, idUbic)
                }else{
                    crudProdALlista.addProducteALlista(ProducteALlista(0, producto.code, idLlista, q, 0, idUbic))
                    Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_ProducteAfegitaLlista) + " " + llistesNom, Snackbar.LENGTH_LONG).show()
                }
            }
            //GESTIONA SOLO FECHAS
            !TaulaLlistesCrud(ctx).gestionaCantidad(idLlista) && TaulaLlistesCrud(ctx).gestionaFechas(idLlista) ->{
                if(crudProdALlista.existeProductoconFechaIgual(idLlista, producto.code.toString(), datemillis)){
                    Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_ProductoYaExisteConMismaFecha) + " " + llistesNom, Snackbar.LENGTH_LONG).show()
                } else {
                    crudProdALlista.addProducteALlista(ProducteALlista(0, producto.code, idLlista, 0, datemillis, idUbic))
                    Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_ProducteAfegitaLlista) + " " + llistesNom, Snackbar.LENGTH_LONG).show()
                }
                CarregaRVDatesCaducitat()
            }
            //NO GESTIONA
            !TaulaLlistesCrud(ctx).gestionaCantidad(idLlista) && !TaulaLlistesCrud(ctx).gestionaFechas(idLlista) ->{
                if(crudProdALlista.existeProductoenLlista(idLlista, producto.code.toString())){
                    Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_ProductoYaExiste) + " " + llistesNom, Snackbar.LENGTH_LONG).show()
                } else {
                    crudProdALlista.addProducteALlista(ProducteALlista(0, producto.code, idLlista, 0, 0, idUbic))
                    Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_ProducteAfegitaLlista) + " " + llistesNom, Snackbar.LENGTH_LONG).show()
                }
            }

        }

    }

   /* private fun sbUndoListener(ctx: Context, idLlista: Int): View.OnClickListener {
        fun onClick(v: View){
            var intent = Intent(ctx, ProductesDeCategoriaActivity::class.java)
            intent.putExtra("VEDE", Constants.MISLISTAS)
            intent.putExtra("IDLLISTA", idLlista)
            startActivity(intent)
            //Toast.makeText(ctx, "Click en VER", Toast.LENGTH_SHORT).show()
        }
    }*/


    private fun MostrarAlertActulizarCantidad(llistesNom: String, q: Int, idLlista: Int, idUbic: Int) {
        var nuevaCantidad:Int = 0
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(getString(R.string.actualizar_cantidad))
        builder.setMessage("Ya existe este producto en la lista: " + llistesNom + ". Quieres añadir la cantidad seleccionada?")
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            nuevaCantidad = q + TaulaProductesALlistesCrud(ctx).getCantidadProducto(idLlista, producto.code.toString())
            TaulaProductesALlistesCrud(ctx).updateCantidad(nuevaCantidad, idLlista, producto.code.toString(), idUbic)
            Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_Cantidad_Actualizada) , Snackbar.LENGTH_LONG).show()
            dialog.dismiss()
        }
        builder.setNegativeButton("CERRAR"){dialog, which->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun MostrarAlertActulizarCantidadConFecha(nomLlista: String, q: Int, idLlista: Int, data:Long, idUbic: Int) {
        var nuevaCantidad:Int = 0
        val builder = AlertDialog.Builder(ctx)
        builder.setTitle(getString(R.string.actualizar_cantidad))
        builder.setMessage("Ya existe este producto con esta fecha de caducidad en la lista: " + nomLlista + ". Quieres añadir la cantidad seleccionada?")
        builder.setPositiveButton("ACEPTAR"){ dialog, which ->
            nuevaCantidad = q + TaulaProductesALlistesCrud(ctx).getCantidadProductoMismaFecha(idLlista, producto.code.toString(), data)
            TaulaProductesALlistesCrud(ctx).updateCantidadMismaFecha(nuevaCantidad, idLlista, data, producto.code.toString(), idUbic)
            Snackbar.make(ivAddaListaDetallBuscats, getString(R.string.SB_Cantidad_Actualizada) , Snackbar.LENGTH_LONG).show()
            dialog.dismiss()
        }
        builder.setNegativeButton("CERRAR"){dialog, which->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun abrirCompartir() {
        var urlCompartir = Constants.URL_PRODUCT_WEB + producto.code
        var intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra("CODIBARRES", codiProducte.toString())
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mira este producto:" + urlCompartir)
        intent.putExtra(Intent.EXTRA_TEXT, tvNomArticleDetallBuscats.text.toString() + " " + urlCompartir)
        startActivity(Intent.createChooser(intent, "Compartir en:"))
    }


    private fun muestraAlertDialogHelp(v: View) {
        var urlinfo: String = ""
        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alertdialog_help, null)
        builder.setView(view)

        when (v.id){
            R.id.ivHelpNova ->{
                builder.setTitle("Qué es Nova?")
                builder.setIcon(R.drawable.ic_nova_group_1)
                view.findViewById<TextView>(R.id.tvResumen).text = getString(R.string.clasificacion_nova)
                view.findViewById<ImageView>(R.id.ivGrupo1).setImageResource(R.drawable.ic_nova_group_1)
                view.findViewById<TextView>(R.id.tvGrupo1).text = getString(R.string.nova_group_1)
                view.findViewById<ImageView>(R.id.ivGrupo2).setImageResource(R.drawable.ic_nova_group_2)
                view.findViewById<TextView>(R.id.tvGrupo2).text = getString(R.string.nova_group_2)
                view.findViewById<ImageView>(R.id.ivGrupo3).setImageResource(R.drawable.ic_nova_group_3)
                view.findViewById<TextView>(R.id.tvGrupo3).text = getString(R.string.nova_group_3)
                view.findViewById<ImageView>(R.id.ivGrupo4).setImageResource(R.drawable.ic_nova_group_4)
                view.findViewById<TextView>(R.id.tvGrupo4).text = getString(R.string.nova_group_4)
                urlinfo = Constants.URL_INFO_NOVA
            }
            R.id.ivHelpNutriScore->{
                builder.setTitle("Qué es NutriScore?")
                builder.setIcon(R.drawable.ic_nutriscore_a)
                view.findViewById<TextView>(R.id.tvResumen).text = getString(R.string.clasificacion_nutriscore)

                view.findViewById<ImageView>(R.id.ivGrupo1).visibility = View.GONE
                view.findViewById<TextView>(R.id.tvGrupo1).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivGrupo2).visibility = View.GONE
                view.findViewById<TextView>(R.id.tvGrupo2).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivGrupo3).visibility = View.GONE
                view.findViewById<TextView>(R.id.tvGrupo3).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivGrupo4).visibility = View.GONE
                view.findViewById<TextView>(R.id.tvGrupo4).visibility = View.GONE

                urlinfo = Constants.URL_INFO_NUTRISCORE
            }
        }


        // Set the alert dialog title

        // Display a message on alert dialog
        //builder.setMessage(R.string.clasificacion_nova)

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton("CERRAR"){ dialog, which ->
            // Do something when user press the positive button
            dialog.dismiss()
        }

        // Display a negative button on alert dialog
        builder.setNegativeButton("MÁS INFO..."){ dialog, which ->
            val webpage: Uri = Uri.parse(urlinfo)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            //if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            //}
        }

        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

    override fun onCellClickListener(id: String?) {
        if(TaulaProductesALlistesCrud(ctx).getCantidadRow(id!!.toInt()) > 1){
            adaptadorCad.EliminaUnaUnidad(id.toInt())
        }else{
            adaptadorCad.EliminaRow(id.toInt())
        }

        adaptadorCad.notifyDataSetChanged()
        CarregaRVDatesCaducitat()
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, context: Context, codibarres: String, vede: String, idLl: Int): PlaceholderFragment {
            return PlaceholderFragment(context).apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString("CODIBARRES", codibarres)
                    putString("VEDE", vede)
                    putInt("IDLL", idLl)
                }
            }
        }
    }

}

