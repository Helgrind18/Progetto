package com.example.progetto.Entity

import androidx.room.Embedded
import androidx.room.Relation


data class StudenteConLibri(
    @Embedded val studente: Studente,
    @Relation(
        parentColumn = "id",
        entityColumn = "studenteId",
        entity = Prestito::class
    )
    val prestiti: List<Prestito>
)