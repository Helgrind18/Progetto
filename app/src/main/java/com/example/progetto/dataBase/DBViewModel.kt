package com.example.progetto.dataBase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Corso
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.Entity.RelazioneStudenteSegueCorsi
import com.example.progetto.Entity.Studente
import kotlinx.coroutines.launch

//Collega i dati (DAO/Database) con l’interfaccia utente. Contiene tutta la logica per interagire con il db
class DBViewModel(application: Application) : AndroidViewModel(application) {


    private val studenteDAO = DataBaseApp.getDatabase(application).getStudenteDao()
    private val libroDAO = DataBaseApp.getDatabase(application).getLibroDao()
    private val aulaDAO = DataBaseApp.getDatabase(application).getAulaDao()
    private val corsoDAO = DataBaseApp.getDatabase(application).getCorsoDao()
    private val relazioneStudenteCorsoDAO =
        DataBaseApp.getDatabase(application).getRelazioneStudenteCorsoDao()


    //STUDENTE

    //Funzione per restituire tutti gli studenti
    fun getAllStudenti(): LiveData<List<Studente>> {
        return studenteDAO.getAll()
    }

    //Funzione per inserire un nuovo studente
    fun inserisciStudente(studente: Studente) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                studenteDAO.inserisciStudente(studente)
                Log.d("DBViewModelDEBUG", "Studente inserito nel database")
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante l'inserimento dello studente", e)
            }
        }
    }


    //Funzione per prendere uno studente tramite la matricola
    fun studenteByMatricola(matricola: Int): Studente? {
        try {
            Log.d("DBViewModelDEBUG", "Eseguo query con matricola: $matricola")
            return studenteDAO.getStudenteByMatricola(matricola) // Esegui la query
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante l'esecuzione della query", e)
        }
        return null
    }

    fun eliminaStudente(matricola: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                studenteDAO.rimuoviStudenteByMatricola(matricola)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante l'eliminazione dello studente", e)
            }
        }
    }


    /////////////// LIBRO //////////////////////////

    fun getLibriBySettore(settore: String): LiveData<List<Libro>>? {
        return try {
            libroDAO.getLibriBySettore(settore)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun aggiungiLibro(libro: Libro) {
        viewModelScope.launch(Dispatchers.IO) {
            libroDAO.inserisciLibro(libro)
        }
    }

    fun aggiungiLibro(name: String, autore: String, settore: String, iSBN: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val libro = Libro(name = name, autore = autore, settore = settore, iSBN = iSBN)
            libroDAO.inserisciLibro(libro)
        }
    }


    fun eliminaLibro(iSBN: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            libroDAO.rimuoviLibroByISBN(iSBN)
        }
    }

    fun getLibriByStudente(matricola: Int): LiveData<List<Libro>> {
        return libroDAO.getLibriByStudente(matricola)
    }

    /////////////// CORSO //////////////////////////
    // CORSO

    fun getAllCorsi(): LiveData<List<Corso>> = corsoDAO.getAll()

    fun inserisciCorso(corso: Corso) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                corsoDAO.inserisciCorso(corso)
                Log.d("DBViewModelDEBUG", "Corso inserito nel database")
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante l'inserimento del corso", e)
            }
        }
    }

    fun eliminaCorso(corso: Corso) {
        viewModelScope.launch(Dispatchers.IO) {
            corsoDAO.rimuoviCorso(corso)
        }
    }

    fun getCorsoById(id: Int): LiveData<Corso>? {
        return try {
            corsoDAO.getCorsoById(id)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    // AULA
    fun inserisciAula(aula: Aula) {
        viewModelScope.launch(Dispatchers.IO) {
            aulaDAO.inserisciAula(aula)
        }
    }

    fun eliminaAula(cubo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            aulaDAO.rimuoviAula(cubo)
        }
    }

    // RELAZIONE STUDENTE-CORSO

    fun inserisciRelazioneStudenteCorso(relazione: RelazioneStudenteCorso) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                relazioneStudenteCorsoDAO.inserisciRelazione(relazione)
                Log.d("DBViewModelDEBUG", "Relazione inserita con successo")
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante l'inserimento della relazione", e)
            }
        }
    }

    fun rimuoviRelazioneStudenteCorso(relazione: RelazioneStudenteCorso) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                relazioneStudenteCorsoDAO.rimuoviRelazione(relazione)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la rimozione della relazione", e)
            }
        }
    }

    fun getCorsiSeguitiDaStudente(matricola: Int): List<Int>? {
        return try {
            relazioneStudenteCorsoDAO.getCorsiDiStudente(matricola)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }
/*
    fun getStudentiDiCorso(corsoId: Int): List<Int>? {
        return try {
            relazioneStudenteCorsoDAO.getStudentiDiCorso(corsoId)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }
*/
    fun getCorsiSeguitiDaStudenteInUnGiorno(giorno: String): List<String>? {
        return try {
            relazioneStudenteCorsoDAO.getCorsiSeguitiDaStudenteInUnGiorno(giorno)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiDiStudente(matricola: Int): LiveData<List<Corso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiDiStudente(matricola)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiDaStudente(matricola: Int): LiveData<List<Corso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiDaFareStudente(matricola)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiDaFareDiUnAnno(matricola: Int, anno: Int): LiveData<List<Corso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiDaFareDiUnAnno(matricola, anno)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiDaFareCompatibili(matricola: Int, anno: Int): LiveData<List<Corso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiDaFareCompatibili(matricola, anno)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiPrenotabili(matricola: Int, anno: Int): LiveData<List<Corso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiPrenotabili(matricola, anno)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiPrenotati(matricola: Int, anno: Int): LiveData<List<Corso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiPrenotati(matricola, anno)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

}


