package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity

//Questa è la relazione che collega uno studente e corso
//uno studente può seguire più corsi, uno stesso corso è seguito da più studenti

@Entity(
    primaryKeys = ["corsoId", "matricola"]
)
data class RelazioneStudenteCorso(
    @ColumnInfo(name = "corsoId") val corsoId: Int,
    @ColumnInfo(name = "matricola") val matricola: Int,
    @ColumnInfo(name = "giorno") val giorno: String,
    @ColumnInfo(name = "ora") val ora: String,  // Ora di inizio (esempio: "10:00", "14:30")
    @ColumnInfo(name = "aula") val aula: String  // Aula in cui si svolge il corso
)