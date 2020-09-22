package com.example.bulsride.userAcivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.bulsride.*
import com.example.bulsride.Adaptor.Notesadaptor
import com.example.bulsride.Itemclick.Itemclick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.ArrayList
import com.example.bulsride.Database.Notes
import com.example.bulsride.R
import com.example.bulsride.Storedata.Shared
import com.example.bulsride.Workmanager.Mywork
import com.example.bulsride.variables.LoginConstant
import com.example.bulsride.variables.sharedpref
import java.util.concurrent.TimeUnit

class User : AppCompatActivity() {

    lateinit var userimg:ImageView
    lateinit var searchtext:EditText
    lateinit var searchimg:ImageView
    lateinit var addbut:FloatingActionButton
    lateinit var recycler:RecyclerView
    var notes = ArrayList<Notes>()
    lateinit var users:String
    lateinit var logout:Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val passcode=100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        //supportActionBar?.hide()
        bindviews()
        setupshared()
        getdatafromdb()
        val intent=intent
        var ss=intent.getStringExtra("username")

        if(!TextUtils.isEmpty(ss)){
            users=ss

        }else{
            getuser()
        }
        setuprecyclerview()
        clickListeners()
        setupworkmanager()
    }
    private fun setupworkmanager(){
        val contraint=Constraints.Builder().build()
        val request=OneTimeWorkRequest.Builder(Mywork::class.java)
                .setConstraints(contraint)
                .build()
        WorkManager.getInstance().enqueue(request)
    }

    private fun getuser(){
        var fullnm=Shared.readString(sharedpref.fullname)
        if (fullnm != null) {
            users=fullnm
        }
    }

    private fun clickListeners(){
        val dj=object:View.OnClickListener{
            override fun onClick(v: View?) {

                var reffer=FirebaseDatabase.getInstance().getReference("data")
                var checkuser=reffer.orderByChild("username").equalTo(users)
                checkuser.addListenerForSingleValueEvent(object:ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot){
                        var validusername=p0.child(users).child("username").getValue().toString()
                        var validfullname=p0.child(users).child("fullname").getValue().toString()
                        var validpasswords=p0.child(users).child("password").getValue().toString()
                        var validemail=p0.child(users).child("email").getValue().toString()

                        val intent = Intent(this@User, user_profile3::class.java)
                        intent.putExtra("validuser",validusername)
                        intent.putExtra("validfull",validfullname)
                        intent.putExtra("validpass",validpasswords)
                        intent.putExtra("c",validemail)
                        startActivity(intent)
                    }
                })
            }
        }
        userimg.setOnClickListener(dj)
        addbut.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                //setupdialog()
                val intent=Intent(this@User,dialogbox2::class.java)
                startActivityForResult(intent,passcode)
            }
        })
        logout.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                applogout()
                deletdata()
                val intent = Intent(this@User, Login::class.java)
                startActivity(intent)
            }
        })
    }

    private fun applogout(){
        Shared.write(sharedpref.key,false)
    }
    private fun deletdata() {
        val ridersapp=applicationContext as Ridersapp
        val ridersdao=ridersapp.getDarkdb().darkDao()
        ridersdao.delete()
    }
    private fun setupshared(){
        Shared.get(this)
    }

    private fun setupdialog(){
        val view= LayoutInflater.from(this@User).inflate(R.layout.activity_dialogbbox,null)
        val title=view.findViewById<TextView>(R.id.titles)
        val description=view.findViewById<TextView>(R.id.description)
        val but=view.findViewById<Button>(R.id.submit)
        val back=view.findViewById<ImageView>(R.id.back)
        val dialog= AlertDialog.Builder(this).setView(view).setCancelable(false).create()
        dialog.show()
        but.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                    val titleshow=title.text.toString()
                    val descshow=description.text.toString()
                if(!TextUtils.isEmpty(title.text.toString())&&!TextUtils.isEmpty(description.text.toString())){
                    val data=Notes(title = titleshow,description = descshow,istaskcomplet = false,imagepath = "")
                    notes.add(data)
                    addDatatoDb(data)
                    dialog.hide()
                }else{
                    Toast.makeText(this@User,"Enter the title", Toast.LENGTH_SHORT).show()
                }
            }
        })

        back.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                dialog.hide()
            }
        })

    }
    private fun addDatatoDb(data:Notes){
    val ridersapp=applicationContext as Ridersapp
        val ridersdao=ridersapp.getDarkdb().darkDao()
        ridersdao.inset(data)
    }
    private fun getdatafromdb(){
        val ridersapp=applicationContext as Ridersapp
        val ridersdao=ridersapp.getDarkdb().darkDao()
        notes.addAll(ridersdao.getAll())
    }
    private fun setuprecyclerview(){
        val itemclick:Itemclick=object :Itemclick{
            override fun onclick(note: Notes) {
                var intent=Intent(this@User, Notesdetail::class.java)
                intent.putExtra("detailtitle",note.title)
                intent.putExtra("detaildesc",note.description)
                startActivity(intent)
            }


            override fun onupdate(note: Notes) {
                val ridersapp=applicationContext as Ridersapp
                val ridersdao=ridersapp.getDarkdb().darkDao()
                ridersdao.update(note)
            }
        }

        val notesadaptor= Notesadaptor(notes,itemclick)
        val linear=LinearLayoutManager(this@User)
        linear.setOrientation(RecyclerView.VERTICAL)
        recycler.setLayoutManager(linear)
        recycler.setAdapter(notesadaptor)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==passcode&&resultCode==Activity.RESULT_OK){
            if (data != null) {
                val title = data?.getStringExtra(LoginConstant.title1)
                val description = data?.getStringExtra(LoginConstant.description1)
                val imagepath = data?.getStringExtra(LoginConstant.imagepath)
                val note = Notes(title = title!!, description = description!!, imagepath = imagepath!!)
                addDatatoDb(note)
                notes.add(note)
                recycler.adapter?.notifyItemChanged(notes.size - 1)
            }
        }
    }
    private fun bindviews() {
        userimg = findViewById(R.id.userimg)
        searchtext = findViewById(R.id.searchtext)
        searchimg = findViewById(R.id.searchimg)
        addbut = findViewById(R.id.addbut)
        recycler = findViewById(R.id.recycler)
        logout = findViewById(R.id.logout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate=menuInflater
       inflate.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item!!.itemId == R.id.blog){
          //  Log.d("sssss","ddddddddd")
            val intent=Intent(this@User,MenuDetails::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
