package com.example.progetto.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Corso
import com.example.progetto.Entity.CorsoDiLaurea
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Piatto
import com.example.progetto.Entity.RelazioneCDLCorso
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.Entity.Studente

// Questa classe definisce il database e le entità associate.
// Funziona come punto di accesso principale dell'app ai dati persistenti
@Database(
    entities = [
        Studente::class,
        Libro::class,
        Aula::class,
        Corso::class,
        RelazioneStudenteCorso::class, // Include la tabella intermedia many-to-many
        RelazioneCDLCorso::class, // Include la tabella intermedia many-to-many
        CorsoDiLaurea::class,
        Piatto::class
    ],
    version = 1
)
abstract class DataBaseApp : RoomDatabase() {
    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao
    abstract fun getCorsoDao(): CorsoDao
    abstract fun getRelazioneStudenteCorsoDao(): RelazioneStudenteCorsoDao
    abstract fun getRelazioneCDLCorsoDao(): RelazioneCDLCorsoDao
    abstract fun getCDLDao(): CorsoDiLaureaDao
    abstract fun getPiattoDao(): PiattoDao
    companion object {
        @Volatile
        private var INSTANCE: DataBaseApp? = null

        // Funzione per ottenere il database
        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseApp::class.java,
                    "DatabaseProjectUnical44"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
