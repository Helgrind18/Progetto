package com.example.progetto.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Libro")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    var iSBN: Long=0L,
    var name: String
)