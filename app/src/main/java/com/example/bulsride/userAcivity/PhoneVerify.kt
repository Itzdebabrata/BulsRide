package com.example.bulsride.userAcivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.bulsride.R
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.concurrent.TimeUnit

class PhoneVerify : AppCompatActivity() {
    lateinit var systemcode:String
    lateinit var phonenumber:TextView
    lateinit var box1:EditText
    lateinit var box2:EditText
    lateinit var box3:EditText
    lateinit var box4:EditText
    lateinit var request:TextView
    lateinit var back:ImageView
    lateinit var verifyph:Button
    lateinit var progressBar:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_verify)
        bindviews()

        var intent=getIntent()
        var dj1=intent.getStringExtra("mob")
        phonenumber.setText(dj1)

        var ss=object:View.OnClickListener{
            override fun onClick(v: View?) {
                val intent=Intent(this@PhoneVerify, Signup::class.java)
                startActivity(intent)
            }
        }
        back.setOnClickListener(ss)
        /*var dj=object:View.OnClickListener{
            override fun onClick(v: View?) {

                var dat="1234"

                var data1=box1.text.toString()
                var data2=box2.text.toString()
                var data3=box3.text.toString()
                var data4=box4.text.toString()
                var data=data1+data2+data3+data4

                if(dat.equals(data)){
                    val intent=Intent(this@PhoneVerify,Login::class.java)
                    startActivity(intent)
                }
            }
        }
       verifyph.setOnClickListener(dj)*/

        sendVerificationCode(dj1)

    }
    private fun sendVerificationCode( dj1:String){
        var phonenumber=dj1
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phonenumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                TaskExecutors.MAIN_THREAD, // Activity (for callback binding)
                callbacks) // OnVerificationStateChangedCallbacks
    }
    var callbacks=object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            systemcode=p0
        }

        override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            var code=p0.smsCode
            if(code!=null){
                verifycode(code)
            }

        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(this@PhoneVerify,"djssss",Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifycode(usercode:String){

        var credential=PhoneAuthProvider.getCredential(systemcode,usercode)
        signupcredential(credential)
    }

    private fun signupcredential(credential: PhoneAuthCredential){

        var firebaseauth=FirebaseAuth.getInstance()
        firebaseauth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent=Intent(this@PhoneVerify,signup::class.java)
                startActivity(intent)

            } else {

            }
        }
    }
    private fun bindviews(){
        phonenumber=findViewById(R.id.phonenumber)
        box1=findViewById(R.id.textInput1)
        box2=findViewById(R.id.textInput2)
        box3=findViewById(R.id.textInput3)
        box4=findViewById(R.id.textInput4)
        request=findViewById(R.id.verify2)
        back=findViewById(R.id.appCompatImageView2)
        verifyph=findViewById(R.id.verifybtn)
        progressBar=findViewById(R.id.progressBar)
    }

}

