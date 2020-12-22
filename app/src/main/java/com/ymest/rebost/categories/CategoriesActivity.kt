package com.ymest.rebost.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ymest.rebost.R
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.categories.Categories
import com.ymest.rebost.json.categories.Tag
import com.ymest.rebost.productesdecategoria.ProductesDeCategoriaActivity
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Network
import kotlinx.android.synthetic.main.activity_categories.*
import javax.xml.transform.ErrorListener


class CategoriesActivity : AppCompatActivity(), CellClickListener, CellLongClickListener {

    val network = Network(this)
    lateinit var listacategories : ArrayList<Tag>
    lateinit var categoriaAPI : Categories

    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: AdaptadorCategories
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var vede: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)


        setSupportActionBar(toolbarCategories)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        vede = intent.getStringExtra("VEDE").toString()

        when(vede){
            Constants.CATEGORIES -> toolbarCategories.title = Constants.CATEGORIES
            Constants.MARQUES    -> toolbarCategories.title = Constants.MARQUESYSUPER
        }

        listacategories = ArrayList()
        carregaCategories()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contextual_categories, menu)

        var btnbusca = menu?.findItem(R.id.imSearchCategories)
        var buscaView = btnbusca?.actionView as android.widget.SearchView

        when(vede){
            Constants.CATEGORIES -> buscaView.queryHint = "Escribe la Categoría..."
            Constants.MARQUES    -> buscaView.queryHint = "Escribe la Marca"
        }

        buscaView.setOnQueryTextListener(object: android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                var elementosEncontrados: ArrayList<Tag> = ArrayList()
                if(newText != null) {
                    for (item in listacategories) {
                        if (item.name.contains(newText.toString(), ignoreCase = true)) {
                            elementosEncontrados.add(
                                Tag(
                                    item.id,
                                    item.known,
                                    item.name,
                                    item.products,
                                    item.url
                                )
                            )
                        }
                    }
                }
                carregaRecyclerViewCategories(elementosEncontrados)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

    private fun carregaRecyclerViewCategories(lista: ArrayList<Tag>) {
        rvCategories.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rvCategories.layoutManager = layoutManager

        viewAdapter = AdaptadorCategories(this, lista, "CATEGORIES",this, this)
        rvCategories.adapter = viewAdapter
    }

    fun carregaCategories(){
        if (network.hayRed()) {
            var url: String = ""
            val queue = Volley.newRequestQueue(this)
            when(vede){
                Constants.CATEGORIES -> url = Constants.URL_API_CATEGORIES
                Constants.MARQUES    -> url = Constants.URL_API_MARQUES
            }
            val request = StringRequest(Request.Method.GET, url, { response ->
                var gson = Gson()
                categoriaAPI = gson.fromJson(response, Categories::class.java)

                for(etiqueta in categoriaAPI.tags){
                    if(etiqueta.name.length > 3 && etiqueta.name.substring(0,3) != "en:") {
                        listacategories.add(
                            Tag(
                                etiqueta.id,
                                etiqueta.known,
                                etiqueta.name,
                                etiqueta.products,
                                etiqueta.url
                            )
                        )
                    }
                }
                carregaRecyclerViewCategories(listacategories)

             },
                { responseError ->
                    Toast.makeText(this, "No hi ha resposta del servidor", Toast.LENGTH_SHORT).show()
                    Log.d("SINRESPUESTA", responseError.toString() )
                })
               queue.add(request)
        } else {
            Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show()
        }




    }

    override fun onCellClickListener(name: String?) {
        Toast.makeText(this, "CLICK A: " + name, Toast.LENGTH_SHORT).show()
        intent = Intent(this, ProductesDeCategoriaActivity::class.java)
        intent.putExtra("VEDE", vede)
        intent.putExtra("NOM", name)
        startActivity(intent)
    }

    override fun onCellLongClickListener(id: String?): Boolean {
        //TODO("Not yet implemented")
        return true
    }
}
