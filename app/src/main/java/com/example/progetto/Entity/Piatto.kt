package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Piatto")
data class Piatto(
    @ColumnInfo(name = "piattoId")@PrimaryKey  val id: Int,
    @ColumnInfo(name = "tipo") val tipo: Int,
    @ColumnInfo(name = "nome") val nome: String,
)
