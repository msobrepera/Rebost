package com.ymest.rebost.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpResponse

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class Network(var activity:AppCompatActivity) {
    init{

    }
    fun hayRed():Boolean{
        var result = false
        val connectivityManager =
            activity.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }

   /* fun httpRequest(context: Context, url:String, httpResponse: HttpResponse){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{

                    response ->

                httpResponse.httpResponseSuccess(response)

            }, Response.ErrorListener {
                    error ->

                Log.d("HTTP_REQUEST", error.message.toString())

                //Mensaje.mensajeError(context, Errores.HTTP_ERROR)
            })
            queue.add(solicitud)
        }else{
            //Mensaje.mensajeError(context, Errores.NO_HAY_RED)
        }
    }

    fun httpPOSTRequest(context:Context, url:String, httpResponse: HttpResponse){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)
            val solicitud = StringRequest(Request.Method.POST, url, Response.Listener<String>{
              response ->
                httpResponse.httpResponseSuccess(response)
            }, Response.ErrorListener {
                error ->
                Log.d("HTTP_REQUEST", error.message.toString())
                httpResponse.httpErrorResponse(error.message.toString())
              //  Mensaje.mensajeError(context, ERRORES.HTTP_ERROR)
            })
            queue.add(solicitud)
        }else{
            //Mensaje.mensajeError(context, Errores.NO_HAY_RED)
        }
    }*/

}