package com.example.bulsride.Itemclick
import com.example.bulsride.Database.Notes
interface Itemclick {
    fun onclick(note:Notes)
    fun onupdate(note: Notes)
}