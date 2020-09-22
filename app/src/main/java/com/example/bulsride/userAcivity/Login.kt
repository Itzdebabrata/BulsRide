package com.example.bulsride.userAcivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.core.app.ActivityOptionsCompat
import com.example.bulsride.R
import com.example.bulsride.Storedata.Shared
import com.example.bulsride.variables.sharedpref
import com.google.firebase.database.*

class Login : AppCompatActivity() {
    lateinit var register:TextView
    lateinit var image:ImageView
    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var login:Button
    lateinit var logintitle:TextView
    lateinit var logindesc:TextView
    lateinit var SharedPreferences:SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        bindviews()
        setupsharedpref()
        val dj=object:View.OnClickListener{
            override fun onClick(v: View?) {
                val intent=Intent(this@Login, Signup::class.java)
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                    val img = androidx.core.util.Pair.create<View, String>(image, "splashimage")
                    val text= androidx.core.util.Pair.create<View, String>(logintitle, "splashname")
                    val username= androidx.core.util.Pair.create<View, String>(username, "transitionuser")
                    val password= androidx.core.util.Pair.create<View, String>(password, "transitionpassword")
                    val desc= androidx.core.util.Pair.create<View, String>(logindesc, "transitiondesc")
                    val button= androidx.core.util.Pair.create<View, String>(login, "transitionbutton")
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@Login,img,text,username,password,desc,button)
                    startActivity(intent, options.toBundle())
                }
            }
        }
        register.setOnClickListener(dj)
        val ss=object:View.OnClickListener{
            override fun onClick(v: View?) {

                var user=username.text.toString()
                var pass=password.text.toString()
                if(user.isNotEmpty() && pass.isNotEmpty()) {
                   isuser()

                }else{
                    Toast.makeText(this@Login,"Enter the Userid and Password",LENGTH_SHORT).show()
                }
            }
        }
        login.setOnClickListener(ss)
    }
    private fun isuser(){
        var user=username.text.toString().trim()
        var password3=password.text.toString().trim()
        var reference=FirebaseDatabase.getInstance().getReference("data")
        var checkuser=reference.orderByChild("username").equalTo(user)
        checkuser.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    username.setError(null)
                    var validpassword=p0.child(user).child("password").getValue().toString()
                    if(password3.equals(validpassword)){
                        password.setError(null)
                        var validusername=p0.child(user).child("username").getValue().toString()
                        var validfullname=p0.child(user).child("fullname").getValue().toString()
                        var validpasswords=p0.child(user).child("password").getValue().toString()
                        var validemail=p0.child(user).child("email").getValue().toString()

                        val intent = Intent(this@Login, user_profile3::class.java)
                        intent.putExtra("validuser",validusername)
                        intent.putExtra("validfull",validfullname)
                        intent.putExtra("validpass",validpasswords)
                        intent.putExtra("c",validemail)
                        startActivity(intent)
                        savelogin()
                        saveusername(user)
                    }
                    else{
                        password.setError("Wrong Password")
                        password.requestFocus()
                    }
                }
                else{
                    username.setError("wrong user name")
                    username.requestFocus()
                }
            }
        })
    }
    private fun bindviews(){
        register=findViewById(R.id.textView2)
        image=findViewById(R.id.appCompatImageView)
        logintitle=findViewById(R.id.textView4)
        logindesc=findViewById(R.id.textView3)
        username=findViewById(R.id.signinusername)
        password=findViewById(R.id.signinpassword)
        login=findViewById(R.id.but)
    }
    private fun savelogin(){
        Shared.write(sharedpref.key,true)
    }
    private fun saveusername( user:String){
        Shared.writeString(sharedpref.fullname,user)
    }

    private fun setupsharedpref(){
        Shared.get(this)
    }
}

