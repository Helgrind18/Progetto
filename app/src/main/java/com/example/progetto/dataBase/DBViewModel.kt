package com.example.progetto.dataBase

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.progetto.Entity.Schemi.Aula
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.CorsoDiLaurea
import com.example.progetto.Entity.Schemi.Libro
import com.example.progetto.Entity.Schemi.Piatto
import com.example.progetto.Entity.Schemi.Pullman
import com.example.progetto.Entity.Relazioni.RelazioneCDLCorso
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Studente
import kotlinx.coroutines.launch

//Collega i dati (DAO/Database) con l’interfaccia utente. Contiene tutta la logica per interagire con il db
class DBViewModel(application: Application) : AndroidViewModel(application) {


    private val studenteDAO = DataBaseApp.getDatabase(application).getStudenteDao()
    private val libroDAO = DataBaseApp.getDatabase(application).getLibroDao()
    private val aulaDAO = DataBaseApp.getDatabase(application).getAulaDao()
    private val corsoDAO = DataBaseApp.getDatabase(application).getCorsoDao()
    private val relazioneStudenteCorsoDAO =
        DataBaseApp.getDatabase(application).getRelazioneStudenteCorsoDao()
    private val relazioneCDLCorsoDAO =
        DataBaseApp.getDatabase(application).getRelazioneCDLCorsoDao()
    private val corsoDiLaureaDAO = DataBaseApp.getDatabase(application).getCDLDao()
    private val piattoDAO = DataBaseApp.getDatabase(application).getPiattoDao()
    private val pullmanDAO = DataBaseApp.getDatabase(application).getPullmanDao()
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

    fun getPastiEffettuati(matricola: Int): Int? {
        return studenteDAO.getPastiEffettuati(matricola)
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

    fun aggiungiLibro(name: String, autore: String, settore: String,matricolaStudente: Int?, sinossi: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val libro = Libro(
                name = name, autore = autore, settore = settore,
                matricolaStudente = matricolaStudente,
                sinossi = sinossi
            )
            libroDAO.inserisciLibro(libro)
        }
    }

