package com.ymest.rebost.ubicacions

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.Llistes
import com.ymest.rebost.json.Ubicacio

/*
class AdaptadorLlistes(var ctx: Context, items:ArrayList<Llistes>, var cellClickListener: CellClickListener, var longClickListener: CellLongClickListener): RecyclerView.Adapter<AdaptadorLlistes.ViewHolder>()  {
    var items:ArrayList<Llistes>
    lateinit var viewHolder: ViewHolder
    var multiseleccion = false
    var itemSeleccionados: ArrayList<Int>? = null

    init{
        this.items = items
        itemSeleccionados = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_ubicacions, parent, false)
        viewHolder = ViewHolder(vista)
        //val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        holder.nomUbicacio.text = item.nomLlista
        holder.nomUbicacio.tag = item.id.toString()
        holder.tag.visibility = View.GONE
        //holder.tag.text = item.id.toString()
        holder.descUbicacio.visibility = View.GONE

        if(itemSeleccionados?.contains(item.id)!!){
            holder.itemView.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(holder.nomUbicacio.tag.toString())
        }
        holder.itemView.setOnLongClickListener() {
            longClickListener.onCellLongClickListener(holder.nomUbicacio.tag.toString())
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

    fun seleccionarItem(index: Int) {
        if (multiseleccion) {
            if (itemSeleccionados?.contains(index)!!) {
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
        var nomUbicacio: TextView
        var descUbicacio: TextView
        var tag:TextView

        init{
            this.nomUbicacio = vista.findViewById(R.id.tvNomUbicacio)
            this.tag = vista.findViewById(R.id.tvTag)
            this.descUbicacio = vista.findViewById(R.id.tvDescUbicacio)
        }


    }
}*/
