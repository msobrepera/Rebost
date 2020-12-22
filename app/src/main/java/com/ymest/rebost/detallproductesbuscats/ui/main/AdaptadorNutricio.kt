package com.ymest.rebost.detallproductesbuscats.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ymest.rebost.R
import com.ymest.rebost.nutricio.Nutricio
import com.ymest.rebost.utils.Funcions.Companion.margin

class AdaptadorNutricio(var ctx: Context, items:ArrayList<Nutricio>): RecyclerView.Adapter<AdaptadorNutricio.ViewHolder>()  {
    var items:ArrayList<Nutricio>
    lateinit var viewHolder: ViewHolder


    init{
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.list_item_txtleft_txtright_oneline, parent, false)
        viewHolder = ViewHolder(vista)
        //val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        if(item.NomNutricio.equals(ctx.getString(R.string.grasas_sat)) ||
            (item.NomNutricio.equals(ctx.getString(R.string.azucar))) ||
            (item.NomNutricio.equals(ctx.getString(R.string.sodi)))){
                //Funció "margin" a l'arxiu Funcions.kt
                holder.nomNutrient.margin(left = 32F)
        }

        holder.nomNutrient.text = item.NomNutricio
        holder.qNutrient.text = item.qx100Nutricio + item.unitNutricio
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista) {
        var nomNutrient: TextView
        var qNutrient: TextView

        init{
            this.nomNutrient = vista.findViewById(R.id.tvLeftText)
            this.qNutrient = vista.findViewById(R.id.tvRightText)
        }
    }

}