package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity

//Questa è la relazione che collega uno studente e corso
//uno studente può seguire più corsi, uno stesso corso è seguito da più studenti

@Entity(primaryKeys = ["corsoId", "matricola"])
data class RelazioneStudenteCorso(
    @ColumnInfo(name = "corsoId") val corsoId: Int,
    @ColumnInfo(name = "matricola") val matricola: Int,
)
