package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Libro")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    var iSBN: Long=0L,
    val name: String,
    val autore: String,
    val settore: String
)