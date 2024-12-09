package com.example.progetto.data

import androidx.room.Database
import androidx.room.RoomDatabase

//Questa classe rappresenta il database, definisce la configurazione e le operazioni di accesso ai dati
@Database(entities = [Studente::class], version = 1)
//Ho elencato tutte le entità di dati associati al database
abstract class AppDatabase : RoomDatabase() {
    //Per ogni classe DAO associata al datbase, la classe deve definire un metodo astratto che restituisca un'istanza della classe DAO
    abstract fun studenteDAO(): StudenteDAO

}