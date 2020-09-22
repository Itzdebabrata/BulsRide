package com.example.bulsride.ViewPager

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.example.bulsride.R
import com.example.bulsride.Storedata.Shared
import com.example.bulsride.userAcivity.Login
import com.example.bulsride.userAcivity.MainActivity
import com.example.bulsride.variables.sharedpref
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnBoardingActivity : AppCompatActivity(),onNextClick,onNextTwo {
    lateinit var viewPage: ViewPager
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editors: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        supportActionBar?.hide()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        onbindviews()
        setupsharedpref()
    }
    private fun setupsharedpref(){
        Shared.get(this)
    }
    private fun onbindviews(){
        viewPage=findViewById(R.id.viewpage)
        val adaptor=FragmentAdaptor(supportFragmentManager)
        viewPage.adapter=adaptor
        val dotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        dotsIndicator.setViewPager(viewPage)
    }

    override fun onClick() {
        viewPage.currentItem=1
    }

    override fun onClickNext() {
        Shared.write1(sharedpref.Onboardingsuccess,true)
        val intent=Intent(this@OnBoardingActivity,Login::class.java)
        startActivity(intent)
    }

    override fun onClikBack() {
        viewPage.currentItem=0
    }
}