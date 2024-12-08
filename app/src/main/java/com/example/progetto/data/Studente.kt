package com.example.progetto.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studenti")
data class Studente (
    @PrimaryKey(autoGenerate = true) //Verranno generate le chiavi automaticamente
    val matricola: String,
    val CF: String,
    val nome: String,
    val cognome: String,
    val email: String,
    val password: String,
    val ISEE: Int

)