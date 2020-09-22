package com.example.bulsride.Storedata

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.bulsride.variables.sharedpref

object Shared {
    private var sharedPreferences:SharedPreferences?=null
    fun get(context: Context){
        if (sharedPreferences==null){
            sharedPreferences= context.getSharedPreferences(sharedpref.shared,MODE_PRIVATE)
        }
    }
    fun write(key:String,value:Boolean){
        val editor:SharedPreferences.Editor?= sharedPreferences?.edit()
        editor?.putBoolean(key,value)
        editor?.apply()
    }
    fun write1(Onboardingsuccess:String,value: Boolean){
        val editor:SharedPreferences.Editor?= sharedPreferences?.edit()
        editor?.putBoolean(Onboardingsuccess,value)
        editor?.apply()
    }
    fun writeString(fullname:String,value:String){
        val editor:SharedPreferences.Editor?= sharedPreferences?.edit()
        editor?.putString(fullname,value)
        editor?.apply()
    }
    fun read(key: String):Boolean?{
        return sharedPreferences?.getBoolean(key,false)
    }
    fun read1(Onboardingsuccess: String):Boolean?{
        return sharedPreferences?.getBoolean(Onboardingsuccess,false)
    }
    fun readString(fullname: String):String?{
        return sharedPreferences?.getString(fullname,"")
    }
}