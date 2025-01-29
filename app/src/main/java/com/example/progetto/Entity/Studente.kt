package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Studente")
data class Studente(
    @PrimaryKey
    @ColumnInfo(name= "matricola") val matricola: Int,
    @ColumnInfo(name = "CF") val cf: String,
    @ColumnInfo(name = "pswd") val pswd: String,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "cognome") val cognome: String,
    @ColumnInfo(name = "ISEE") val isee: Long,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "annoImmatricolazione") var annoImmatricolazione: Int,
    @ColumnInfo(name = "tassa1") var tassa1: Boolean,
    @ColumnInfo(name = "tassa2") var tassa2: Boolean,
    @ColumnInfo(name = "tassa3") var tassa3: Boolean,
    @ColumnInfo(name = "tassa4") var tassa4: Boolean,
    @ColumnInfo(name = "pastiEffettuati") var pastiEffettuati: Int
){
    override fun toString(): String {
        var ris: String=""
        ris+= "NOME:        $nome\n"
        ris+= "COGNOME:  $cognome\n"
        ris+= "MATRICOLA: $matricola\n"
        ris+= "ANNO DI IMMATRICOLAZIONE $annoImmatricolazione\$annoImmatricolazione+1"
        ris+= "EMAIL:      $email\n"
        return ris
    }
}

