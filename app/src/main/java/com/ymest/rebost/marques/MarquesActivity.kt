package com.ymest.rebost.marques

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ymest.rebost.R
import com.ymest.rebost.categories.AdaptadorCategories
import com.ymest.rebost.events.CellClickListener
import com.ymest.rebost.events.CellLongClickListener
import com.ymest.rebost.json.categories.Categories
import com.ymest.rebost.json.categories.Tag
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Network
import kotlinx.android.synthetic.main.activity_categories.*
import kotlinx.android.synthetic.main.activity_marques.*


class MarquesActivity : AppCompatActivity(), CellClickListener, CellLongClickListener {

    val network = Network(this)
    lateinit var listacategories : ArrayList<Tag>
    lateinit var categoriaAPI : Categories

    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: AdaptadorCategories
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marques)

        setSupportActionBar(toolbarMarques)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        listacategories = ArrayList()
        carregaCategories()
    }

    fun carregaCategories() {
        if (network.hayRed()) {
            val queue = Volley.newRequestQueue(this)
            val url = Constants.URL_API_MARQUES

            //TODO: Falta fer que listacategories sigui una llista
            val request = StringRequest(
                Request.Method.GET, url, { response ->
                    var gson = Gson()
                    categoriaAPI = gson.fromJson(response, Categories::class.java)

                    for (etiqueta in categoriaAPI.tags) {
                        if (etiqueta.name.length > 3 && etiqueta.name.substring(0, 3) != "en:") {
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
                {
                    Toast.makeText(this, "No hi ha resposta del servidor", Toast.LENGTH_SHORT)
                        .show()
                })
            queue.add(request)
        } else {
            Toast.makeText(this, "Error de red", Toast.LENGTH_SHORT).show()
        }
    }

    private fun carregaRecyclerViewCategories(lista: ArrayList<Tag>) {
        rvMarques.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        rvMarques.layoutManager = layoutManager

        //viewAdapter = AdaptadorCategories(this, lista, this, this)
        rvMarques.adapter = viewAdapter
    }

    override fun onCellClickListener(id: String?) {
        TODO("Not yet implemented")
    }

    override fun onCellLongClickListener(id: String?): Boolean {
        TODO("Not yet implemented")
    }


}