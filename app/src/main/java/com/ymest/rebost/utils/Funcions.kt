package com.ymest.rebost.utils

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class Funcions {
    companion object{
        fun DataToMillis(date: String):Long{
            Log.d("MSQUEPASSA", date)
            val myDate: String = date //"2017/12/20 18:10:45";
            val format: String = "dd/MM/yyyy"
            val sdf = SimpleDateFormat(format)
            val date: Date = sdf.parse(myDate)
            Log.d("MSQUEPASSA", date.time.toString())

            return date.time
        }

        fun MillisToDate(milliSeconds: Long): String {
            var millis = abs(milliSeconds)
            Log.d("MSQUEPASSA", "MS que arriben: " + millis.toString())
            val format = "dd/MM/yyyy"
            val formatter = SimpleDateFormat(format)
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            Log.d("MSQUEPASSA", "Formatter: " + formatter.format(calendar.time))
            return formatter.format(calendar.time)
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun obtenirDataActualMillis(): Long{
            var dataActual = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            var dataActualMillis = Funcions.DataToMillis(dataActual)
            return dataActualMillis
        }
        @RequiresApi(Build.VERSION_CODES.O)
        fun ComparaData(milliSeconds: Long): Long{
            var dataActualMillis = obtenirDataActualMillis()
            var tempsPerCaducar = milliSeconds - dataActualMillis
            /*if(milliSeconds - dataActualMillis > Constants.TIEMPO_AVISO_CADUCIDAD_HORAS*3600000){
                Log.d("CADU", "OK")
            } else if(milliSeconds - dataActualMillis > 0){
                Log.d("CADU", "A Punt de caducar")
            } else{
                Log.d("CADU", "Caducat")
            }
            Log.d("CADU", (milliSeconds - dataActualMillis).toString())*/
            return tempsPerCaducar
        }

        // extension function to convert dp to equivalent pixels
        fun Int.dpToPixels(context: Context):Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,this.toFloat(),context.resources.displayMetrics
        )

        fun View.margin(left: Float? = null, top: Float? = null, right: Float? = null, bottom: Float? = null) {
            layoutParams<ViewGroup.MarginLayoutParams> {
                left?.run { leftMargin = dpToPx(this) }
                top?.run { topMargin = dpToPx(this) }
                right?.run { rightMargin = dpToPx(this) }
                bottom?.run { bottomMargin = dpToPx(this) }
            }
        }

        inline fun <reified T : ViewGroup.LayoutParams> View.layoutParams(block: T.() -> Unit) {
            if (layoutParams is T) block(layoutParams as T)
        }

        fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)
        fun Context.dpToPx(dp: Float): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
    }
}