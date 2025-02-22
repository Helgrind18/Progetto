package com.example.progetto.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.progetto.Entity.Schemi.Aula
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.Libro
import com.example.progetto.Entity.Schemi.Piatto
import com.example.progetto.Entity.Schemi.Pullman
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Studente

//Qui dentro sono presenti tutte le interfacce per gestire le query
//Abbiamo deciso di gestire interfacce singole per ogni Entity per semplicità e per facilitare la lettura del codice
@Dao
interface LibroDao {

    @Query("SELECT * FROM Libro")
    fun getAllLibri(): LiveData<List<Libro>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciLibro(libro: Libro)

    @Delete
    fun rimuoviLibro(libro: Libro)

    @Query("SELECT * FROM Libro WHERE NAME = :name")
    fun getLibroByName(name: String): LiveData<List<Libro>>

    @Transaction
    @Query("SELECT * FROM Libro WHERE name = :nome AND autore = :autore")
    fun getLibroByNomeAutore(nome: String, autore: String): Libro?

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
    fun getAllStudenti(): LiveData<List<Studente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciStudente(studente: Studente)

    @Delete
    fun rimuoviStudente(studente: Studente)

    @Query("DELETE FROM Studente WHERE matricola = :matricola")
    fun rimuoviStudenteByMatricola(matricola: Int)

    @Query("SELECT * FROM Studente WHERE matricola = :matricola")
    fun getStudenteByMatricola(matricola: Int): Studente?

    @Query("SELECT s.pastiEffettuati FROM Studente s WHERE s.matricola = :matricola")
    fun getPastiEffettuati(matricola: Int): Int?

}

@Dao
interface AulaDao{

    @Query("SELECT * FROM Aula")
    fun getAllAule(): LiveData<List<Aula>>

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
    fun getAllCorsi(): LiveData<List<Corso>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciCorso(corso: Corso)

    @Delete
    fun rimuoviCorso(corso: Corso)

    @Query("DELETE FROM Corso WHERE corsoId = :id")
    fun rimuoviCorsoById(id: Int)

    //Recupera il corso dall'id
    @Query("SELECT * FROM Corso WHERE corsoId = :id")
    fun getCorsoById(id: Int): Corso?

}


@Dao
interface RelazioneStudenteCorsoDao {
        //Recupera tutte le relazioni
        @Transaction
        @Query("SELECT * FROM RelazioneStudenteCorso")
        fun getAllRelazioniStudenteCorso(): LiveData<List<RelazioneStudenteCorso>>

        //Recupera tutte le relazioni sotto forma di lista
        @Transaction
        @Query("SELECT * FROM RelazioneStudenteCorso")
        fun getAllRelazioniStudenteCorsoList(): List<RelazioneStudenteCorso>

        // Inserisce una relazione tra uno studente e un corso
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun inserisciRelazione(relazione: RelazioneStudenteCorso)

        // Rimuove una relazione specifica
        @Delete
        fun rimuoviRelazione(relazione: RelazioneStudenteCorso)

        // Recupera tutti i corsi di uno studente dato
        @Transaction
        @Query("SELECT r.corsoId FROM RelazioneStudenteCorso r WHERE r.matricola = :matricola")
        fun getCorsiDiStudente(matricola: Int): List<Int>?

        //Recupera tutti i corsi seguiti da uno studente in un determinato giorno
        @Transaction
        @Query("SELECT r.aula FROM RelazioneStudenteCorso r where r.giorno = :giorno")
        fun getCorsiSeguitiDaStudenteInUnGiorno(giorno: String): List<String>?

        //Recupera tutti gli esami sostenuti da uno studente
        @Transaction
        @Query("SELECT r.* FROM RelazioneStudenteCorso r, Corso c" +
                " where r.matricola = :matricola AND r.voto >= 18 AND r.corsoId = c.corsoId")
        fun getEsamiDiStudente(matricola: Int): List<RelazioneStudenteCorso>?

        @Transaction
        @Query("SELECT r.* FROM RelazioneStudenteCorso r, Corso c" +
            " WHERE r.matricola = :matricola AND r.corsoId = c.corsoId" +
            " ORDER BY c.anno ASC, c.semestre ASC")
        fun getEsamiDiStudenteLD(matricola: Int): LiveData<List<RelazioneStudenteCorso>>?


        //Recupera tutti gli esami che uno studente ancora non ha sostenuto
        @Transaction
        @Query("SELECT c.* FROM RelazioneStudenteCorso r, Corso c where " +
                "r.matricola = :matricola AND r.voto = -1 and r.corsoId = c.corsoId")
        fun getEsamiDaFareStudente(matricola: Int): LiveData<List<Corso>>?

        //Recupera tutti i corsi seguiti da uno studente che ancora non ha sostenuto l'esame di un determinato anno
        @Transaction
        @Query("SELECT c.* FROM RelazioneStudenteCorso r, Corso c" +
                " where r.matricola = :matricola AND r.voto = -1 AND r.corsoId = c.corsoId AND c.anno = :anno")
        fun getEsamiDaFareDiUnAnno(matricola: Int, anno: Int): LiveData<List<Corso>>?

