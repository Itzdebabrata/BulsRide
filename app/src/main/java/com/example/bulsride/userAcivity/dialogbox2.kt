package com.example.bulsride.userAcivity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.bulsride.BuildConfig
import com.example.bulsride.R
import com.example.bulsride.variables.LoginConstant
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class dialogbox2 : AppCompatActivity() {
    lateinit var dialogtitle:TextView
    lateinit var dialogdesc:TextView
    lateinit var dialogsubmit:TextView
    lateinit var dialogback:ImageView
    lateinit var dialogimg:ImageView
    val galarypass=100
    val camerapass=101
    val permissioncode=123
    var photouri: Uri?=null
    val FILE_NAME="file.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogbox2)
        bindviews()
        clicklisteners()
    }
    private fun clicklisteners(){
        dialogback.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                var intent=Intent(this@dialogbox2,User::class.java)
                startActivity(intent)
            }
        })
        dialogimg.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(permissionchecked()){
                setupdialogbox()}
            }
        })
       dialogsubmit.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                val fileOutputStream=openFileOutput(FILE_NAME,Context.MODE_PRIVATE)
                fileOutputStream.write(dialogtitle.text.toString().toByteArray())

                val intent=Intent()
                intent.putExtra(LoginConstant.title1,dialogtitle.text.toString())
                intent.putExtra(LoginConstant.description1,dialogdesc.text.toString())
                val photo:String=photouri.toString()
                intent.putExtra(LoginConstant.imagepath,photo)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }
        })
    }
    private fun permissionchecked():Boolean{
        val camerapermission=ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
        val storagepermission=ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val listpermissionneed=ArrayList<String>()
        if(camerapermission!=PackageManager.PERMISSION_GRANTED){
            listpermissionneed.add(android.Manifest.permission.CAMERA)
        }
        if (storagepermission!=PackageManager.PERMISSION_GRANTED){
            listpermissionneed.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!listpermissionneed.isEmpty()){
            ActivityCompat.requestPermissions(this,listpermissionneed.toTypedArray<String>(),permissioncode)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissioncode->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    setupdialogbox()
                }
            }
        }
    }

    private fun setupdialogbox(){
        val view= LayoutInflater.from(this@dialogbox2).inflate(R.layout.cameradialog,null)
        val galary=view.findViewById<TextView>(R.id.galary)
        val camera=view.findViewById<TextView>(R.id.camera)
        val dialog= AlertDialog.Builder(this).setView(view).setCancelable(true).create()
        dialog.show()

        galary.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val intent=Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,galarypass)
                dialog.hide()
            }
        })
        camera.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val cameraintent=Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                var photofile: File?=null
                photofile=createimge()
                if(photofile!=null){
                    photouri= FileProvider.getUriForFile(this@dialogbox2, BuildConfig.APPLICATION_ID + ".provider",photofile)
                    cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,photouri)
                    startActivityForResult(cameraintent,camerapass)
                }
            }
        })

    }
    @SuppressLint("SimpleDateFormat")
    private fun createimge(): File? {
        val timestamp= SimpleDateFormat("yyyymmddhhmmss").format(Date())
        val filename="JPEG"+timestamp+"_"
        val storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(filename,".jpg",storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode==galarypass){
            dialogimg.setImageURI(data?.data)
            photouri=data?.data
        }
        if(resultCode==Activity.RESULT_OK && requestCode==camerapass){
            dialogimg.setImageURI(photouri)
        }
    }

    private fun bindviews(){
        dialogtitle=findViewById(R.id.dialogtitle)
        dialogdesc=findViewById(R.id.dialogdescription)
        dialogimg=findViewById(R.id.dialogimg)
        dialogback=findViewById(R.id.dialogback)
        dialogsubmit=findViewById(R.id.dialogsubmit)
    }
}