package com.example.progetto.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente

//Questa classe definisce il database e le entità associate.
/*
* Funge da punto di accesso principale dell'app ai dati persistenti
* */

@Database(entities = [Studente::class, Libro::class, Aula::class], version = 1) // array di database che contiene le entità
abstract class DataBaseApp : RoomDatabase() {
    //Per ogni DAO definisco un metodo astratto che restituisce l'istanza del DAO corrispondente
    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao

    companion object {
        @Volatile
        private var INSTANCE: DataBaseApp? = null
        //getDatabase() assicura che venga creata una sola istanza del db
        fun getDatabase(context: Context): DataBaseApp {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataBaseApp::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}