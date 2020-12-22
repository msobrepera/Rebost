package com.ymest.rebost.detallproductesbuscats.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.json.ProducteALlista
import com.ymest.rebost.nutricio.Nutricio
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.utils.Funcions
import com.ymest.rebost.utils.Funcions.Companion.margin

class AdaptadorCaducitat(var ctx: Context, items:ArrayList<ProducteALlista>, var cellClickListener: CellClickListener): RecyclerView.Adapter<AdaptadorCaducitat.ViewHolder>()  {
    var items:ArrayList<ProducteALlista>
    lateinit var viewHolder: ViewHolder


    init{
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.list_item_txtleft_txtcenter_txtright_oneline, parent, false)
        viewHolder = ViewHolder(vista)
        //val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)


        holder.lista.text = TaulaLlistesCrud(ctx).getLlista(item.idLlista).nomLlista +" (" + item.idUbicacio + ")"
        holder.datacad.text = Funcions.MillisToDate(item.dataCaducitat!!.toLong())
        holder.datacad.tag = item.id
        holder.ivDeleteFecha.tag = item.id
        holder.qdatacad.text = item.quantitat.toString()

        holder.ivDeleteFecha.setOnClickListener {
            cellClickListener.onCellClickListener(item?.id.toString())
        }

        /*holder.ivDeleteFecha.setOnClickListener {
            if(item.quantitat == 1){
                TaulaProductesALlistesCrud(ctx).deleteProducteaLlista(item.id!!.toInt())
                Snackbar.make(holder.ivDeleteFecha, "Eliminado de Fechas de Caducidad", Snackbar.LENGTH_SHORT).show()
            } else {
                TaulaProductesALlistesCrud(ctx).updateCantidadMenosUna(item.id!!.toInt(), item.quantitat!!.toInt() - 1 )
                Snackbar.make(holder.ivDeleteFecha, "Eliminada una unidad", Snackbar.LENGTH_SHORT).show()
                notifyItemChanged(item.id)
            }

        }*/
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun EliminaUnaUnidad(id: Int){
        TaulaProductesALlistesCrud(ctx).updateCantidadMenosUna(id)
    }

    fun EliminaRow(id: Int){
        TaulaProductesALlistesCrud(ctx).deleteProducteaLlista(id)
    }



    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista) {
        var lista: TextView
        var datacad: TextView
        var qdatacad: TextView
        var ivDeleteFecha: ImageView

        init{
            this.lista = vista.findViewById(R.id.tvLeftText)
            this.datacad = vista.findViewById(R.id.tvCenterText)
            this.qdatacad = vista.findViewById(R.id.tvRightText)
            this.ivDeleteFecha = vista.findViewById(R.id.ivDeleteFechaCad)
        }
    }

}