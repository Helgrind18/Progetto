package com.example.progetto.Entity

import androidx.room.Embedded
import androidx.room.Relation

data class RelazionePrestitoLibro(
    @Embedded val studente: Studente,
    @Relation(
        parentColumn = "matricola",
        entityColumn = "matricolaStudente"
    )
    val libri: List<Libro>
)
