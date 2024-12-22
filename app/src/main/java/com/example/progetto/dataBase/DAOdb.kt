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

import com.example.progetto.Entity.PrestitoConLibro
import com.example.progetto.Entity.Studente


@Dao
interface LibroDao {

    @Query("SELECT * FROM Libro")
    fun getAll(): LiveData<List<Libro>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciLibro(libro: Libro)

    @Delete
    fun rimuoviLibro(libro: Libro)

    @Query("DELETE FROM Libro WHERE ISBN = :iSBN")
    fun rimuoviLibroByISBN(iSBN: Long)

    @Query("SELECT * FROM Libro WHERE NAME = :name")
    fun getLibroByName(name: String): LiveData<List<Libro>>

    @Transaction //È necessario per garantire che le query multiple (relazionali) vengano eseguite come un'unica transazione atomica.
    @Query("SELECT * FROM Studente WHERE matricola = :matricola")
    fun getStudenteConLibri(matricola: Int): LiveData<Studente>
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
@Dao
interface PrestitoConLibroDAO {

    // Recupera tutti i prestiti associati a uno studente, dato il numero di matricola
    @Query("SELECT * FROM PrestitoConLibro WHERE matricolaStudente = :matricola")
    fun getPrestitiByStudente(matricola: Int): LiveData<List<PrestitoConLibro>>

    // Aggiunge un nuovo prestito
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun aggiungiPrestito(prestito: PrestitoConLibro)

    // Elimina un prestito esistente
    @Delete
    fun eliminaPrestito(prestito: PrestitoConLibro)

    // Recupera un prestito specifico per uno studente e un libro (usando matricola, nome e autore del libro)
    @Query("        SELECT * FROM PrestitoConLibro        WHERE matricolaStudente = :matricola        AND nameLibro = :name        AND autoreLibro = :autore    ")
    fun getPrestitoSpecifico(matricola: Int, name: String, autore: String): LiveData<PrestitoConLibro>

    // Ottiene tutti i libri presi in prestito con una scadenza specifica
    @Query("SELECT * FROM PrestitoConLibro WHERE dataScadenza <= :dataLimite    ")
    fun getPrestitiScaduti(dataLimite: String): LiveData<List<PrestitoConLibro>>

    // Ottiene tutti gli studenti che hanno preso in prestito un libro specifico
    @Query("SELECT * FROM PrestitoConLibro WHERE nameLibro = :name        AND autoreLibro = :autore ")
    fun getStudentiConLibro(name: String, autore: String): LiveData<List<PrestitoConLibro>>

    // Recupera tutti i prestiti con un ordinamento per data di scadenza
    @Query("SELECT * FROM PrestitoConLibro ORDER BY dataScadenza DESC ")
    fun getPrestitiOrdinatiPerScadenza(): LiveData<List<PrestitoConLibro>>
}

