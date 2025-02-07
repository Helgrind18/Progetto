package com.example.progetto.Entity.Relazioni

import androidx.room.Embedded
import androidx.room.Relation
import com.example.progetto.Entity.Schemi.Libro
import com.example.progetto.Entity.Schemi.Studente

data class RelazionePrestitoLibro(
    @Embedded val studente: Studente,
    @Relation(
        parentColumn = "matricola",
        entityColumn = "matricolaStudente"
    )
    val libri: List<Libro>
)