    fun getLibroByNomeAutore(nome: String, autore: String): Libro? {
        return libroDAO.getLibroByNomeAutore(nome, autore)
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

    fun getCorsoById(id: Int): Corso? {
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
        try {
            relazioneStudenteCorsoDAO.inserisciRelazione(relazione)
            Log.d("DBViewModelDEBUGRel", "✅ Relazione ${relazione.matricola} ${relazione.corsoId} inserita con successo")
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUGRel", "❌ Errore durante l'inserimento della relazione", e)
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

    fun getCorsiSeguitiDaStudenteInUnGiorno(giorno: String): List<String>? {
        return try {
            relazioneStudenteCorsoDAO.getCorsiSeguitiDaStudenteInUnGiorno(giorno)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

    fun getEsamiDiStudenteLD(matricola: Int): LiveData<List<RelazioneStudenteCorso>>? {
        return try {
            relazioneStudenteCorsoDAO.getEsamiDiStudenteLD(matricola)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            null
        }
    }

        fun getEsamiDiStudente(matricola: Int): List<RelazioneStudenteCorso>? {
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

        fun getEsamiPrenotabili(
            matricola: Int,
            anno: Int
        ): LiveData<List<RelazioneStudenteCorso>>? {
            return try {
                relazioneStudenteCorsoDAO.getEsamiPrenotabili(matricola, anno)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        fun getEsamiPrenotati(matricola: Int, anno: Int): LiveData<List<RelazioneStudenteCorso>>? {
            return try {
                relazioneStudenteCorsoDAO.getEsamiPrenotati(matricola, anno)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        fun getMedia(matricola: Int): Double? {
            return try {
                relazioneStudenteCorsoDAO.getMedia(matricola)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        fun getMediaPonderata(matricola: Int): Double? {
            return try {
                relazioneStudenteCorsoDAO.getMediaPonderata(matricola)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        fun getLezioni(giorno: Int, matricola: Int, anno: Int, semestre: Int): LiveData<List<RelazioneStudenteCorso>>? {
            return try {
                relazioneStudenteCorsoDAO.getLezioni(giorno, matricola, anno, semestre)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        //CDL

        fun getAll(): LiveData<List<CorsoDiLaurea>> = corsoDiLaureaDAO.getAll()

        fun inserisciCorsoDiLaurea(corsoDiLaurea: CorsoDiLaurea) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    corsoDiLaureaDAO.inserisciCorsoDiLaurea(corsoDiLaurea)
                    Log.d("DBViewModelDEBUG", "Corso inserito nel database")
                } catch (e: Exception) {
                    Log.e("DBViewModelDEBUG", "Errore durante l'inserimento del corso", e)
                }
            }
        }

        fun rimuoviCorsoDiLaurea(corsoDiLaurea: CorsoDiLaurea) {
            viewModelScope.launch(Dispatchers.IO) {
                corsoDiLaureaDAO.rimuoviCorsoDiLaurea(corsoDiLaurea)
            }
        }

        fun getCDLById(id: Int): LiveData<CorsoDiLaurea>? {
            return try {
                corsoDiLaureaDAO.getCDLById(id)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        //// RELAZIONE CDL - CORSO

        fun inserisciRelazioneCDLCorso(relazione: RelazioneCDLCorso) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    relazioneCDLCorsoDAO.inserisciRelazione(relazione)
                    Log.d("DBViewModelDEBUG", "inserisciRelazioneCDLCorso inserito nel database")
                } catch (e: Exception) {
                    Log.e(
                        "DBViewModelDEBUG",
                        "Errore durante l'inserimento inserisciRelazioneCDLCorso",
                        e
                    )
                }
            }
        }

        fun rimuoviRelazioneCDLCorso(relazione: RelazioneCDLCorso) {
            viewModelScope.launch {
                try {
                    relazioneCDLCorsoDAO.rimuoviRelazione(relazione)
                } catch (e: Exception) {
                    Log.e(
                        "DBViewModelDEBUG",
                        "Errore durante l'eliminazione rimuoviRelazioneCDLCorso",
                        e
                    )
                }
            }
        }

        fun getCorsiDiCDL(cdl: String): List<Corso>? {
            return try {
                relazioneCDLCorsoDAO.getCorsiDiCDL(cdl)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

//// PIATTO

        fun inserisciPiatto(piatto: Piatto) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    piattoDAO.inserisciPiatto(piatto)
                    Log.d("DBViewModelDEBUG", "Piatto inserito nel database")
                } catch (e: Exception) {
                    Log.e("DBViewModelDEBUG", "Errore durante l'inserimento del piatto", e)
                }
            }
        }

        fun eliminaPiatto(piatto: Piatto) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    piattoDAO.rimuoviPiatto(piatto)
                } catch (e: Exception) {
                    Log.e("DBViewModelDEBUG", "Errore durante l'eliminazione dello studente", e)
                }
            }
        }

        fun getPiatti(): LiveData<List<Piatto>> {
            return piattoDAO.getAll()
        }

        fun getPiattiByTipo(tipo: Int): List<Piatto>? {
            return try {
                piattoDAO.getPiattiByTipo(tipo)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }

        fun getPiattoById(id: Int): Piatto? {
            return try {
                piattoDAO.getPiattoById(id)
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante la query", e)
                null
            }
        }
    //Pullman
    fun inserisciPullman(pullman: Pullman) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                pullmanDAO.inserisciPullman(pullman)
                Log.d("DBViewModelDEBUG", "Pullman ${pullman.id} inserito nel database")
            } catch (e: Exception) {
                Log.e("DBViewModelDEBUG", "Errore durante l'inserimento del pullman", e)
            }
        }
    }

    fun getPullmanById(id: Int): Pullman? {
        return try {
            pullmanDAO.getPullmanById(id)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante la query", e)
            throw e
        }
    }

    fun getPullmanByOrarioPartenza(orarioPartenza: Int): LiveData<List<Pullman>>? {
        try {
            Log.d("DBViewModelDEBUG", "Eseguo query con orario di partenza: $orarioPartenza")
            return pullmanDAO.getPullmanByOrarioPartenza(orarioPartenza)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante l'esecuzione della query", e)
        }
        return null
    }

    fun getPullmanByDestinazione(destinazione: String): List<Pullman>?{
        try {
            Log.d("DBViewModelDEBUG", "Eseguo query con destinazione: $destinazione")
            return pullmanDAO.getPullmanByDestinazione(destinazione)
            } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante l'esecuzione della query", e)
        }
        return null
    }

    fun getPullmanByOrarioPartenzaEDestinazione(orarioPartenza: Int, destinazione: String): LiveData<List<Pullman>>?{
        try {
            Log.d("DBViewModelDEBUG", "Eseguo query con orario di partenza: $orarioPartenza e destinazione: $destinazione")
            return pullmanDAO.getPullmanByOrarioPartenzaEDestinazione(orarioPartenza, destinazione)
        } catch (e: Exception) {
            Log.e("DBViewModelDEBUG", "Errore durante l'esecuzione della query", e)
        }
        return null
    }

}





