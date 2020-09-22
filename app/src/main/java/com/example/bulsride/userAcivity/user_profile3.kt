package com.example.bulsride.userAcivity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.bulsride.R
import com.example.bulsride.variables.Stroedata
import com.example.bulsride.variables.sharedpref
import com.google.firebase.database.FirebaseDatabase

class user_profile3 : AppCompatActivity() {

    lateinit var full_name2: EditText
    lateinit var emailid2: EditText
    lateinit var mobnumber: EditText
    lateinit var password2: EditText
    lateinit var username: EditText
    lateinit var usernm: TextView
    lateinit var userdescr: TextView
    lateinit var signup1: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile3)
        supportActionBar?.hide()
        findid()
        setupshared()
        var intent = getIntent()
        var dj = intent.getStringExtra("validfull")
        full_name2.setText(dj)
        var dj1 = intent.getStringExtra("validuser")
        username.setText(dj1)
        var dj2 = intent.getStringExtra("c")
        emailid2.setText(dj2)
        var dj3 = intent.getStringExtra("validpass")
        password2.setText(dj3)
        var dj4 = intent.getStringExtra("validfull")
        usernm.setText(dj4)

        var ss = object : View.OnClickListener {
            override fun onClick(v: View?) {

                var full_name = full_name2.text.toString()
                var emailid = emailid2.text.toString()
                var mobnumber = mobnumber.text.toString()
                var passwords = password2.text.toString()
                var usernames = username.text.toString()

                var reffer = FirebaseDatabase.getInstance().getReference("data")
                var ss = Stroedata(fullname = full_name, email = emailid, username = usernames, password = passwords, mobilenumber = mobnumber)
                reffer.child(usernames).setValue(ss)

                var intent = Intent(this@user_profile3, User::class.java)
                editor=sharedPreferences.edit()
                editor.putString(sharedpref.fullname,dj1)
                editor.apply()
                startActivity(intent)
            }
        }
        signup1.setOnClickListener(ss)
    }

    private fun findid() {
        full_name2 = findViewById(R.id.full_name2)
        emailid2 = findViewById(R.id.emailid2)
        mobnumber = findViewById(R.id.mobnumber)
        password2 = findViewById(R.id.password2)
        username = findViewById(R.id.user3)
        usernm = findViewById(R.id.usernm)
        signup1 = findViewById(R.id.signup1)
    }

    private fun setupshared() {
        sharedPreferences = getSharedPreferences(sharedpref.shared, MODE_PRIVATE)
    }
}
