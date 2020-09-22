package com.example.bulsride.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName= "darkdata")
data class Notes(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        @ColumnInfo(name = "title")
        var title: String? = "",
        @ColumnInfo(name = "description")
        var description: String? = "",
        @ColumnInfo(name = "istaskcomplet")
        var istaskcomplet: Boolean? = false,
        @ColumnInfo(name="imagepath")
        var imagepath: String
)