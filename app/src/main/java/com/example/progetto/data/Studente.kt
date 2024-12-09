package com.example.progetto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studenti",
    primaryKeys = ["matricola", "CF"])
data class Studente(
    val matricola: String,
    val CF: String,
    val nome: String,
    val cognome: String,
    val eta: Int,
)
@Entity (tableName = "Libro",)
data class Libro (
    @PrimaryKey val ISBN: String,
    val numPagine: Int,
    val titolo: String,
    val autore: String,
)