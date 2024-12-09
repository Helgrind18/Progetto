package com.example.progetto.data

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Un DAO è un'interfaccia che contiene i metodi per l'accesso ai dati
interface StudenteDAO {

    @Query("SELECT * FROM studenti")
    fun getAllStudenti(): LiveData<List<Studente>>

    @Query("SELECT * FROM studenti WHERE matricola = :matricola")
    fun getStudenteByMatricola(matricola: String): LiveData<Studente>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStudente(studente: Studente)

    @Delete
    fun deleteStudente(studente: Studente)

    @Query("SELECT * FROM studenti WHERE eta > :eta")
    fun loadAllStudentsOlderThan(eta: Int): LiveData<List<Studente>>
}