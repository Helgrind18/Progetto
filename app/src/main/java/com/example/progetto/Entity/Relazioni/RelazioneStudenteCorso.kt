package com.example.progetto.Entity.Relazioni

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.progetto.Entity.Schemi.Corso

//Questa è la relazione che collega uno studente e corso
//uno studente può seguire più corsi, uno stesso corso è seguito da più studenti
@Entity(
    primaryKeys = ["corsoId", "matricola"],
    foreignKeys = [
        ForeignKey(
            entity = Corso::class,
            parentColumns = ["corsoId"],  // La FK punta alla PK di Corso
            childColumns = ["corsoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RelazioneStudenteCorso(
    @ColumnInfo(name = "corsoId") val corsoId: Int,  // FK corretta
    @ColumnInfo(name = "matricola") val matricola: Int,
    @ColumnInfo(name = "giorno") val giorno: Int,
    @ColumnInfo(name = "ora") val ora: String,
    @ColumnInfo(name = "aula") val aula: String,
    @ColumnInfo(name = "voto") val voto: Int,
    @ColumnInfo(name = "prenotazione") var prenotazione: Int,
    @ColumnInfo(name = "nomeCorso") val nomeCorso: String  // Solo per riferimento, NON FK
)
