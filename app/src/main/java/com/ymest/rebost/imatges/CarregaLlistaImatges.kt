package com.ymest.rebost.imatges

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ymest.rebost.R
import com.ymest.rebost.json.Nutriments
import com.ymest.rebost.json.Producto

class CarregaLlistaImatges(ctx: Context, producto: Producto, var llistaImatges: ArrayList<String>) {
    var prod: Producto
    var ctx: Context

    init{
        this.prod = producto
        this.ctx = ctx
    }

    fun comprovaImatges(){
        comprovaImatgePrincipal()
        comprovaImatgeIngredients()
        comprovaImatgeNutricio()
        //comprovaImatgeFront()
    }

    private fun comprovaImatgeFront() {
        if(!prod.product?.imageFrontUrl?.toString().isNullOrEmpty()){
            llistaImatges.add(prod.product?.imageFrontUrl.toString())
        }
    }

    private fun comprovaImatgeNutricio() {
        if(!prod.product?.imageNutritionUrl?.toString().isNullOrEmpty()){
            llistaImatges.add(prod.product?.imageNutritionUrl.toString())
        }
    }

    private fun comprovaImatgeIngredients() {
        if(!prod.product?.imageIngredientsUrl?.toString().isNullOrEmpty()){
            llistaImatges.add(prod.product?.imageIngredientsUrl.toString())
        }
    }

    private fun comprovaImatgePrincipal() {
        if(!prod.product?.imageUrl.toString()?.isNullOrEmpty()){
            llistaImatges.add(prod.product?.imageUrl.toString())
        }
    }
}