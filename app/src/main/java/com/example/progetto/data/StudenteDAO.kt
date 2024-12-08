package com.example.progetto.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudenteDAO {
    //Ha i metodi per accedere al database
    @Insert(onConflict = OnConflictStrategy.IGNORE) // In questo modo non succede niente se c'è il conflitto
    suspend fun inserisciStudente(studente: Studente)
    @Query("SELECT * FROM studenti")
    fun getAllStudents(): LiveData<List<Studente>>
}