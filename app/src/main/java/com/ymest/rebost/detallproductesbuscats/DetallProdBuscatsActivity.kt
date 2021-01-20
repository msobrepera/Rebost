package com.ymest.rebost.detallproductesbuscats

import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.ymest.rebost.R
import com.ymest.rebost.detallproductesbuscats.ui.main.SectionsPagerAdapter

class DetallProdBuscatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detall_prod_buscats)
        val codi = intent.getStringExtra("ID")
        Log.d("CODI", codi.toString())
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, codi.toString())
        val viewPager: ViewPager = findViewById(R.id.vpDetallProductes)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        /*val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}