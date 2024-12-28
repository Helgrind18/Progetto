package com.example.progetto.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro

import com.example.progetto.Entity.Studente


@Dao
interface LibroDao {

    @Query("SELECT * FROM Libro")
    fun getAll(): LiveData<List<Libro>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciLibro(libro: Libro)

    @Delete
    fun rimuoviLibro(libro: Libro)

    @Query("DELETE FROM Libro WHERE name = :nome AND autore = :autore")
    fun rimuoviLibroByNomeEAutore(nome: String, autore: String)

    @Query("SELECT * FROM Libro WHERE NAME = :name")
    fun getLibroByName(name: String): LiveData<List<Libro>>


    @Query("UPDATE Libro SET matricolaStudente = :matricola WHERE name = :nome AND autore = :autore")
    fun aggiuntaStudenteCheHaPresoLibroInPrestito(nome: String, autore: String,matricola: Int)


}
@Dao
interface StudenteDao {

    @Query("SELECT * FROM Studente")
    fun getAll(): LiveData<List<Studente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciStudente(studente: Studente)

    @Delete
    fun rimuoviStudente(studente: Studente)

    @Query("DELETE FROM Studente WHERE matricola = :matricola")
    fun rimuoviStudenteByMatricola(matricola: Int)

    @Query("SELECT * FROM Studente WHERE matricola = :matricola")
    fun getStudenteByMatricola(matricola: Int): Studente?


    // metodo che mi permette di ottenere tutti i libri presi in prestito da uno studente
    @Transaction
    @Query("SELECT * FROM Libro WHERE matricolaStudente = :matricola")
    fun getListaLibriDiStudente(matricola: Int): LiveData<List<Libro>>

}


@Dao
interface AulaDao{

    @Query("SELECT * FROM Aula")
    fun getAll(): LiveData<List<Aula>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciAula(aula: Aula)

    @Delete
    fun rimuoviAula(aula: Aula)

    @Query("DELETE FROM Aula WHERE cubo = :cubo")
    fun rimuoviAula(cubo: Int)


}