        //Recupera tutti gli esami che uno studente può prenotare e che sono compatibili per il suo anno
        @Transaction
        @Query("SELECT DISTINCT c.* FROM RelazioneStudenteCorso r, Corso c, Studente s"+
        " where r.matricola = :matricola AND r.voto = -1 AND r.corsoId = c.corsoId AND (anno = :anno -s.annoImmatricolazione+1)<=c.anno" )
        fun getEsamiDaFareCompatibili(matricola: Int, anno: Int): LiveData<List<Corso>>?

        //Recupera tutti gli esami che uno studente può prenotare e che sono compatibili per il suo anno
        @Transaction
        @Query("SELECT DISTINCT r.* FROM RelazioneStudenteCorso r, Corso c, Studente s"+
            " where r.matricola = :matricola AND r.voto = -1 AND r.corsoId = c.corsoId " +
                "AND (anno = :anno -s.annoImmatricolazione+1)<=c.anno AND r.prenotazione = 0"
        )
        fun getEsamiPrenotabili(matricola: Int, anno: Int): LiveData<List<RelazioneStudenteCorso>>?

        //Recupera tutte le prenotazioni di uno studente, ovvero dove il booleano è 1
        @Transaction
        @Query("SELECT DISTINCT r.* FROM RelazioneStudenteCorso r, Corso c, Studente s"+
                " where r.matricola = :matricola AND r.voto = -1 AND r.corsoId = c.corsoId " +
                "AND (anno = :anno -s.annoImmatricolazione+1)<=c.anno AND r.prenotazione = 1"
        )
        fun getEsamiPrenotati(matricola: Int, anno: Int): LiveData<List<RelazioneStudenteCorso>>?

        //Calcola la media aritmetica dello studente
        @Transaction
        @Query("SELECT  AVG(r.voto) " +
                "FROM RelazioneStudenteCorso r, Corso c " +
                "WHERE r.matricola = :matricola " +
                "AND r.corsoId = c.corsoId " +
                "AND r.voto >= 18 " +
                "GROUP BY r.matricola")
        fun getMedia(matricola: Int): Double?


        //Calcola la media ponderata dello studente
        @Transaction
        @Query("SELECT  SUM(r.voto*c.CFU) / SUM(c.CFU) " +
                "FROM RelazioneStudenteCorso r, Corso c " +
                "WHERE r.matricola = :matricola " +
                "AND r.corsoId = c.corsoId " +
                "AND r.voto >= 18 " +
                "GROUP BY r.matricola")
        fun getMediaPonderata(matricola: Int): Double?

        //Recupera le lezioni dato un giorno della settimana (tiene conto anche dell'anno e dello studente)
        @Transaction
        @Query(
            "SELECT r.* " +
            "FROM RelazioneStudenteCorso r, Corso c " +
            "WHERE r.matricola = :matricola " +
            "AND r.giorno = :giorno " +
            "AND r.corsoId = c.corsoId "+
            "AND c.anno = :anno " +
            "AND c.semestre = :semestre "
        )
        fun getLezioni(giorno: Int, matricola: Int, anno: Int, semestre: Int): LiveData<List<RelazioneStudenteCorso>>?

}

@Dao
interface PiattoDao{
    @Query("SELECT * FROM Piatto")
    fun getAllPiatti(): LiveData<List<Piatto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciPiatto(piatto: Piatto)

    @Delete
    fun rimuoviPiatto(piatto: Piatto)

    //Recupera il piatto dall'id
    @Query("SELECT * FROM Piatto p WHERE p.piattoId = :id")
    fun getPiattoById(id: Int): Piatto?

    //Recupera i piatti per tipo
    @Query("SELECT * FROM Piatto p WHERE p.tipo = :tipo")
    fun getPiattiByTipo(tipo: Int): List<Piatto>?
}

@Dao
interface PullmanDao{
    //Recupera i pullman
    @Query("SELECT * FROM Pullman")
    fun getAllPullman(): LiveData<List<Pullman>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciPullman(pullman: Pullman)

    //Recupera il pullman dato l'id
    @Query("SELECT * FROM Pullman p WHERE p.id = :id")
    fun getPullmanById(id: Int): Pullman?

    //Recupera i pullman dato un orario di partenza
    @Query("SELECT * FROM Pullman p WHERE (p.orarioPartenza - :orarioPartenza) > 0")
    //Significa che l'orario di partenza sia maggiore di quello attuale, quindi il pullman deve ancora passare
    fun getPullmanByOrarioPartenza(orarioPartenza: Int): LiveData<List<Pullman>>?

    //Recupera i pullman data una destinazione
    @Query("SELECT * FROM Pullman p WHERE p.destinazione = :destinazione")
    fun getPullmanByDestinazione(destinazione: String): List<Pullman>?

    @Query("SELECT * FROM Pullman p WHERE (p.orarioPartenza - :orarioPartenza) > 0 AND p.destinazione = :destinazione ORDER BY p.orarioPartenza ASC")
    //Significa che l'orario di partenza sia maggiore di quello attuale, quindi il pullman deve ancora passare
    fun getPullmanByOrarioPartenzaEDestinazione(orarioPartenza: Int, destinazione: String): LiveData<List<Pullman>>?

}

