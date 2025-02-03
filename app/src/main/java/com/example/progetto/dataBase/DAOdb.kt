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
import com.example.progetto.Entity.CorsoDiLaurea
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Piatto
import com.example.progetto.Entity.RelazioneCDLCorso
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
    fun getAll(): LiveData<List<Studente>>

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
    fun getCorsoById(id: Int): Corso?

}

@Dao
interface CorsoDiLaureaDao {

    @Query("SELECT * FROM CorsoDiLaurea")
    fun getAll(): LiveData<List<CorsoDiLaurea>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciCorsoDiLaurea(corsoDiLaurea: CorsoDiLaurea)

    @Delete
    fun rimuoviCorsoDiLaurea(corsoDiLaurea: CorsoDiLaurea)

    //Recupera il corso dall'id
    @Query("SELECT * FROM CorsoDiLaurea c WHERE c.corsoLaureaId = :id")
    fun getCDLById(id: Int): LiveData<CorsoDiLaurea>?

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

}

@Dao
interface RelazioneCDLCorsoDao {
    // Inserisce una relazione tra un corso e un CDL
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserisciRelazione(relazione: RelazioneCDLCorso)

    //Rimuove la relazione
    @Transaction
    @Delete
    fun rimuoviRelazione(relazione: RelazioneCDLCorso)

    //Recupera tutti i corsi dato un CDL
    @Transaction
    @Query("SELECT DISTINCT c.* FROM RelazioneCDLCorso r, Corso c WHERE r.corsoLaureaId = :cdl " +
            "AND r.corsoId = c.corsoId")
    fun getCorsiDiCDL(cdl: String): List<Corso>?

    //Recupera i CDL dato un corso
    @Transaction
    @Query("SELECT DISTINCT c.* FROM RelazioneCDLCorso r, CorsoDiLaurea c WHERE r.corsoId = :corso " +
            "AND r.corsoLaureaId = c.corsoLaureaId")
    fun getCDLDiCorso(corso: Int): List<CorsoDiLaurea>?
}

@Dao
interface PiattoDao{
    @Query("SELECT * FROM Piatto")
    fun getAll(): LiveData<List<Piatto>>

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

