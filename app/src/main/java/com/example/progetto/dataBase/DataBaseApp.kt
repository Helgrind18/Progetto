package com.example.progetto.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente

//Questa classe definisce il database e le entità associate.
/*
* Funge da punto di accesso principale dell'app ai dati persistenti*/

@Database(entities = [Studente::class, Libro::class, Aula::class], version = 1) // array di database che contiene le entità
abstract class DataBaseApp : RoomDatabase() {

    companion object{
        const val NAME= "App_DataBase"
    }

    //Per ogni DAO definisco un metodo astratto che restituisce l'istanza del DAO corrispondente
    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao

}