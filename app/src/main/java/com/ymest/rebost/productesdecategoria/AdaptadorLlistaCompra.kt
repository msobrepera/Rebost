package com.ymest.rebost.productesdecategoria

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellClickListenerCompra
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
import kotlinx.android.synthetic.main.recycler_view_item_llista_compra.*


class AdaptadorLlistaCompra(
    var ctx: Context,
    items: ArrayList<Producto>?,
    idLista: Int?,
    var cellClickListener: CellClickListenerCompra,
    var longClickListener: CellLongClickListener
): RecyclerView.Adapter<AdaptadorLlistaCompra.ViewHolder>() {

    var items: ArrayList<Producto>?
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
        this.idLista = idLista
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_item_llista_compra,
            parent,
            false
        )
        Log.d("TAG1", "Entra al Adaptador Llista Compra")
        viewHolder = ViewHolder(vista)
        return viewHolder!!
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("TAG1", "When Tab Title")
        val item = items?.get(position)
        holder.tvQuantityLlistaCompra.text = TaulaProductesALlistesCrud(ctx).getCantidadProducto(idLista, item?.code.toString()).toString()
        cargaProducto(holder, item)

        /*holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(item.code)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(item.code)
        }*/

    }

    private fun cargaProducto(holder: ViewHolder, item: Producto?) {
        var separador = ""

        /*if(itemSeleccionados?.contains(item?.code)!!){
            holder.cvListaProductos.radius = 10.dpToPixels(ctx)
            holder.cvListaProductos.setCardBackgroundColor(Color.LTGRAY)
            //holder.cvListaProductos.setBackgroundColor(Color.LTGRAY)

        } else {
            holder.cvListaProductos.radius = 10.dpToPixels(ctx)
            holder.cvListaProductos.setCardBackgroundColor(Color.WHITE)
            //holder.cvListaProductos.setBackgroundColor(Color.WHITE)
        }*/

        Log.d("SUMREBOST", item.toString())

        if(estacomprat(item?.code.toString())){
            holder.tvNomProducteLlistaCompra.setTextColor(Color.LTGRAY)
            holder.tvMarcaProducteLlistaCompra.setTextColor(Color.LTGRAY)
            holder.tvQuantityLlistaCompra.setTextColor(Color.LTGRAY)
            holder.tvNomProducteLlistaCompra.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvMarcaProducteLlistaCompra.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.tvQuantityLlistaCompra.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.ivProducteLlistaCompra.alpha = 0.5F
            holder.ivMarcarComprado.setImageResource(R.drawable.ic_anadir_al_carrito)

        } else {
            holder.tvNomProducteLlistaCompra.setTextColor(Color.BLACK)
            holder.tvMarcaProducteLlistaCompra.setTextColor(Color.BLACK)
            holder.tvQuantityLlistaCompra.setTextColor(Color.BLACK)
            holder.tvNomProducteLlistaCompra.paintFlags = 0
            holder.tvMarcaProducteLlistaCompra.paintFlags = 0
            holder.tvQuantityLlistaCompra.paintFlags = 0
            holder.ivProducteLlistaCompra.alpha = 1.0F
            holder.ivMarcarComprado.setImageResource(R.drawable.ic_anadir_al_carrito_primary)
        }

        holder.tvNomProducteLlistaCompra.text = buscaNomProducte(item)
        holder.tvNomProducteLlistaCompra.tag = item?.code

        holder.tvMarcaProducteLlistaCompra.text = item?.product?.brands

        if(!item?.product?.imageUrl.isNullOrEmpty()) {
            Picasso.get().load(item?.product?.imageUrl).into(holder.ivProducteLlistaCompra)
        }

        holder.ivMarcarComprado.setOnClickListener {
            cellClickListener.onCellClickListener(item?.code, it)
        }
        holder.tvQuantityLlistaCompra.setOnClickListener {
            cellClickListener.onCellClickListener(item?.code, it)
        }

        /*holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(item?.code)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(item?.code)
        }*/
    }

    private fun estacomprat(cb:String): Boolean {
        var comprat: Int = TaulaProductesALlistesCrud(ctx).getSiEstaComprat(cb)
        return comprat != 0
    }

    private fun buscaNomProducte(item: Producto?): String {
        var nomProducte:String = ""
        if(!item?.product?.productNameEs.isNullOrEmpty() && item?.product?.productNameEs != "null"){
            nomProducte = item?.product?.productNameEs.toString()
        }else if(!item?.product?.productName.isNullOrEmpty() && item?.product?.productName != "null"){
            nomProducte = item?.product?.productName.toString()
        }else if(!item?.product?.genericNameEs.isNullOrEmpty() && item?.product?.genericNameEs != "null"){
            nomProducte = item?.product?.genericNameEs.toString()
        }else if(!item?.product?.genericName.isNullOrEmpty() && item?.product?.genericName != "null"){
            nomProducte = item?.product?.genericName.toString()
        }

        nomProducte = nomProducte.toLowerCase().capitalize()
        if(nomProducte.length > 20){
            nomProducte = nomProducte.substring(0,20) + "..."
        }

        return nomProducte
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    fun marcarComoComprado(cb:String) {
        if(estacomprat(cb)){
            TaulaProductesALlistesCrud(ctx).updateComprat(cb, 0)
        } else {
            TaulaProductesALlistesCrud(ctx).updateComprat(cb, 1)
        }
        notifyDataSetChanged()

    }

    fun mostraADNumberPicker(cb:String) {
        val numberPicker = NumberPicker(ctx)
        var cantidadSeleccionada: Int = 0
        numberPicker.maxValue = 30
        numberPicker.minValue = 0
        numberPicker.value = TaulaProductesALlistesCrud(ctx).getCantidadProducto(idLista, cb)


        val builder = AlertDialog.Builder(ctx)
        builder.setView(numberPicker)
        builder.setTitle("Selecciona cantidad")
        builder.setMessage("")
        builder.setPositiveButton("OK") { dialog, which ->
            cantidadSeleccionada = numberPicker.value
            TaulaProductesALlistesCrud(ctx).updateCantidad(cantidadSeleccionada, idLista!!.toInt(), cb, 0)
            notifyDataSetChanged()
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> dialog.dismiss() }
        builder.create()
        builder.show()
    }

    fun deleteComprats(){
        TaulaProductesALlistesCrud(ctx).deleteProductesComprats()
        notifyDataSetChanged()
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

        var tvNomProducteLlistaCompra: TextView
        var tvMarcaProducteLlistaCompra: TextView
        var ivProducteLlistaCompra: ImageView
        var tvQuantityLlistaCompra: TextView
        var ivMarcarComprado: ImageView


        init{
            this.tvNomProducteLlistaCompra = vista.findViewById(R.id.tvNomProducteLlistaCompra)
            this.tvMarcaProducteLlistaCompra = vista.findViewById(R.id.tvMarcaProducteLlistaCompra)
            this.ivProducteLlistaCompra = vista.findViewById(R.id.ivProducteLlistaCompra)
            this.tvQuantityLlistaCompra = vista.findViewById(R.id.tvQuantityLlistaCompra)
            this.ivMarcarComprado = vista.findViewById(R.id.ivMarcarComprado)
        }
    }
}
