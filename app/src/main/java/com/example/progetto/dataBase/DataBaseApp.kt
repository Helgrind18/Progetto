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

        // Definizione delle migrazioni
        /*private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Aggiunta di una nuova colonna "nuovaColonna" alla tabella "Studente"
                database.execSQL("ALTER TABLE Studente ADD COLUMN nuovaColonna TEXT DEFAULT ''")

                // Aggiunta di una nuova colonna "annoPubblicazione" alla tabella "Libro"
                database.execSQL("ALTER TABLE Libro ADD COLUMN annoPubblicazione INTEGER DEFAULT 0")

                // Aggiunta di una nuova colonna "capacità" alla tabella "Aula"
                database.execSQL("ALTER TABLE Aula ADD COLUMN capacità INTEGER DEFAULT 0")
            }
        }*/

        // Funzione per ottenere il database
        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseApp::class.java,
                    "NuovoDatabase"
                )
                    /*.addMigrations(MIGRATION_1_2) // Aggiungi qui le migrazioni
                    .fallbackToDestructiveMigration() // Usa solo se vuoi distruggere il DB in caso di errore
                     */
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
