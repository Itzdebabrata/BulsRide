package com.example.bulsride.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class],version = 1)
abstract class Darkdatabase:RoomDatabase() {
    abstract fun darkDao(): DarkDao

    companion object {
        lateinit var INSTANCE: Darkdatabase
        fun getInstance(Context: Context): Darkdatabase {
            synchronized(Darkdatabase::class) {
                INSTANCE = Room.databaseBuilder(Context.applicationContext, Darkdatabase::class.java, "Riderdatabase.db")
                        .allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}