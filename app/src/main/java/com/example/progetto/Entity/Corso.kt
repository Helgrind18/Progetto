package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Corso")
data class Corso(
    @ColumnInfo(name = "corsoId") @PrimaryKey val id: Int,
    val nome: String,
    val CFU: Int,
    val anno: Int
)
