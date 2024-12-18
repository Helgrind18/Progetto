package com.example.progetto.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Studente")
data class Studente(
    @PrimaryKey(autoGenerate = false)
    var matricola: Int,
    var CF: String,
    var pswd: String,
    var nome: String,
    var cognome: String,
    var ISEE: Long,
    var email: String
)

