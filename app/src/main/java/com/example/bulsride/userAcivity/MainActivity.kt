package com.example.bulsride.userAcivity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.NotificationCompat
import com.example.bulsride.R
import com.example.bulsride.Storedata.Shared
import com.example.bulsride.ViewPager.OnBoardingActivity
import com.example.bulsride.variables.sharedpref
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import java.lang.Boolean.getBoolean

class MainActivity : AppCompatActivity() {
    lateinit var sharedimg:ImageView
    lateinit var buttom:Animation
    lateinit var title:TextView
    lateinit var description:TextView
    val splashout=4500
    //lateinit var SharedPreferences:SharedPreferences
    //lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        title=findViewById(R.id.splashtitle)
        description=findViewById(R.id.splashdescrption)
        sharedimg=findViewById(R.id.splashimg)

        buttom= AnimationUtils.loadAnimation(this, R.anim.buttom)
        title.animation = buttom
        description.animation=buttom
        Handler().postDelayed({
            setupshared()
            checklogin()
            Checklogins()
            getfcmtoken()
           // setupNotification("dark rider")
        },splashout.toLong())

    }
    private fun setupNotification(body:String){
        val changeid="dark riders"
        val ringtone= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationmanager=getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            val channel= NotificationChannel(changeid,"dark rider", NotificationManager.IMPORTANCE_HIGH)
        }
        val Notificationbuilder= NotificationCompat.Builder(this,changeid)
                .setSmallIcon(R.drawable.user1).setContentTitle("the dark driderssssssssssss").setContentText(body)
                .setSound(ringtone)
        notificationmanager.notify(0,Notificationbuilder.build())
    }

    private fun getfcmtoken(){
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("getfcm", "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    //val msg = getString(R.string.msg_token_fmt, token)
                    Log.d("getfcm", token)
                    //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })

    }
    private fun checklogin(){
       val intent=Intent(this@MainActivity, Login::class.java)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            val img = androidx.core.util.Pair.create<View, String>(sharedimg, "splashimage")
            val text= androidx.core.util.Pair.create<View, String>(title, "splashname")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, img,text)
            startActivity(intent, options.toBundle())
        }
    }
    private fun Checklogins(){
        val key=Shared.read(sharedpref.key)
        if(key!!){
            val intent=Intent(this@MainActivity, User::class.java)
            intent.putExtra(sharedpref.fullname,"username")
            startActivity(intent)
        }
        else{
            val onBoard:Boolean?=Shared.read1(sharedpref.Onboardingsuccess)
            if(onBoard!!){
                val intent = Intent(this@MainActivity, Login::class.java)
                startActivity(intent)
                finish()
            }else {
                val intent=Intent(this@MainActivity,OnBoardingActivity::class.java)
                startActivity(intent)
            }
        }
        finish()
    }
    private fun setupshared() {
        Shared.get(this)
    }
}