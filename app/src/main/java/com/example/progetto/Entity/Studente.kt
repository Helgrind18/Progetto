package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Studente")
data class Studente(
    @PrimaryKey val matricola: Int,
    @ColumnInfo(name = "CF") val cf: String,
    @ColumnInfo(name = "pswd") val pswd: String,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "cognome") val cognome: String,
    @ColumnInfo(name = "ISEE") val isee: Long,
    @ColumnInfo(name = "email") val email: String,
)
