package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Libro")
data class Libro(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "isbn") // Specifica il nome della colonna
    var iSBN: Long = 0L,
    @ColumnInfo(name= "name")var name: String,
    @ColumnInfo(name= "autore")val autore: String,
    @ColumnInfo(name= "settore")val settore: String
)