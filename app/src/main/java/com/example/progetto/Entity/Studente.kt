package com.example.progetto.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Studente(
    @PrimaryKey(autoGenerate = true)
    var matricola: Int=0,
    var CF: String,
    var nome: String,
    var cognome: String,
    var dataDiNascita: Int,
    var ISEE: Long,
    var email: String
)