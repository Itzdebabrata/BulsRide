package com.example.bulsride.userAcivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.bulsride.R

class Notesdetail : AppCompatActivity() {
    lateinit var titledetail:TextView
    lateinit var descdetail:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notesdetail)
        bindview()
        var intent=getIntent()
        titledetail.setText(intent.getStringExtra("detailtitle"))
        descdetail.setText(intent.getStringExtra("detaildesc"))
    }
    private fun bindview(){
        titledetail=findViewById(R.id.titledetail)
        descdetail=findViewById(R.id.descdetail)
    }
}