package com.ymest.rebost.categories

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.R
import com.ymest.rebost.json.categories.Tag
import com.ymest.rebost.sqlite.old.ComprarCrud
import com.ymest.rebost.sqlite.old.RebostCrud

class AdaptadorCategories(var ctx: Context, items:ArrayList<Tag>, var tipus:String, var cellClickListener: CellClickListener, var longClickListener: CellLongClickListener): RecyclerView.Adapter<AdaptadorCategories.ViewHolder>() {

    var items: ArrayList<Tag>

    lateinit var viewHolder: ViewHolder
    lateinit var crudRebost: RebostCrud
    lateinit var crudComprar: ComprarCrud
    var sumaProductos: Int = 0
    var primeradatacad:String = ""

    var multiseleccion = false
    var itemSeleccionados: ArrayList<String>? = ArrayList<String>()

    init{
        this.items = items

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var vista : View

        when (tipus){
            "CATEGORIES" -> {
                vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_categories, parent, false)
                viewHolder = ViewHolder(vista)
            }
            "MARQUES"    -> {
                vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_marques, parent, false)
            }
            else -> vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_categories, parent, false)
        }

        viewHolder = ViewHolder(vista)
        return viewHolder!!
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        var separador = ""
        holder.nomCategoria.tag = item.id
        holder.nomCategoria.text = item.name
        holder.numProductes.text = item.products.toString() + " productes"


        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(item.id)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(item.id)
        }

    }

    override fun getItemCount(): Int {
        return items.count()
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

    fun seleccionarItem(index: String) {
        if (multiseleccion) {
            if (itemSeleccionados!!.contains(index)) {
                //Si el item ya está seleccionado, lo quitamos de la lista
                itemSeleccionados?.remove(index)
            } else {
                itemSeleccionados?.add(index)
            }
            notifyDataSetChanged()
        }
    }

    fun obtenerNumSeleccionados():Int{
        return itemSeleccionados?.count()!!
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista) {


        var nomCategoria: TextView
        var numProductes: TextView

        init{
            this.nomCategoria = vista.findViewById(R.id.tvNomCategoria)
            this.numProductes = vista.findViewById(R.id.tvNumProductes)
        }
    }

    class ViewHolderMarques(vista: View): RecyclerView.ViewHolder(vista) {
        var nomMarca: TextView
        var numProductes: TextView

        init{
            this.nomMarca = vista.findViewById(R.id.tvNomMarca)
            this.numProductes = vista.findViewById(R.id.tvProductesMarca)
        }
    }
}
