package com.example.progetto.Entity

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
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "autore") val autore: String,
    @ColumnInfo(name = "settore") val settore: String,
    @ColumnInfo(name = "isbn") val iSBN: Long = 0L, // Non è più una chiave primaria
    // Relazione:
    // Studente -> Libro è 0:n (uno studente può prendere più libri)
    // Libro -> Studente è 0:1 (un libro può essere preso da uno studente)
    @ColumnInfo(name = "matricolaStudente") val matricolaStudente: Int? = null // Foreign key
)
