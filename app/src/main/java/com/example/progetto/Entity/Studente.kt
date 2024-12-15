package com.example.progetto.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Studente")
data class Studente(
    @PrimaryKey(autoGenerate = false)
    var matricola: Int,
    var CF: String,
    var nome: String,
    var cognome: String,
    var dataDiNascita: Int,
    var ISEE: Long,
    var email: String
)