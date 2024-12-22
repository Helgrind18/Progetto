package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "prestito")
data class Prestito(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ForeignKey(entity = Studente::class, parentColumns = ["matricola"], childColumns = ["matricolaStudente"])
    val matricolaStudente: Int,
    @ForeignKey(Libro::class, ["isbn"], ["idLibro"], ForeignKey.CASCADE)
    val idLibro: Long,
    val giorniRimanenti: Int
)

