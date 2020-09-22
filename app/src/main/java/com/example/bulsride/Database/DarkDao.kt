package com.example.bulsride.Database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface DarkDao {
    @Query(value= "select  * from darkdata")
    fun getAll():List<Notes>
    @Insert(onConflict = REPLACE)
    fun inset(Storedata:Notes)
    @Update
    fun update(stroedata: Notes)
    @Delete
    fun delete(stroedata: Notes)
    @Query("DELETE FROM darkdata")
    fun delete()
    @Query("DELETE FROM darkdata WHERE istaskcomplet=:status")
    fun selectedDelet(status:Boolean)
}