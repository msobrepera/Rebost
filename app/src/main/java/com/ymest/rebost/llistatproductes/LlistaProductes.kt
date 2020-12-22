package com.ymest.rebost.llistatproductes


import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.ymest.rebost.MainActivity
import com.ymest.rebost.NavigationView.ClickListener
import com.ymest.rebost.NavigationView.NavigationItemModel
import com.ymest.rebost.NavigationView.NavigationRVAdapter
import com.ymest.rebost.NavigationView.RecyclerTouchListener
import com.ymest.rebost.R
import com.ymest.rebost.ajustes.AjustesActivity
import com.ymest.rebost.categories.CategoriesActivity
import com.ymest.rebost.scan.ScanActivity
import com.ymest.rebost.ubicacions.UbicacionsActivity
import com.ymest.rebost.utils.Constants
import kotlinx.android.synthetic.main.activity_llista_productes.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*
import kotlinx.android.synthetic.main.recycler_view_row_navigation.*
import kotlinx.android.synthetic.main.recycler_view_row_navigation.view.*


class LlistaProductes : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout
    var tabSeleccionada: String = Constants.TAB_PRIMERA
    lateinit var adapter: ViewPageAdapter

    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapterNavigation: NavigationRVAdapter

    private var items = arrayListOf(
        NavigationItemModel(R.drawable.ic_shopping_basket_24px_black, "Inicio"),
        NavigationItemModel(R.drawable.ic_scan_codigo_de_barras_24, "Escanear Producto"),
        NavigationItemModel(R.drawable.ic_search_black_24dp, "Buscar Producto"),
        NavigationItemModel(R.drawable.ic_baseline_remove_red_eye_24, "Ver Mis Listas"),
        NavigationItemModel(R.drawable.ic_marca, "Marcas"),
        NavigationItemModel(R.drawable.ic_baseline_category_24, "Categorias"),
        NavigationItemModel(R.drawable.ic_baseline_settings_24, "Ajustes"),
        NavigationItemModel(R.drawable.ic_baseline_info_24, "Acerca de")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llista_productes)

        drawerLayout = findViewById(R.id.drawer_layout)

        setSupportActionBar(toolbarLlistaProductes)
        toolbarLlistaProductes.setNavigationOnClickListener(View.OnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
            /*updateAdapter(0)
            Toast.makeText(
                applicationContext,
                "your icon was clicked",
                Toast.LENGTH_SHORT
            ).show()*/
        })
        navigation_header_img.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
        tvNavigationHeader.setTextColor(Color.WHITE)
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)


        // Add Item Touch Listener
        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    //INICIO
                    0 -> {
                        // # Home Fragment
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    }
                    //ESCANER
                    1 -> {
                        intent = Intent(applicationContext, ScanActivity::class.java)
                        startActivity(intent)
                    }
                    //BUSCAR PRODUCTOS
                    2 -> {
                        Snackbar.make(navigation_header_img,getString(R.string.SB_EnDesarrollo) ,Snackbar.LENGTH_LONG).show()
                        /*intent = Intent(this@LlistaProductes, CategoriesActivity::class.java)
                        intent.putExtra("VEDE", Constants.CATEGORIES)
                        startActivity(intent)*/
                    }
                    //VER MIS LISTAS
                    3 -> {
                        //Snackbar.make(navigation_header_img,getString(R.string.SB_EnDesarrollo) ,Snackbar.LENGTH_LONG).show()
                        intent = Intent(applicationContext, UbicacionsActivity::class.java)
                        intent.putExtra("VEDE", Constants.MISLISTAS)
                        startActivity(intent)
                    }
                    //MARCAS
                    4 -> {
                        intent = Intent(applicationContext, CategoriesActivity::class.java)
                        intent.putExtra("VEDE", Constants.MARQUES)
                        startActivity(intent)
                    }
                    //CATEGORIAS
                    5 -> {
                        intent = Intent(applicationContext, CategoriesActivity::class.java)
                        intent.putExtra("VEDE", Constants.CATEGORIES)
                        startActivity(intent)
                    }
                    //AJUSTES
                    6 -> {
                        //Snackbar.make(navigation_header_img,getString(R.string.SB_EnDesarrollo) ,Snackbar.LENGTH_LONG).show()
                        intent = Intent(applicationContext, AjustesActivity::class.java)
                        startActivity(intent)

                    }
                    //ACERCA DE
                    7 -> {
                        Snackbar.make(navigation_header_img,getString(R.string.SB_EnDesarrollo) ,Snackbar.LENGTH_LONG).show()
                        /*intent = Intent(this@LlistaProductes, CategoriesActivity::class.java)
                         intent.putExtra("VEDE", Constants.CATEGORIES)
                         startActivity(intent)*/
                    }
                }


                updateAdapter(position)

                Handler().postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))

        updateAdapter(0)

        tabLayout = findViewById(R.id.tab)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position

                when (tab.position) {
                    0 -> tabSeleccionada = Constants.TAB_PRIMERA
                    1 -> tabSeleccionada = Constants.TAB_SEGONA
                    2 -> tabSeleccionada = Constants.TAB_TERCERA
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        viewPager = findViewById(R.id.viewPager)

        setUpViewPager()
        tabLayout.setupWithViewPager(viewPager)

        fabadd.setOnClickListener {
            var intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("TAB", tabSeleccionada)
            startActivity(intent)
        }
    }

    private fun updateAdapter(highlightItemPos: Int) {
        adapterNavigation = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapterNavigation
        adapterNavigation.notifyDataSetChanged()
    }

  /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_llista_productes, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.imNewScan -> {
                intent = Intent(this, ScanActivity::class.java)
                intent.putExtra("TAB", tabSeleccionada)
                startActivity(intent)

                return true
            }
            R.id.imUbicacions -> {
                intent = Intent(this, UbicacionsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.imCategories -> {
                intent = Intent(this, CategoriesActivity::class.java)
                intent.putExtra("VEDE", Constants.CATEGORIES)
                startActivity(intent)
                return true
            }

            R.id.imMarques -> {
                intent = Intent(this, CategoriesActivity::class.java)
                intent.putExtra("VEDE", Constants.MARQUES)
                startActivity(intent)
                return true
            }
            else ->{
                return super.onOptionsItemSelected(item)
            }
        }
    }*/
    fun setUpViewPager(){

        adapter = ViewPageAdapter(supportFragmentManager)

        adapter.addFragment(TabsFragment(this, Constants.TAB_PRIMERA), Constants.TAB_PRIMERA)
        adapter.addFragment(TabsFragment(this, Constants.TAB_SEGONA), Constants.TAB_SEGONA)
        adapter.addFragment(TabsFragment(this, Constants.TAB_TERCERA), Constants.TAB_TERCERA)

        viewPager.adapter = adapter

    }


}