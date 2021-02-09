package com.ymest.rebost.services


import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/*

class MyFirebaseMsg : FirebaseMessagingService() {
    val TAG = "FireTAG"
    override fun onMessageReceived(p0: RemoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${p0.from}")

        // Check if message contains a data payload.
        if (p0.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${p0.data}")

            if (*/
/* Check if data needs to be processed by long running job *//*
 true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob()
                Log.d(TAG, "Message data payload2: ${p0.data}")
            } else {
                // Handle message within 10 seconds
                //handleNow()
            }
        }

        // Check if message contains a notification payload.
        p0.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
}*/
