package com.example.progetto.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente

@Dao
interface LibroDao{
    @Query("SELECT * FROM Libro")
    fun getAll(): LiveData<List<Libro>>

    @Insert
    fun inserisciLibro(ISEE: Long)

    @Delete
    fun rimuoviLibro(ISBN: Long)
}

@Dao
interface StudenteDao{

    @Query("SELECT * FROM Studente")
    fun getAll(): LiveData<List<Studente>>

    @Insert
    fun inserisciStudente(nome: String, cognome: String, codiceFiscale: String, dataDiNascita: Int, ISEE: Long, email: String)

    @Delete
    fun rimuoviStudente(matricola: Int)
}

@Dao
interface AulaDao{

    @Query("SELECT * FROM Aula")
    fun getAll(): LiveData<List<Aula>>

    @Insert
    fun inserisciAula(cubo: Int, piano: Int, capienza: Int)

    @Delete
    fun rimuoviAula(cubo: Int)

}