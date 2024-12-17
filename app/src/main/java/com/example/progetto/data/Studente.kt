package com.example.progetto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studenti")
data class Studente (
    @PrimaryKey(autoGenerate = true) val matricola: Int,
    val nome: String,
    val cognome: String,
)