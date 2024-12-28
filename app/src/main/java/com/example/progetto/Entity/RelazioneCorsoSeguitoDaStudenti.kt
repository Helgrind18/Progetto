package com.example.progetto.Entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

//Questa modella il fatto che più studenti seguono lo stesso corso
data class RelazioneCorsoSeguitoDaStudenti(
    @Embedded val corso: Corso,
    @Relation(
        parentColumn = "corsoId",
        entityColumn = "matricola",
        associateBy = Junction(RelazioneStudenteCorso::class)
    )
    val studenti: List<Studente>
)
