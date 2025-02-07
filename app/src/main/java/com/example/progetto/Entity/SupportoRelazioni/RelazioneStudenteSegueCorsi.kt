package com.example.progetto.Entity.SupportoRelazioni

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.Studente

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
