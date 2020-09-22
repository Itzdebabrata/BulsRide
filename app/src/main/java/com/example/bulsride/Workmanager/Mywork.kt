package com.example.bulsride.Workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.bulsride.Database.Darkdatabase
import com.example.bulsride.Ridersapp

class Mywork(val context:Context,val workerParameters: WorkerParameters): Worker(context,workerParameters) {
    override fun doWork(): Result{
        Log.d("djlll","fdsdfsf")
        val darkdat=applicationContext as Ridersapp
        val darkdao=darkdat.getDarkdb().darkDao()
        Log.d("djlll","fdsdfsf")
        darkdao.selectedDelet(true)
        return Result.success()
    }

}