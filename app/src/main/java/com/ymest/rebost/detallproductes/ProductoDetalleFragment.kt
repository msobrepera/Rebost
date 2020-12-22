package com.ymest.rebost.detallproductes

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ymest.rebost.R
import com.ymest.rebost.json.Rebost
import com.ymest.rebost.sqlite.old.ProductesCrud
import com.ymest.rebost.sqlite.old.RebostCrud

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProductoDetalleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProductoDetalleFragment(ctx: Context, tabTitle:String, codi:String) : Fragment() {
    // TODO: Rename and change types of parameters
    var ctx:Context
    var tabTitle:String
    var codi: String
    lateinit var rvDetallCaducitats:RecyclerView
    lateinit var adaptador: AdaptadorCaducitat

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var crudRebost: RebostCrud
    lateinit var listaCaducitatRebost: ArrayList<Rebost>
    lateinit var crudProducte: ProductesCrud

    lateinit var tvNomDetallProducte: TextView
    lateinit var tvtamanoDetallProducte: TextView
    lateinit var tvMarcaDetallProducte: TextView
    lateinit var tvBotigaDetallProducte: TextView
    lateinit var tvCodiDetallProducte: TextView
    lateinit var ivFotoDetallProducte: ImageView

    init{
        this.ctx = ctx
        this.tabTitle = tabTitle
        this.codi = codi
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_producto_detalle, container, false)

        rvDetallCaducitats = view.findViewById(R.id.rvDetallCaducitats)
        tvNomDetallProducte = view.findViewById(R.id.tvNomDetallProducte)
        tvtamanoDetallProducte = view.findViewById(R.id.tvtamanoDetallProducte)
        tvMarcaDetallProducte = view.findViewById(R.id.tvMarcaDetallProducte)
        tvBotigaDetallProducte = view.findViewById(R.id.tvBotigaDetallProducte)
        tvCodiDetallProducte = view.findViewById(R.id.tvCodiDetallProducte)
        ivFotoDetallProducte = view.findViewById(R.id.ivFotoDetallProducte)
        carregaResumProducte()
        carregaDetallCaducitats()

        // Inflate the layout for this fragment
        return view
    }



    private fun carregaResumProducte() {
        /*crudProducte = ProductesCrud(ctx)
        var producte = crudProducte.getProducte(codi)
        Log.d("PRODUCTE", producte.toString())
        tvNomDetallProducte.text = producte.product?.productNameEs
        tvtamanoDetallProducte.text = producte.product?.quantity
        tvMarcaDetallProducte.text = producte.product?.brands
        tvBotigaDetallProducte.text = producte.product?.stores
        tvCodiDetallProducte.text = producte.code
        Picasso.get().load(producte.product?.imageUrl).into(ivFotoDetallProducte)*/
    }

    private fun carregaDetallCaducitats() {
        rvDetallCaducitats.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(ctx)
        rvDetallCaducitats.layoutManager = layoutManager

        crudRebost = RebostCrud(ctx)
        listaCaducitatRebost = crudRebost.getLlistaCaducitats(codi)
        Log.d("LLISTACADUCITAT", listaCaducitatRebost.toString())
        adaptador = AdaptadorCaducitat(ctx, listaCaducitatRebost, tabTitle)
        rvDetallCaducitats.adapter = adaptador
    }




}