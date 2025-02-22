package com.example.progetto.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progetto.Entity.Schemi.Aula
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.Libro
import com.example.progetto.Entity.Schemi.Piatto
import com.example.progetto.Entity.Schemi.Pullman
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Studente

// Questa classe definisce il database e le entità associate.
// Funziona come punto di accesso principale dell'app ai dati persistenti
//Vengono definite tutte le entity con cui il db dovrà interfacciarsi e i vari DAO per ogni entity
@Database(
    entities = [
        Studente::class,
        Libro::class,
        Aula::class,
        Corso::class,
        RelazioneStudenteCorso::class, // Include la tabella intermedia many-to-many
        Piatto::class,
        Pullman::class
    ],
    version = 1
)
abstract class DataBaseApp : RoomDatabase() {
    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao
    abstract fun getCorsoDao(): CorsoDao
    abstract fun getRelazioneStudenteCorsoDao(): RelazioneStudenteCorsoDao
    abstract fun getPiattoDao(): PiattoDao
    abstract fun getPullmanDao(): PullmanDao
    companion object {
        @Volatile
        private var INSTANCE: DataBaseApp? = null

        // Funzione per ottenere il database
        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseApp::class.java,
                    "DatabaseProjectUnical89"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
