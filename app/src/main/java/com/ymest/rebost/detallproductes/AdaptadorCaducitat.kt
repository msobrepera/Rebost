package com.ymest.rebost.detallproductes

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ymest.rebost.R
import com.ymest.rebost.json.Rebost
import com.ymest.rebost.sqlite.old.RebostCrud
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions

class AdaptadorCaducitat(var ctx: Context, items:ArrayList<Rebost>, tabTitle:String): RecyclerView.Adapter<AdaptadorCaducitat.ViewHolder>() {

    var items: ArrayList<Rebost>
    var tabTitle: String
    lateinit var viewHolder: ViewHolder
    lateinit var crudRebost: RebostCrud

    var multiseleccion = false
    var itemSeleccionados: ArrayList<Int>? = null

    init{
        this.items = items
        this.tabTitle = tabTitle
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_detall_caducitats, parent, false)
        viewHolder = ViewHolder(vista)
        //val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)
        var separador = ""



        when (tabTitle) {
            Constants.TAB_DP_PRIMERA -> {
                holder.tvDataCaducitat.text = Funcions.MillisToDate(item.datacaducitat)
                holder.tvUbicacio.text = item.idubicacio.toString()
                holder.tvquantitat.text = item.quantitat.toString()
                holder.ivIconoCaducitat
                if (Funcions.ComparaData(item.datacaducitat) > Constants.TIEMPO_AVISO_CADUCIDAD_HORAS * 3600000) {
                    holder.ivIconoCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_green)
                } else if (Funcions.ComparaData(item.datacaducitat) > 0) {
                    holder.ivIconoCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_orange)
                } else {
                    holder.ivIconoCaducitat.setImageResource(R.drawable.ic_senal_de_precaucion_red)
                }
            }
        }
    }
           /* Constants.TAB_SEGONA ->{
                crudComprar = ComprarCrud(ctx)
                sumaProductos = crudComprar.getSumaQuantitats(item.code)
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
            }
            Constants.TAB_TERCERA->{
                holder.quantitat.visibility = View.GONE
                holder.datacad.visibility = View.GONE
                holder.alertaCaducitat.visibility = View.GONE
            }
        }

        Log.d("SUMREBOST", sumaProductos.toString())
        if(item.product.product_name_es.length>20){
            holder.nomArticle.text = item.product.product_name_es.substring(0,20) + "..."
        }else if(item.product.product_name_es.length == 0) {
            holder.nomArticle.text = item.product.product_name
        }else{
            holder.nomArticle.text = item.product.product_name_es
        }
        holder.tamano.text = item.product.quantity
        holder.codibarres.text = item.code

        if(!item.product.brands.isEmpty() && !item.product.stores.isEmpty()){
            separador = " - "
        } else separador = ""
        holder.marcaArticle.text = item.product.brands + separador + item.product.stores
        holder.quantitat.text = sumaProductos.toString()
        Picasso.get().load(item.product.image_url).into(holder.foto)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(item.code)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener.onCellLongClickListener(item.code)
        }

    }*/

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
        var tvDataCaducitat: TextView
        var tvUbicacio: TextView
        var tvquantitat: TextView
        var ivIconoCaducitat: ImageView

        init{
            this.tvDataCaducitat = vista.findViewById(R.id.tvDataCaducitat)
            this.tvUbicacio = vista.findViewById(R.id.tvUbicacioDetallCaducitat)
            this.tvquantitat = vista.findViewById(R.id.tvQuantitatCaducitat)
            this.ivIconoCaducitat = vista.findViewById(R.id.ivIconoCaducitat)
        }
    }
}
