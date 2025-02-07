package com.example.progetto.Entity.SupportoRelazioni

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.Studente

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
