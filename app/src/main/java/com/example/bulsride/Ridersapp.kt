package com.example.bulsride

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.example.bulsride.Database.Darkdatabase


class Ridersapp:Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext);

    }
    fun getDarkdb():Darkdatabase{
        return Darkdatabase.getInstance(this)
    }
}