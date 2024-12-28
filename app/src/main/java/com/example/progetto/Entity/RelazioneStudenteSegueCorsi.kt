package com.example.progetto.Entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

//Questa relazione modella uno studente che può seguire più corsi

data class RelazioneStudenteSegueCorsi(
    @Embedded val studente: Studente,
    @Relation(
        parentColumn = "matricola",
        entityColumn = "corsoId",
        associateBy = Junction(RelazioneStudenteCorso::class)
)
    val corsi: List<Corso>
)
