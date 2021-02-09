package com.ymest.rebost.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.ymest.rebost.MainActivity
import com.ymest.rebost.R

/*
class MyService : Service() {
    val TAG = "FirebaseMessaging"
companion object {

    const val CHANNEL_ID = "ForegroundService Kotlin"
    */
/*val TAG2 = "FirebaseMessaging"
    fun startService(context: Context, message: String) {
        Log.d(TAG2, "Servicio Iniciado")
        val startIntent = Intent(context, MyService::class.java)
        startIntent.putExtra("inputExtra", message)
        ContextCompat.startForegroundService(context, startIntent)
    }

    fun stopService(context: Context) {
        Log.d(TAG2, "Servicio Parado")
        val stopIntent = Intent(context, MyService::class.java)
        context.stopService(stopIntent)
    }*//*

    }

    override fun onCreate() {
        Log.d(TAG, "Servicio Creado")
        super.onCreate()
    }

      override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
            //do heavy work on a background thread
          Log.d(TAG, "OnStartCommand")
             val input: String? = intent?.getStringExtra("inputExtra")
             createNotificationChannel()
             val notificationIntent = Intent(this, MainActivity::class.java)
             val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0, notificationIntent, 0)
             val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                 .setContentTitle(getText(R.string.service_title))
                 .setContentText(input)
                 .setSmallIcon(R.drawable.ic_shopping_basket_24px_black)
                 .setContentIntent(pendingIntent)
                 .build()
             startForeground(1, notification)
             return START_NOT_STICKY
     }

    override fun onDestroy() {
        Log.d(TAG, "Servicio Parado")
        super.onDestroy()
    }

  override fun onBind(intent: Intent): IBinder? {
      return null
 }


     private fun createNotificationChannel() {
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
             val manager: NotificationManager? = getSystemService(NotificationManager::class.java)
             manager!!.createNotificationChannel(serviceChannel)
         }
     }

 }*/
