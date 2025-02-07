package com.example.progetto.Entity.Schemi

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Libro",
    primaryKeys = ["name", "autore"], // Definizione delle chiavi primarie composite
    foreignKeys = [
        ForeignKey(
            entity = Studente::class,
            parentColumns = ["matricola"],
            childColumns = ["matricolaStudente"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Libro(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "autore") var autore: String,
    @ColumnInfo(name = "settore") var settore: String,
    // Relazione:
    // Studente -> Libro è 0:n (uno studente può prendere più libri)
    // Libro -> Studente è 0:1 (un libro può essere preso da uno studente)
    @ColumnInfo(name = "matricolaStudente") var matricolaStudente: Int? = null, // Foreign key
    @ColumnInfo(name = "sinossi") var sinossi: String
)
