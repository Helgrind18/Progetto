package com.example.progetto.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Studente::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    // Classe per gestire il database degli studenti
    abstract fun studenteDao(): StudenteDAO

    companion object {
        //In questo modo implemento il Singleton, garantendo che ci sia una sola istanza del db all'interno dell'applicazione
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        //La variabile INSTANCE è marcata come @Volatile per assicurare che
        // i cambiamenti alla variabile siano immediatamente visibili a tutti i thread.
        fun getDatabase(context: Context): StudentDatabase {
            //Questo metodo crea e restituisce l'istanza del database.
            //Se INSTANCE è già inizializzata, viene restituita.
            //Altrimenti. Se non è inizializzata,
            // il blocco sincronizzato (synchronized) garantisce che solo un thread alla volta possa creare l'istanza.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "Student_Database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
