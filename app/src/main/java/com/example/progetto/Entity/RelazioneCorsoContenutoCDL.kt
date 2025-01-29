package com.example.progetto.Entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

//Questa relazione modella il fatto che un corso sia contenuto dentro più CDL

data class RelazioneCorsoContenutoCDL(
    @Embedded val corso: Corso,
    @Relation(
        parentColumn = "corsoId",
        entityColumn = "corsoLaureaId",
        associateBy = Junction(RelazioneCDLCorso::class)
    )
    val corsiDiLaurea: List<CorsoDiLaurea>
    )
