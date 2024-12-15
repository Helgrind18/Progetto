package com.example.progetto.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente

@Database(entities = [Studente::class, Libro::class, Aula::class], version = 1)
abstract class DataBaseApp : RoomDatabase() {

    companion object{
        const val NAME= "App_DataBase"
    }

    abstract fun getStudenteDao(): StudenteDao
    abstract fun getLibroDao(): LibroDao
    abstract fun getAulaDao(): AulaDao

}