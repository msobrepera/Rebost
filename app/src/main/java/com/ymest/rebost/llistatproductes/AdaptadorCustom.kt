package com.ymest.rebost.llistatproductes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.ListaPersonalizadaProducto
import com.ymest.rebost.json.Producto
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import com.ymest.rebost.sqlite.TaulaPersonalitzadaCrud
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.sqlite.old.ComprarCrud
import com.ymest.rebost.sqlite.old.DataBaseHelperOld
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions
import com.ymest.rebost.utils.Funcions.Companion.dpToPixels


class AdaptadorCustom(
    var ctx: Context,
    items: ArrayList<Producto>?,
    itemsp: ArrayList<ListaPersonalizadaProducto>?,
    tabTitle: String,
    idLista: Int?,
    var cellClickListener: CellClickListener,
    var longClickListener: CellLongClickListener
): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items: ArrayList<Producto>?
    var itemsp: ArrayList<ListaPersonalizadaProducto>?
    var tabTitle: String
    lateinit var viewHolder: ViewHolder
    lateinit var crudProductesALlista: TaulaProductesALlistesCrud
    lateinit var crudComprar: ComprarCrud
    var dataCad: Long = 0
    var primeradatacad: String = ""
    var selPosition: Int = 0
    var idLista:Int?

    var multiseleccion = false
    var itemSeleccionados: ArrayList<String>? = ArrayList<String>()

    init{
        this.items = items
        this.itemsp = itemsp
        this.tabTitle = tabTitle
        this.idLista = idLista
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_item,
            parent,
            false
        )
        Log.d("TAG1", "Entra al Adaptador Custom")
        viewHolder = ViewHolder(vista)
        return viewHolder!!
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG1", tabTitle)
        when (tabTitle){
            Constants.TAB_PRIMERA -> {
                val item = items?.get(position)
                holder.quantitat.text = TaulaProductesALlistesCrud(ctx).getCantidadProducto(2, item?.code.toString()).toString()
                cargaPrimeraTAB(holder, item)
                cargaProducto(holder, item)
            }
            Constants.TAB_SEGONA -> {
                val item = items?.get(position)
                holder.groupPersonalizado.visibility = View.GONE
                holder.quantitat.text = TaulaProductesALlistesCrud(ctx).getCantidadProducto(1, item?.code.toString()).toString()
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
                cargaProducto(holder, item)
            }
            Constants.TAB_TERCERA -> {
                val item = items?.get(position)
                holder.groupPersonalizado.visibility = View.GONE
                holder.quantitat.visibility = View.GONE
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
                cargaProducto(holder, item)
            }
            Constants.HISTORIAL-> {
                Log.d("TAG1", "Constants.Historial")
                val item = items?.get(position)
                holder.groupPersonalizado.visibility = View.GONE
                holder.quantitat.visibility = View.GONE
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
                cargaProducto(holder, item)
            }
            Constants.MISLISTAS -> {
                val itemp = itemsp?.get(position)
                cargaProductoPersonalizado(holder, itemp)
            }
            Constants.MISUBICACIONES -> {
                val itemp = itemsp?.get(position)
                cargaProductoPersonalizado(holder, itemp)
            }

            "LISTPRODCAT" -> {
                val item = items?.get(position)
                holder.groupPersonalizado.visibility = View.GONE
                holder.quantitat.visibility = View.GONE
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
                cargaProducto(holder, item)
            }
            Constants.MAINACT -> {
                val item = items?.get(position)
                holder.groupPersonalizado.visibility = View.GONE
                holder.quantitat.visibility = View.GONE
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
                cargaProducto(holder, item)
            }
        }

        /*holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(item.code)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(item.code)
        }*/

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun cargaProductoPersonalizado(holder: ViewHolder, itemp: ListaPersonalizadaProducto?) {
        var separador = ""

        if(itemSeleccionados?.contains(itemp?.code)!!){
            holder.cvListaProductos.radius = 10.dpToPixels(ctx)
            holder.cvListaProductos.setCardBackgroundColor(Color.LTGRAY)
            //holder.cvListaProductos.setBackgroundColor(Color.LTGRAY)

        } else {
            holder.cvListaProductos.radius = 10.dpToPixels(ctx)
            holder.cvListaProductos.setCardBackgroundColor(Color.WHITE)
            //holder.cvListaProductos.setBackgroundColor(Color.WHITE)
        }
        when(tabTitle){
            Constants.MISLISTAS->{
                if(TaulaLlistesCrud(ctx).gestionaCantidad(idLista)){
                    if(idLista == 3){
                        holder.quantitat.text = TaulaProductesALlistesCrud(ctx).getCantidadDeUnProductoCaducado(itemp?.code.toString(), Funcions.obtenirDataActualMillis()).toString()
                    }else {
                        holder.quantitat.text = TaulaProductesALlistesCrud(ctx).getCantidadProducto(idLista, itemp?.code.toString()).toString()
                    }
                } else {
                    holder.quantitat.visibility = View.GONE
                }
            }
            Constants.MISUBICACIONES->{
                holder.quantitat.text = TaulaProductesALlistesCrud(ctx).getCantidadProductoXUbicacion(idLista, itemp?.code.toString()).toString()
            }
        }

        if(TaulaLlistesCrud(ctx).gestionaFechas(idLista)){
            var primeraDataCad: String = Funcions.MillisToDate(TaulaProductesALlistesCrud(ctx).getPrimeraDataCaducitat(itemp?.code.toString()))
            holder.datacad.text = primeraDataCad
            var tempsPerCaducar = Funcions.ComparaData(TaulaProductesALlistesCrud(ctx).getPrimeraDataCaducitat(itemp?.code.toString()))
            if(tempsPerCaducar<0){
                holder.alertaCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_red)
            } else if(tempsPerCaducar < Constants.TIEMPO_AVISO_CADUCIDAD_MS && tempsPerCaducar > 0){
                holder.alertaCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_orange)
            } else {
                holder.alertaCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_green)
            }
        } else {
            holder.datacad.visibility = View.GONE
            holder.alertaCaducitat.visibility = View.GONE
        }

        Log.d("SUMREBOST", itemp.toString())

        if(!itemp?.product?.productNameEs.isNullOrEmpty() && itemp?.product?.productNameEs != "null") {
            if (itemp?.product?.productNameEs?.length!! > 20) {
                holder.nomArticle.text = itemp?.product.productNameEs.substring(0, 20) + "..."
            } else{
                holder.nomArticle.text = itemp.product.productNameEs
            }
        }else if(!itemp?.product?.productName.isNullOrEmpty() && itemp?.product?.productName != "null"){
            holder.nomArticle.text = itemp?.product?.productName
        }else if(!itemp?.product?.genericNameEs.isNullOrEmpty() && itemp?.product?.genericNameEs != "null"){
            holder.nomArticle.text = itemp?.product?.genericNameEs
        }else if(!itemp?.product?.genericName.isNullOrEmpty() && itemp?.product?.genericName != "null"){
            holder.nomArticle.text = itemp?.product?.genericName
        }

        /*if(itemp?.product?.productNameEs?.length!!>20){
            holder.nomArticle.text = itemp?.product.productNameEs.substring(0, 20) + "..."
        }else if(itemp.product.productNameEs.length == 0) {
            holder.nomArticle.text = itemp?.product.productName
        }else{
            holder.nomArticle.text = itemp.product.productNameEs
        }*/
        holder.tamano.text = itemp?.product?.quantity
        holder.codibarres.text = itemp?.code

        holder.marcaArticle.text = itemp?.product?.brands //+ separador + itemp.product.stores
        //holder.quantitat.text = sumaProductos.toString()
        if(!itemp?.product?.imageUrl.isNullOrEmpty()) {
            Picasso.get().load(itemp?.product?.imageUrl).into(holder.foto)
        }

        holder.rbPersonalitzat.rating = itemp?.personalitzat?.puntuacio!!.toFloat()
        if(itemp.personalitzat.favorit == 1){
            holder.ivFavoritoPersonalitzat.setImageResource(R.drawable.ic_baseline_favorite_24)
            holder.ivFavoritoPersonalitzat.tag = 1
        }else{
            holder.ivFavoritoPersonalitzat.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            holder.ivFavoritoPersonalitzat.tag = 0
        }
        when(itemp.personalitzat.gust){
            1 -> {
                holder.ivSaborPersonalitzat.setImageResource(R.drawable.ic_cara_feliz)
                holder.ivSaborPersonalitzat.tag = 1
            }
            2 -> {
                holder.ivSaborPersonalitzat.setImageResource(R.drawable.ic_cara_esceptico)
                holder.ivSaborPersonalitzat.tag = 2
            }
            3 -> {
                holder.ivSaborPersonalitzat.setImageResource(R.drawable.ic_cara_triste)
                holder.ivSaborPersonalitzat.tag = 3
            }
        }
        holder.ivFavoritoPersonalitzat.setOnClickListener {
            if(holder.ivFavoritoPersonalitzat.tag == 1){
                holder.ivFavoritoPersonalitzat.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                holder.ivFavoritoPersonalitzat.tag = 0
            }else {
                holder.ivFavoritoPersonalitzat.setImageResource(R.drawable.ic_baseline_favorite_24)
                holder.ivFavoritoPersonalitzat.tag = 1
            }
            TaulaPersonalitzadaCrud(ctx).updateFavorito(
                itemp.code,
                holder.ivFavoritoPersonalitzat.tag.toString().toInt()
            )
        }

        holder.ivSaborPersonalitzat.setOnClickListener {
            when(holder.ivSaborPersonalitzat.tag){
                1 -> {
                    holder.ivSaborPersonalitzat.setImageResource(R.drawable.ic_cara_esceptico)
                    holder.ivSaborPersonalitzat.tag = 2
                }
                2 -> {
                    holder.ivSaborPersonalitzat.setImageResource(R.drawable.ic_cara_triste)
                    holder.ivSaborPersonalitzat.tag = 3
                }
                3 -> {
                    holder.ivSaborPersonalitzat.setImageResource(R.drawable.ic_cara_feliz)
                    holder.ivSaborPersonalitzat.tag = 1
                }
            }
            TaulaPersonalitzadaCrud(ctx).updateSabor(
                itemp.code,
                holder.ivSaborPersonalitzat.tag.toString().toInt()
            )
        }

        holder.rbPersonalitzat.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = rating
            TaulaPersonalitzadaCrud(ctx).updatePuntuacio(itemp.code, rating)

        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(itemp.code)
        }
        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(itemp.code)
        }
    }

    private fun cargaProducto(holder: ViewHolder, item: Producto?) {
        var separador = ""

        if(itemSeleccionados?.contains(item?.code)!!){
            holder.cvListaProductos.radius = 10.dpToPixels(ctx)
            holder.cvListaProductos.setCardBackgroundColor(Color.LTGRAY)
            //holder.cvListaProductos.setBackgroundColor(Color.LTGRAY)

        } else {
            holder.cvListaProductos.radius = 10.dpToPixels(ctx)
            holder.cvListaProductos.setCardBackgroundColor(Color.WHITE)
            //holder.cvListaProductos.setBackgroundColor(Color.WHITE)
        }

        Log.d("SUMREBOST", item.toString())
        if(!item?.product?.productNameEs.isNullOrEmpty() && item?.product?.productNameEs != "null"){
            if (item?.product?.productNameEs?.length!! > 20) {
                holder.nomArticle.text = item?.product.productNameEs.substring(0, 20) + "..."
            } else{
                holder.nomArticle.text = item.product.productNameEs
            }
        }else if(!item?.product?.productName.isNullOrEmpty() && item?.product?.productName != "null"){
            holder.nomArticle.text = item?.product?.productName
        }else if(!item?.product?.genericNameEs.isNullOrEmpty() && item?.product?.genericNameEs != "null"){
            holder.nomArticle.text = item?.product?.genericNameEs
        }else if(!item?.product?.genericName.isNullOrEmpty() && item?.product?.genericName != "null"){
            holder.nomArticle.text = item?.product?.genericName
        }
        holder.tamano.text = item?.product?.quantity
        if(tabTitle == Constants.TAB_PRIMERA){
            holder.codibarres.visibility = View.GONE
            holder.ivCodiBarres.visibility = View.GONE
        }
        holder.codibarres.text = item?.code

        holder.marcaArticle.text = item?.product?.brands //+ separador + item.product.stores
        //holder.quantitat.text = sumaProductos.toString()
        if(!item?.product?.imageUrl.isNullOrEmpty()) {
            Picasso.get().load(item?.product?.imageUrl).into(holder.foto)
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(item?.code)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(item?.code)
        }
    }

    private fun cargaPrimeraTAB(holder: ViewHolder, item: Producto?) {
        holder.groupPersonalizado.visibility = View.GONE
        crudProductesALlista = TaulaProductesALlistesCrud(ctx)
        Log.d("ITEM", item?.code.toString())
        dataCad = crudProductesALlista.getPrimeraDataCaducitat(item?.code.toString())
        Log.d("DATACADREC", dataCad.toString())
        holder.datacad.text =
            Funcions.MillisToDate(dataCad)
        if (if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Funcions.ComparaData(dataCad) > Constants.TIEMPO_AVISO_CADUCIDAD_HORAS * 3600000
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        ) {
            holder.alertaCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_green)
        } else if (if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Funcions.ComparaData(dataCad) > 0
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        ) {
            holder.alertaCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_orange)
        } else {
            holder.alertaCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_red)
        }
    }

    override fun getItemCount(): Int {
        var itemCount: Int = 0
        when(tabTitle){
            Constants.TAB_PRIMERA, Constants.TAB_SEGONA, Constants.TAB_TERCERA, "LISTPRODCAT", Constants.HISTORIAL -> itemCount =
                items?.count()!!
            Constants.MISLISTAS, Constants.MISUBICACIONES -> itemCount = itemsp?.count()!!
        }
        return itemCount
    }

    fun iniciarActionMode() {
        multiseleccion = true
    }

    fun destruirActionMode() {
        multiseleccion = false
        itemSeleccionados?.clear()
        notifyDataSetChanged()
    }

    fun terminarActionMode() {
        //eliminar elementos seleccionados
        for (item in itemSeleccionados!!) {
            itemSeleccionados?.remove(item)
        }
        multiseleccion = false
    }

    fun seleccionarItem(index: String?) {
        if (multiseleccion) {
            if (itemSeleccionados!!.contains(index)) {
                //Si el item ya está seleccionado, lo quitamos de la lista
                itemSeleccionados?.remove(index)
            } else {
                itemSeleccionados?.add(index!!)
            }
            notifyDataSetChanged()
        }
    }

    fun deleteSelccionados(){
        for (item in itemSeleccionados!!) {
            //itemSeleccionados?.remove(item)
            TaulaProductesALlistesCrud(ctx).deleteUnProducteDeUnaLlista(item, idLista!!.toInt())
            notifyDataSetChanged()
            Log.d("DELETE", "ITEM: " + item + " Eliminado")
        }
        destruirActionMode()

    }

    fun obtenerNumSeleccionados():Int{
        return itemSeleccionados?.count()!!
    }

    fun getSelectedPosition(): Int {
        return selPosition
    }

    fun setSelectedPosition(selectedPosition: Int) {
        selPosition = selectedPosition
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista) {

        var nomArticle: TextView
        var marcaArticle: TextView
        var foto: ImageView
        var tamano: TextView
        var quantitat: TextView
        var codibarres: TextView
        var ivCodiBarres: ImageView
        var datacad: TextView
        var alertaCaducitat: ImageView
        var cvListaProductos : CardView
        var groupPersonalizado: Group
        var rbPersonalitzat: RatingBar
        var ivFavoritoPersonalitzat: ImageView
        var ivSaborPersonalitzat: ImageView
        var clProducto: ConstraintLayout

        init{
            this.clProducto = vista.findViewById(R.id.clProducto)
            this.nomArticle = vista.findViewById(R.id.tvNomArticle)
            this.marcaArticle = vista.findViewById(R.id.tvMarca)
            this.foto = vista.findViewById(R.id.ivMiniatura)
            this.tamano = vista.findViewById(R.id.tvTamano)
            this.quantitat = vista.findViewById(R.id.tvquantitat)
            this.codibarres = vista.findViewById(R.id.tvcodibarres)
            this.ivCodiBarres = vista.findViewById(R.id.ivCodiBarres)
            this.datacad = vista.findViewById(R.id.tvfechacad)
            this.alertaCaducitat = vista.findViewById(R.id.ivalertacaducitat)
            this.cvListaProductos = vista.findViewById(R.id.cvListaProductos)
            this.groupPersonalizado = vista.findViewById(R.id.groupPersonalizado)
            this.rbPersonalitzat = vista.findViewById(R.id.rblistaProductos)
            this.ivFavoritoPersonalitzat = vista.findViewById(R.id.ivFavoritoListaProductos)
            this.ivSaborPersonalitzat = vista.findViewById(R.id.ivSaborListaProductos)
        }
    }
}
