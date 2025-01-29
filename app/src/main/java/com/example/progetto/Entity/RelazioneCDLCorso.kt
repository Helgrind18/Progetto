package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity

//Questa relazione modella il fatto che
/*
* Un CDL ha 0:n corsi, un corso può essere contenuto in più CDL
* */
@Entity(
    primaryKeys = ["corsoId", "corsoLaureaId"]
)
data class RelazioneCDLCorso (
    @ColumnInfo(name = "corsoId") val corsoId: Int,
    @ColumnInfo(name = "corsoLaureaId") val cdlId: Int,
)