package com.ymest.rebost.detallproductesbuscats.ui.main

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.Ubicacio
import com.ymest.rebost.nutricio.Nutricio
import com.ymest.rebost.utils.Funcions.Companion.margin

class AdaptadorImatges(var ctx: Context, items:ArrayList<String>): RecyclerView.Adapter<AdaptadorImatges.ViewHolder>()  {
    var items:ArrayList<String>
    lateinit var viewHolder: ViewHolder


    init{
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_imatges, parent, false)
        viewHolder = ViewHolder(vista)
        //val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        Picasso.get().load(item).into(holder.ivProductoImatges)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista) {
        var ivProductoImatges: ImageView

        init{
            this.ivProductoImatges = vista.findViewById(R.id.ivProductoImatges)
        }
    }

}