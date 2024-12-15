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
    fun inserisciLibro(libro: Libro)

    @Delete
    fun rimuoviLibro(libro: Libro)

    @Query("DELETE FROM Libro WHERE ISBN = :iSBN")
    fun rimuoviLibro(iSBN: Long)
}

@Dao
interface StudenteDao{

    @Query("SELECT * FROM Studente")
    fun getAll(): LiveData<List<Studente>>

    @Insert
    fun inserisciStudente(studente: Studente)

    @Delete
    fun rimuoviStudente(studente: Studente)

    @Query("DELETE FROM Studente WHERE matricola = :matricola")
    fun rimuoviStudente(matricola: Int)
}

@Dao
interface AulaDao{

    @Query("SELECT * FROM Aula")
    fun getAll(): LiveData<List<Aula>>

    @Insert
    fun inserisciAula(aula: Aula)

    @Delete
    fun rimuoviAula(aula: Aula)

    @Query("DELETE FROM Aula WHERE cubo = :cubo")
    fun rimuoviAula(cubo: Int)


}