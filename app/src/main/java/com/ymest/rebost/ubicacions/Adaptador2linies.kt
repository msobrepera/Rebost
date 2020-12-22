package com.ymest.rebost.ubicacions

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.Llistes
import com.ymest.rebost.json.Ubicacio
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.utils.Constants

class Adaptador2linies(var ctx: Context, var itemsU:ArrayList<Ubicacio>?, var itemsL:ArrayList<Llistes>?, var vede:String, var cellClickListener: CellClickListener, var longClickListener: CellLongClickListener): RecyclerView.Adapter<Adaptador2linies.ViewHolder>()  {

    lateinit var viewHolder: ViewHolder
    var multiseleccion = false
    var itemSeleccionados: ArrayList<Int>? = null

    init{
        itemSeleccionados = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_ubicacions, parent, false)
        viewHolder = ViewHolder(vista)
        //val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemU = itemsU?.get(position)
        val itemL = itemsL?.get(position)
        when(vede){
            Constants.UBICACIONS ->{
                holder.nomUbicacio.text = itemU?.nomubicacio
                holder.nomUbicacio.tag = itemU?.id.toString()
                holder.tag.visibility = View.GONE
                //holder.tag.text = item.id.toString()
                holder.descUbicacio.text = itemU?.descubicacio
                holder.ivCalendar.visibility = View.GONE
                holder.ivHastag.visibility = View.GONE
                holder.ivUbicacio.visibility = View.GONE

                if(itemSeleccionados?.contains(itemU?.id)!!){
                    holder.itemView.setBackgroundColor(Color.LTGRAY)
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE)
                }
            }
            Constants.LLISTES ->{
                holder.nomUbicacio.text = itemL?.nomLlista
                holder.nomUbicacio.tag = itemL?.id.toString()
                holder.tag.visibility = View.GONE
                //holder.tag.text = item.id.toString()
                holder.descUbicacio.visibility = View.GONE

                if(itemL?.gestion_dataCad == 0) holder.ivCalendar.visibility = View.GONE else holder.ivCalendar.visibility = View.VISIBLE
                if(itemL?.gestiona_cantidad == 0) holder.ivHastag.visibility = View.GONE else holder.ivHastag.visibility = View.VISIBLE
                if(itemL?.gestiona_ubicaciones == 0) holder.ivUbicacio.visibility = View.GONE else holder.ivUbicacio.visibility = View.VISIBLE

                if(itemSeleccionados?.contains(itemL?.id)!!){
                    holder.itemView.setBackgroundColor(Color.LTGRAY)
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE)
                }
            }
            Constants.MISLISTAS ->{
                var crudProductosLista = TaulaProductesALlistesCrud(ctx)

                holder.nomUbicacio.text = itemL?.nomLlista
                holder.nomUbicacio.tag = itemL?.id.toString()
                holder.tag.visibility = View.GONE
                //holder.tag.text = item.id.toString()
                holder.descUbicacio.text = crudProductosLista.getNumProductosUnicosXLista(itemL?.id.toString().toInt()).toString() + " productos"

                if(itemL?.gestion_dataCad == 0) holder.ivCalendar.visibility = View.GONE else holder.ivCalendar.visibility = View.VISIBLE
                if(itemL?.gestiona_cantidad == 0) holder.ivHastag.visibility = View.GONE else holder.ivHastag.visibility = View.VISIBLE

                if(itemSeleccionados?.contains(itemL?.id)!!){
                    holder.itemView.setBackgroundColor(Color.LTGRAY)
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE)
                }
            }

            //item = items.get(position) as ArrayList<Llistes>
        }
        //val item = items.get(position)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(holder.nomUbicacio.tag.toString())
        }
        holder.itemView.setOnLongClickListener() {
            longClickListener.onCellLongClickListener(holder.nomUbicacio.tag.toString())
        }


    }

    override fun getItemCount(): Int {
        var itemCount: Int = 0
        when(vede){
            Constants.UBICACIONS -> itemCount = itemsU?.count()!!
            Constants.LLISTES -> itemCount = itemsL?.count()!!
            Constants.MISLISTAS -> itemCount = itemsL?.count()!!
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
        var ivCalendar: ImageView
        var ivHastag: ImageView
        var ivUbicacio: ImageView

        init{
            this.nomUbicacio = vista.findViewById(R.id.tvNomUbicacio)
            this.tag = vista.findViewById(R.id.tvTag)
            this.descUbicacio = vista.findViewById(R.id.tvDescUbicacio)
            this.ivCalendar = vista.findViewById(R.id.ivCalendar)
            this.ivHastag = vista.findViewById(R.id.ivHastag)
            this.ivUbicacio = vista.findViewById(R.id.ivUbicacion)
        }


    }

}