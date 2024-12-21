package com.example.progetto.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente

//Questa classe definisce il database e le entità associate.
/*
* Funge da punto di accesso principale dell'app ai dati persistenti
* */

@Database(entities = [Studente::class, Libro::class, Aula::class], version = 2) // Incrementa la versione se hai modifiche
abstract class DataBaseApp : RoomDatabase() {
    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao

    companion object {
        @Volatile
        private var INSTANCE: DataBaseApp? = null

        // Definizione delle migrazioni
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Esempio di modifica: aggiunta di una nuova colonna alla tabella "Studente"
                database.execSQL("ALTER TABLE Studente ADD COLUMN nuovaColonna TEXT DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseApp::class.java,
                    "DatabaseApplicazione"
                )
                    .addMigrations(MIGRATION_1_2) // Aggiungi qui le migrazioni
                    .fallbackToDestructiveMigration() // Usa solo se vuoi distruggere il DB in caso di errore
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

