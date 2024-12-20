package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Libro")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "isbn") // Specifica il nome della colonna
    var iSBN: Long = 0L,
    var name: String,
    val autore: String,
    val settore: String
)