package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date


data class PrestitoConLibro(
    @Embedded val Studente: Studente,
    @Relation(
        parentColumn = "matricola",
        entityColumn = "matricolaStudente"
    )
    val libri: List<Libro>
)



/*

@Entity(
    tableName = "PrestitoConLibro",
    foreignKeys = [
        ForeignKey(
            entity = Studente::class,
            parentColumns = ["matricola"],
            childColumns = ["matricolaStudente"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Libro::class,
            parentColumns = ["name", "autore"],
            childColumns = ["nameLibro", "autoreLibro"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PrestitoConLibro(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "matricolaStudente") val matricolaStudente: Int, // Chiave esterna per Studente
    @ColumnInfo(name = "nameLibro") val nameLibro: String, // Chiave esterna per Libro (name)
    @ColumnInfo(name = "autoreLibro") val autoreLibro: String, // Chiave esterna per Libro (autore)
    @ColumnInfo(name = "dataScadenza") val dataScadenza: Int // Data di scadenza per il ritorno del libro (è un intero per facilitare la gestione)
)


 */