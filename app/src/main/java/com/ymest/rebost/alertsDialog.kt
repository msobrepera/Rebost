package com.ymest.rebost

import android.content.Context
import android.content.DialogInterface
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.TypedArrayUtils.getString
import com.google.android.material.snackbar.Snackbar
import com.ymest.rebost.detallproductesbuscats.ui.main.PlaceholderFragment
import com.ymest.rebost.sqlite.TaulaLlistesCrud
import kotlinx.android.synthetic.main.alert_dialog_nova_llista.view.*
import kotlinx.android.synthetic.main.fragment_detall_prod_buscats.*

object alertsDialog {

    fun muestraAlertDialogListas(ctx: Context) {
        var llistesNom: Array<String>
        var crudLlistes: TaulaLlistesCrud

        llistesNom = arrayOf()
        crudLlistes = TaulaLlistesCrud(ctx)

        for(llista in crudLlistes.getAllLlista()){
            llistesNom += llista.nomLlista!!
        }
        llistesNom += ctx.getString(R.string.titulo_AfegirLlista)

        val builder = AlertDialog.Builder(ctx)
        //builder.setTitle(getString(R.string.titulo_Llistes))

        builder.setItems(llistesNom, DialogInterface.OnClickListener{ dialog, which->

            if (which == llistesNom.size - 1){
                Toast.makeText(ctx, "Click a Afegir: " + which, Toast.LENGTH_SHORT).show()
                //mostraAlertDialogNovaLlista()
            } else {
                Toast.makeText(ctx, "Click a Llista: " + which, Toast.LENGTH_SHORT).show()
               /* var idLlista = TaulaLlistesCrud(ctx).getIdLlista(llistesNom[which])
                if(TaulaLlistesCrud(ctx).gestionaCantidad(idLlista)){
                    mostraAlertDialogQuantitat(llistesNom[which])
                } else if(TaulaLlistesCrud(ctx).gestionaFechas(idLlista)){
                    mostraAlertDialogCalendar(llistesNom[which], 0)
                } else{
                    afegirProducteALlista(llistesNom[which], 0 , 0)
                }*/

            }
        })
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun mostraAlertDialogNovaLlista(ctx:Context) {
        var gestionadates:Int = 0
        var gestionaCantidad:Int = 0
        val builder = AlertDialog.Builder(ctx)
        val factory = LayoutInflater.from(ctx)
        val view: View = factory.inflate(R.layout.alert_dialog_nova_llista, null)

        builder.setView(view)
        builder.setTitle("Añadir Lista: ")
        builder.setPositiveButton("AÑADIR"){ dialog, which ->
            /*if(view.etNomNovaLlista.text.isNotEmpty()){
                if (view.cbDataCad.isChecked) gestionadates = 1 else gestionadates = 0
                if (view.cbGestionaCantidad.isChecked) gestionaCantidad = 1 else gestionaCantidad = 0
                PlaceholderFragment.
                addNovaLlista(view.etNomNovaLlista.text.toString(), gestionadates, gestionaCantidad)

                var idNovaLlista = TaulaLlistesCrud(ctx).getIdLlista(view.etNomNovaLlista.text.toString())
                if(TaulaLlistesCrud(ctx).gestionaCantidad(idNovaLlista)){
                    mostraAlertDialogQuantitat(view.etNomNovaLlista.text.toString())
                } else if(TaulaLlistesCrud(ctx).gestionaFechas(idNovaLlista)){
                    mostraAlertDialogCalendar(view.etNomNovaLlista.text.toString(), 0)
                } else{
                    afegirProducteALlista(view.etNomNovaLlista.text.toString(),0,0)
                    Snackbar.make(ivAddaListaDetallBuscats ,getString(R.string.SB_LlistaAfegida),
                        Snackbar.LENGTH_LONG).show()
                }


            }else{
                Snackbar.make(ivAddaListaDetallBuscats ,getString(R.string.ERR_TEXTO_LISTA_VACIO),
                    Snackbar.LENGTH_LONG).show()
            }*/

        }
        builder.setNegativeButton("CANCELAR"){ dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}