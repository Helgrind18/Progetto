package com.example.progetto.Entity.SupportoRelazioni

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progetto.Entity.Relazioni.RelazioneCDLCorso
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.CorsoDiLaurea

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
