package com.example.progetto.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Corso
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.Entity.Studente
import com.example.progetto.Entity.RelazioneStudenteSegueCorsi


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

    //!!!!!!!! ATTENZIONE NON SO SE FUNZIONA LA QUERY DI SOTTO!!!!!!!!

    @Transaction //È necessario per garantire che le query multiple (relazionali) vengano eseguite come un'unica transazione atomica.
    @Query("SELECT * FROM Libro l WHERE l.matricolaStudente = :matricola")
    fun getLibriByStudente(matricola: Int): LiveData<List<Libro>>

    @Query("SELECT * FROM Libro WHERE settore = :settore")
    fun getLibriBySettore(settore: String): LiveData<List<Libro>>

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
interface CorsoDao {
    @Query("SELECT * FROM Corso")
    fun getAll(): LiveData<List<Corso>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciCorso(corso: Corso)

    @Delete
    fun rimuoviCorso(corso: Corso)

    @Query("DELETE FROM Corso WHERE corsoId = :id")
    fun rimuoviCorsoById(id: Int)

    //Recupera il corso dall'id
    @Query("SELECT * FROM Corso WHERE corsoId = :id")
    fun getCorsoById(id: Int): LiveData<Corso>?

}

@Dao
interface RelazioneStudenteCorsoDao {
        // Inserisce una relazione tra uno studente e un corso
        @Transaction
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun inserisciRelazione(relazione: RelazioneStudenteCorso)

        // Rimuove una relazione specifica
        @Delete
        @Transaction
        fun rimuoviRelazione(relazione: RelazioneStudenteCorso)

        // Recupera tutti i corsi di uno studente dato
        @Transaction
        @Query("SELECT r.corsoId FROM RelazioneStudenteCorso r WHERE r.matricola = :matricola")
        fun getCorsiDiStudente(matricola: Int): List<Int>?

        // Recupera tutti gli studenti iscritti a un corso dato
        @Transaction
        @Query("SELECT r.matricola FROM RelazioneStudenteCorso r WHERE r.corsoId = :corsoId")
        fun getStudentiDiCorso(corsoId: Int): List<Int>

        //Recupera tutti i corsi seguiti da uno studente in un determinato giorno
        @Transaction
        @Query("SELECT r.aula FROM RelazioneStudenteCorso r where r.giorno = :giorno")
        fun getCorsiSeguitiDaStudenteInUnGiorno(giorno: String): List<String>?

        //Recupera tutti i voti di uno studente
        @Transaction
        @Query("SELECT r.voto FROM RelazioneStudenteCorso r where r.matricola = :matricola")
        fun getVotiDiStudente(matricola: Int): List<Int>?
}

