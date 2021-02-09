package com.ymest.rebost.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ymest.rebost.MainActivity
import com.ymest.rebost.R
import com.ymest.rebost.detallproductesbuscats.DetallProdBuscatsActivity
import com.ymest.rebost.json.ProducteALlista
import com.ymest.rebost.productesdecategoria.ProductesDeCategoriaActivity
import com.ymest.rebost.sqlite.TaulaProductesALlistesCrud
import com.ymest.rebost.utils.Constants
import com.ymest.rebost.utils.Funcions


class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "FirebaseMessaging"
    lateinit var crudLlistaProductes: TaulaProductesALlistesCrud
    lateinit var productesCaducats: ArrayList<ProducteALlista>
    val CHANNEL_ID: String = "1001"
    var notificationId: Int = 100

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        //sendRegistrationToServer(token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "HA ENTRAT A ONMESSAGERECEIVED")
        Log.d(TAG, "REMOTE MESSAGE: " + remoteMessage.notification!!.body.toString())
        notificationId ++

        //mostranotificacio()
        crudLlistaProductes = TaulaProductesALlistesCrud(applicationContext)
        productesCaducats = crudLlistaProductes.getCaducats(Funcions.obtenirDataActualMillis())
        if (productesCaducats.size > 0) {
            notificationId ++
            Log.d(TAG, "NOTIFICATION ID: : " + notificationId.toString())
            mostranotificacio()
        }
    }



    private fun mostranotificacio() {
        Log.d(TAG, "HA ENTRAT A MOSTRA NOTIFICACIÓ")
        createNotificationChannel()
        val intent = Intent(this, ProductesDeCategoriaActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra("VEDE", Constants.MISLISTAS)
        intent.putExtra("IDLLISTA", "3")
        //startActivity(intent)
        /*val intent = Intent(this, DetallProdBuscatsActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }
        intent.putExtra("ID", "3560070820306")*/

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_shopping_basket_24px_black)
            .setContentTitle("Producto(s) caducado(s)")
            .setContentText("Mira los productos que han caducado!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        Log.d(TAG, "HA ENTRAT A MOSTRA CREATENOTIFICATION CHANNEL")
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "CHANNEL_PROVA_NAME"//getString(R.string.channel_name)
            Log.d(TAG, "CHANNEL NAME: " + name)
            val descriptionText = "CHANNEL_PROVA_DESCRIPTION"//getString(R.string.channel_description)
            Log.d(TAG, "CHANNEL DESC: " + descriptionText)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}