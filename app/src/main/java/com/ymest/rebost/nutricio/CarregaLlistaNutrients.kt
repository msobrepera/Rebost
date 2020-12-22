package com.ymest.rebost.nutricio

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ymest.rebost.R
import com.ymest.rebost.json.Nutriments
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToLong
import kotlin.math.round

class CarregaLlistaNutrients(ctx: Context, nutri: Nutriments, var llistaNutrients: ArrayList<Nutricio>) {
    var nutri: Nutriments
    var ctx: Context

    init{
        this.nutri = nutri
        this.ctx = ctx
    }

    fun comprovaNutrients(){
        comprovaEnergia()
        comprovaGreixos()
        comprovaGreixosSaturats()
        comprovaCarboHidrats()
        comprovaSucres()
        comprovaProteines()
        comprovaSal()
        comprovaSodi()
    }

    private fun comprovaSodi() {
        if(!nutri.sodium100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.sodi), String.format("%.2f", nutri.sodium100g), nutri.sodiumUnit.toString()))
        }
    }

    private fun comprovaSal() {
        if(!nutri.salt100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.sal), String.format("%.2f", nutri.salt100g), nutri.saltUnit.toString()))
        }
    }

    private fun comprovaProteines() {
        if(!nutri.proteins100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.proteinas), String.format("%.2f", nutri.proteins100g), nutri.proteinsUnit.toString()))
        }
    }

    private fun comprovaSucres() {
        if(!nutri.sugars100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.azucar), String.format("%.2f", nutri.sugars100g), nutri.sugarsUnit.toString()))
        }
    }

    private fun comprovaCarboHidrats() {
        if(!nutri.carbohydrates100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.hidratos_carbono), String.format("%.2f", nutri.carbohydrates100g), nutri.carbohydratesUnit.toString()))
        }
    }

    private fun comprovaGreixosSaturats() {
        if(!nutri.saturatedFat100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.grasas_sat), String.format("%.2f", nutri.saturatedFat100g), nutri.saturatedFatUnit.toString()))
        }
    }

    private fun comprovaGreixos() {
        if(!nutri.fat100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.grasas), String.format("%.2f", nutri.fat100g), nutri.fatUnit.toString()))
        }
    }

    fun comprovaEnergia() {
        if(!nutri.energy100g?.toString().isNullOrEmpty()){
            llistaNutrients.add(Nutricio(ctx.getString(R.string.valor_energetico), nutri.energyKcal100g.toString(), nutri.energyKcalUnit.toString()))
        }
    }
}