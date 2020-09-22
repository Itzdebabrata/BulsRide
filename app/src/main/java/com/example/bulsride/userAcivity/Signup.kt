package com.example.bulsride.userAcivity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bulsride.R
import com.example.bulsride.variables.Stroedata
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {

    lateinit var signup:Button
    lateinit var signin:TextView
    lateinit var fullname:EditText
    lateinit var email:EditText
    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var mobilenum1:EditText
    lateinit var fullname1:String
    lateinit var username1:String
    lateinit var email1:String
    lateinit var password1:String

    lateinit var rootnode:FirebaseDatabase
    lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        bindviews()
        getdata()
        val dj=object:View.OnClickListener{
            override fun onClick(v: View?) {

                if(!validename() or  !valideemail() or  !validepassword() or  !valideusername()) {
                    return
                }

                var fullname12=fullname.text.toString()
                var username12=username.text.toString()
                var email12=email.text.toString()
               var password12=password.text.toString()
                var mobile=mobilenum1.text.toString()

                rootnode= FirebaseDatabase.getInstance()
                reference=rootnode.getReference("data")
                var ss= Stroedata(fullname = fullname12, email = email12, username = username12, password = password12, mobilenumber = mobile)
                reference.child(username12).setValue(ss)
                    val intent = Intent(this@Signup, Login::class.java)
                    //intent.putExtra("mob",mobile)
                    startActivity(intent)
            }
        }
        signup.setOnClickListener(dj)

        val ss=object:View.OnClickListener{
            override fun onClick(v: View?) {
                val intent=Intent(this@Signup, Login::class.java)
                startActivity(intent)
            }
        }
        signin.setOnClickListener(ss)
    }

    private fun bindviews(){
        signup=findViewById(R.id.signup)
        signin=findViewById(R.id.signin)
        fullname=findViewById(R.id.full_name)
        email=findViewById(R.id.email)
        username=findViewById(R.id.username)
        password=findViewById(R.id.password)
        mobilenum1=findViewById(R.id.moblienum)
    }
    private fun getdata(){
        fullname1=fullname.text.toString()
        username1=username.text.toString()
        email1=email.text.toString()
        password1=password.text.toString()
    }
    private fun validename():Boolean{
        if(fullname.text.toString().isEmpty()){
            fullname.setError("enter the fullname")
            return false
        }else{
            return true
        }
    }
    private fun valideemail():Boolean {
        val dj=email.text.toString()
        if (email.text.toString().isEmpty()) {
            email.setError("enter the fullname")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(dj).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        }
        else {
            return true
        }

    }
    private fun validepassword():Boolean {
        if (password.text.toString().isEmpty()) {
            password.setError("enter the fullname")
            return false
        } else {
            return true
        }
    }
    private fun valideusername():Boolean {
        if (username.text.toString().isEmpty()) {
            username.setError("enter the username")
            return false
        } else if (username.length() > 15) {
            username.setError("Username too long");
            return false;
        }
        else {
            return true
        }
    }

}

