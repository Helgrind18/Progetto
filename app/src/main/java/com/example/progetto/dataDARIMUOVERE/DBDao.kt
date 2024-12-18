package com.example.progetto.dataDARIMUOVERE

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DBDao {
    @Insert
    fun insert(studente: Studente)

    @Query("SELECT * FROM studenti WHERE matricola = :matricola")
    fun getStudenteByMatricola(matricola: Int): Studente

    @Query("SELECT * FROM studenti")
    fun getAllStudenti(): LiveData<List<Studente>>

    @Delete
    fun deleteStudente(studente: Studente)

}