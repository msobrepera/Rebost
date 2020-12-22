package com.ymest.rebost.detallproductes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ymest.rebost.R
import com.ymest.rebost.llistatproductes.ViewPageAdapter
import com.ymest.rebost.utils.Constants
import kotlinx.android.synthetic.main.activity_producto_detalle.*

class ProductoDetalleActivity : AppCompatActivity() {

    lateinit var viewPagerDetallProducte: ViewPager
    lateinit var tabLayoutDetallProducte: TabLayout
    var tabSeleccionadaDetallProducte: String = Constants.TAB_PRIMERA
    lateinit var codi : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto_detalle)

        codi = intent.getStringExtra("CODI").toString()

        setSupportActionBar(toolbarDetallProducte)
        viewPagerDetallProducte = findViewById(R.id.vpDetallProducte)
        tabLayoutDetallProducte = findViewById(R.id.tabDetallProducte)
        setUpViewPager()
        tabLayoutDetallProducte.setupWithViewPager(viewPagerDetallProducte)
    }

    fun setUpViewPager(){

        val adapter: ViewPageAdapter = ViewPageAdapter(supportFragmentManager)
        adapter.addFragment(ProductoDetalleFragment(this, Constants.TAB_DP_PRIMERA, codi), Constants.TAB_DP_PRIMERA)
        adapter.addFragment(ProductoDetalleFragment(this, Constants.TAB_DP_SEGONA, codi), Constants.TAB_DP_SEGONA)
        adapter.addFragment(ProductoDetalleFragment(this, Constants.TAB_DP_TERCERA, codi), Constants.TAB_DP_TERCERA)
        viewPagerDetallProducte.adapter = adapter

    }
}