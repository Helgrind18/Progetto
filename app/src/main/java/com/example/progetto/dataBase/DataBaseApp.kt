package com.example.progetto.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.PrestitoConLibro
import com.example.progetto.Entity.Studente

// Questa classe definisce il database e le entità associate.
// Funziona come punto di accesso principale dell'app ai dati persistenti
@Database(entities = [Studente::class, Libro::class, Aula::class], version = 1) // Incrementa la versione se hai modifiche
abstract class DataBaseApp : RoomDatabase() {
    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao

    companion object {
        @Volatile
        private var INSTANCE: DataBaseApp? = null

        // Funzione per ottenere il database
        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseApp::class.java,
                    "DatabaseProjectUnical4"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
