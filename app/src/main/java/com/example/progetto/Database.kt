package com.example.progetto

import androidx.room.Entity
import androidx.room.ForeignKey

// Tabella Studenti
@Entity(
    tableName = "Studenti",
    primaryKeys = ["matricola", "CF"] // Chiave primaria composta
)
data class Studente(
    val matricola: String,
    val CF: String,
    val nome: String,
    val cognome: String,
    val email: String,
    val password: String,
    val ISEE: Int
)

// Tabella Corso
@Entity(
    tableName = "Corso",
    primaryKeys = ["id"] // Chiave primaria singola
)
data class Corso(
    val id: Int,
    val nome: String,
    val CFU: Int
)

// Tabella Segue (Associazione tra Studente e Corso)
@Entity(
    tableName = "Segue",
    primaryKeys = ["matricola", "CF", "id"], // Chiave primaria composta
    foreignKeys = [
        ForeignKey(
            entity = Studente::class, // Collegamento con Studente
            parentColumns = ["matricola", "CF"], // Colonne primary key di Studente
            childColumns = ["matricola", "CF"], // Colonne in Segue
            onDelete = ForeignKey.CASCADE // Elimina i record di Segue se lo studente viene eliminato
        ),
        ForeignKey(
            entity = Corso::class, // Collegamento con Corso
            parentColumns = ["id"], // Colonna primary key di Corso
            childColumns = ["id"], // Colonna in Segue
            onDelete = ForeignKey.CASCADE // Elimina i record di Segue se il corso viene eliminato
        )
    ]
)
data class Segue(
    val matricola: String, // Chiave esterna verso Studente
    val CF: String,        // Chiave esterna verso Studente
    val id: Int            // Chiave esterna verso Corso
)
@Entity(
    tableName = "Esame",
    primaryKeys = ["orario", "data"],
    foreignKeys = [
        ForeignKey(
            entity = Corso::class,
            parentColumns = ["id"],
            childColumns = ["idCorso"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

// commento per psuh

data class Esame(
    val idCorso: Int,   // Chiave esterna verso Corso
    val orario: Int,    // Parte della chiave primaria
    val data: String,   // Parte della chiave primaria
    val prenotazioni: Int,
    val durata: Int
)
data class Professore(
    val CF : String,
    val nome : String,
    val cognome : String,
    val email : String,
    val password: String,
    val ufficio: String,
    val telefonoUfficio: String,
)

@Entity(
    tableName = "Aula",
    primaryKeys = ["ora"], // Chiave primaria
    foreignKeys = [
        ForeignKey(
            entity = Corso::class,
            parentColumns = ["id"],
            childColumns = ["idCorso"],
            onDelete = ForeignKey.CASCADE // Elimina record in Aula se il corso viene eliminato
        ),
        ForeignKey(
            entity = Esame::class,
            parentColumns = ["idCorso", "orario", "data"], // Colonne di riferimento in Esame
            childColumns = ["idCorso", "oraEsame", "dataEsame"], // Colonne in Aula
            onDelete = ForeignKey.CASCADE // Elimina record in Aula se l'esame viene eliminato
        )
    ]
)
data class Aula(
    val ora: Int,        // Chiave primaria
    val idCorso: Int,    // Chiave esterna verso Corso
    val oraEsame: Int,   // Chiave esterna verso Esame
    val dataEsame: String, // Chiave esterna verso Esame
    val cubo: String,
    val piano: Int,
    val capienza: Int
)



data class Libro(
    val ISBN: String,
)

@Entity(
    tableName = "Scritto",
    primaryKeys = ["ISBN"],  // ISBN è la chiave primaria
    foreignKeys = [
        ForeignKey(
            entity = Libro::class, // Collegamento con Libro
            parentColumns = ["ISBN"], // Colonna primaria di Libro
            childColumns = ["ISBN"], // Colonna in Scritto
            onDelete = ForeignKey.CASCADE // Elimina i record in Scritto se il libro viene eliminato
        ),
        ForeignKey(
            entity = Professore::class, // Collegamento con Professore
            parentColumns = ["CF"], // Colonna primaria di Professore
            childColumns = ["CF"], // Colonna in Scritto
            onDelete = ForeignKey.SET_NULL, // Se il professore viene eliminato, imposta il CF a NULL
            onUpdate = ForeignKey.CASCADE // Se il CF del professore viene aggiornato, aggiorna anche i record in Scritto
        )
    ]
)
data class Scritto(
    val ISBN: String, // Chiave primaria
    val CF: String?   // Chiave esterna verso Professore (può essere null)
)

data class Archivio (
    //Questa è la biblioteca ma per evitare errori metto questo nome
    val nome: String,
    val posizione: String, //verrà gestita come un indirizzo
)
//Commento per Commit

@Entity(
    tableName = "LibriPrestati",
    primaryKeys = ["matricola", "CF", "nome", "posizione"], // Chiave primaria composta
    foreignKeys = [
        ForeignKey(
            entity = Studente::class, // Collegamento con Studente
            parentColumns = ["matricola", "CF"], // Colonne primary key di Studente
            childColumns = ["matricola", "CF"], // Colonne in LibriPrestati
            onDelete = ForeignKey.CASCADE // Elimina i record in LibriPrestati se lo studente viene eliminato
        ),
        ForeignKey(
            entity = Archivio::class, // Collegamento con Biblioteca
            parentColumns = ["nome", "posizione"], // Colonne primary key di Biblioteca
            childColumns = ["nome", "posizione"], // Colonne in LibriPrestati
            onDelete = ForeignKey.CASCADE // Elimina i record in LibriPrestati se la biblioteca viene eliminata
        )
    ]
)
data class LibriPrestati(
    val Scadenza: Int, // Viene gestita come giorni rimanenti
    val nome: String,   // Da Biblioteca
    val posizione: String, // Da Biblioteca
    val matricola: String, // Da Studente
    val CF: String,      // Da Studente
)