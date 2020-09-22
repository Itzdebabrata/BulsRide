package com.example.bulsride.FCM

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.bulsride.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class firebasemassage:FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("djsss",message?.from)
        Log.d("djss",message?.notification?.body)
        //Log.d("djss","ashdgadg")
        var ss=message?.notification?.body
        if (ss != null) {
            setupNotification(ss)
        }
    }
    private fun setupNotification(body:String){
    val changeid="dark riders"
        val ringtone=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val Notificationbuilder=NotificationCompat.Builder(this,changeid)
                .setSmallIcon(R.drawable.user1).setContentTitle("the dark drider").setContentText(body)
                .setSound(ringtone)
        val notificationmanager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            val channel=NotificationChannel(changeid,"dark rider",IMPORTANCE_HIGH)
        }
        notificationmanager.notify(0,Notificationbuilder.build())
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}