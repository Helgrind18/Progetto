package com.example.progetto.Entity.SupportoRelazioni

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.progetto.Entity.Relazioni.RelazioneCDLCorso
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.CorsoDiLaurea


//Questa relazione modella il fatto che un CDL contiene 0:n corsi
data class RelazioneCDLContieneCorsi(
    @Embedded val corsoDiLaurea: CorsoDiLaurea,
    @Relation(
        parentColumn = "corsoLaureaId",
        entityColumn = "corsoId",
        associateBy = Junction(RelazioneCDLCorso::class)
    )
    val corsi: List<Corso>
    )
