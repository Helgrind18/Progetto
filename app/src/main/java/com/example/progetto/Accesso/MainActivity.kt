package com.example.progetto.Accesso

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Corso
import com.example.progetto.Entity.CorsoDiLaurea
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Piatto
import com.example.progetto.Entity.RelazioneCDLCorso
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.progetto.Entity.Studente
import com.example.progetto.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //recupero i bottoni
        val bottoneLogin: Button = findViewById(R.id.bottoneLogin)
        val bottoneReg: Button = findViewById(R.id.bottoneReg)

        //vado alla schermata di login
        bottoneLogin.setOnClickListener {
            Log.d("MainActivityDEBUG", "Login button clicked")
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        //vado alla schermata di registrazione
        bottoneReg.setOnClickListener {
            val intent = Intent(this, RegistrazioneActivity::class.java)
            Log.d("MainActivityDEBUG", "Registrazione button clicked")
            startActivity(intent)
            finish()
        }

        //creazione di uno studente per test
        val dbViewModel = DBViewModel(application)
        val studenteTest = Studente(
            matricola = 15,
            cf = "CODFSC67890",
            pswd = "a",
            nome = "Giuseppe",
            cognome = "Bianchi",
            isee = 18000L,
            email = "giuseppe.bianchi@studenti.unical.it",
            annoImmatricolazione = 2022,
            tassa1 = false,
            tassa2 = false,
            tassa3 = false,
            tassa4 = false,
            36
        )

// Creazione della lista dei libri
        val libri = listOf(
            Libro(
                name = "Introduzione alla Psicologia",
                autore = "John Smith",
                settore = "Psicologia",
                matricolaStudente = studenteTest.matricola,
                sinossi = "Un libro che offre una panoramica generale sulla psicologia, esplorando le sue teorie, metodi e applicazioni nella vita quotidiana."
            ),
            Libro(
                name = "Teoria della Relatività",
                autore = "Albert Einstein",
                settore = "Fisica",
                matricolaStudente = studenteTest.matricola,
                sinossi = "Un'opera fondamentale che introduce la teoria della relatività, esplorando concetti come il tempo, lo spazio e la gravità."
            ),
            Libro(
                name = "Storia delle Idee Filosofiche",
                autore = "Sofia Lorenzi",
                settore = "Filosofia",
                matricolaStudente = studenteTest.matricola,
                sinossi = "Un viaggio attraverso la storia del pensiero filosofico, dalle origini della filosofia antica fino alle correnti moderne."
            ),
            Libro(
                name = "Il codice della mente straordinaria",
                autore = "Vittorino Andreoli",
                settore = "Psicologia",
                matricolaStudente = studenteTest.matricola,
                sinossi = "Un'opera che esplora i segreti della mente umana e come comprendere e migliorare le nostre capacità cognitive e emotive."
            ),
            Libro(
                name = "La psicologia del futuro",
                autore = "Giovanni Rossi",
                settore = "Psicologia",
                matricolaStudente = null,
                sinossi = "Un libro che anticipa le future tendenze nella psicologia, esplorando come la scienza psicologica evolverà con l'avanzamento della tecnologia."
            ),
            Libro(
                name = "Introduzione alla filosofia",
                autore = "Alessandro Verdi",
                settore = "Filosofia",
                matricolaStudente = null,
                sinossi = "Un'opera che introduce i principali concetti e autori della filosofia, esplorando le idee fondamentali che hanno plasmato il pensiero umano."
            )
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciStudente(studenteTest)
                    Log.d("MainActivityDEBUG", "Studenti inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento degli studenti", e)
                }
            }

            withContext(Dispatchers.IO) {
                try {
                    libri.forEach { libro ->
                        dbViewModel.aggiungiLibro(libro)
                    }
                    Log.d("MainActivityDEBUG", "Libri inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei libri", e)
                }
            }
        }

        // Creazione dei corsi

        val corso = listOf(
            Corso(id = 1, nome = "Ingegneria del Software", CFU = 12, semestre = 1, anno = 3, descrizione = "Il corso fornisce conoscenze sui principi, le metodologie e gli strumenti dell'ingegneria del software."),
            Corso(id = 2, nome = "Basi di Dati", CFU = 9, semestre = 2, anno = 3, descrizione = "Si studiano i fondamenti delle basi di dati, con particolare attenzione ai modelli relazionali e SQL."),
            Corso(id = 3, nome = "Reti di Calcolatori", CFU = 6, semestre = 1, anno = 3, descrizione = "Il corso introduce i principi delle reti di calcolatori, i protocolli di comunicazione e la sicurezza delle reti."),
            Corso(id = 4, nome = "Analisi Matematica 1", CFU = 9, semestre = 1, anno = 1, descrizione = "Studio delle funzioni reali, limiti, derivate, integrali e serie numeriche."),
            Corso(id = 5, nome = "Fisica 1", CFU = 6, semestre = 2, anno = 1, descrizione = "Fondamenti di meccanica classica, dinamica e termodinamica."),
            Corso(id = 6, nome = "Programmazione 1", CFU = 12, semestre = 1, anno = 1, descrizione = "Introduzione alla programmazione con focus su algoritmi e strutture dati di base."),
            Corso(id = 7, nome = "Sistemi Operativi", CFU = 9, semestre = 2, anno = 2, descrizione = "Approfondimento sui principi e le funzionalità dei sistemi operativi moderni."),
            Corso(id = 8, nome = "Ingegneria del Web", CFU = 6, semestre = 1, anno = 2, descrizione = "Tecnologie e metodologie per la progettazione e lo sviluppo di applicazioni web."),
            Corso(id = 9, nome = "Algoritmi e Strutture Dati", CFU = 12, semestre = 2, anno = 2, descrizione = "Studio degli algoritmi avanzati e delle strutture dati fondamentali."),
            Corso(id = 10, nome = "Calcolo Numerico", CFU = 6, semestre = 1, anno = 3, descrizione = "Tecniche numeriche per la risoluzione di problemi matematici e computazionali."),
            Corso(id = 11, nome = "Intelligenza Artificiale", CFU = 9, semestre = 2, anno = 3, descrizione = "Introduzione alle tecniche di AI, machine learning e reti neurali."),
            Corso(id = 12, nome = "Sicurezza Informatica", CFU = 6, semestre = 1, anno = 3, descrizione = "Principi di sicurezza informatica, crittografia e protezione dei sistemi."),
            Corso(id = 13, nome = "Economia Aziendale", CFU = 6, semestre = 2, anno = 1, descrizione = "Fondamenti di economia e gestione aziendale."),
            Corso(id = 14, nome = "Reti di Telecomunicazioni", CFU = 9, semestre = 1, anno = 2, descrizione = "Analisi delle reti di telecomunicazione e delle loro architetture."),
            Corso(id = 15, nome = "Progettazione Software", CFU = 9, semestre = 2, anno = 3, descrizione = "Metodologie e strumenti per la progettazione di sistemi software complessi."),
            Corso(id = 16, nome = "Sviluppo Mobile", CFU = 6, semestre = 1, anno = 3, descrizione = "Introduzione allo sviluppo di applicazioni mobili per Android e iOS."),
            Corso(id = 17, nome = "Elaborazione di Immagini", CFU = 6, semestre = 2, anno = 3, descrizione = "Tecniche di elaborazione e analisi delle immagini digitali."),
            Corso(id = 18, nome = "Matematica Discreta", CFU = 9, semestre = 1, anno = 1, descrizione = "Studio delle strutture discrete, teoria dei grafi e combinatoria.")
        )

        // Creazione delle relazioni Studente-Corso

        val relazioni = listOf(
            RelazioneStudenteCorso(1, 15, "Lunedì", "10:00", "A1", -1, 1, corso.first { it.id == 1 }.nome),
            RelazioneStudenteCorso(2, 15, "Martedì", "14:30", "B2", 30, 0, corso.first { it.id == 2 }.nome),
            RelazioneStudenteCorso(3, 15, "Mercoledì", "09:00", "C3", 18, 0, corso.first { it.id == 3 }.nome),
            RelazioneStudenteCorso(4, 15, "Martedì", "11:00", "B1", -1, 0, corso.first { it.id == 4 }.nome),
            RelazioneStudenteCorso(5, 15, "Martedì", "14:00", "B2", -1, 0, corso.first { it.id == 5 }.nome),
            RelazioneStudenteCorso(6, 15, "Mercoledì", "10:00", "C1", 30, 0, corso.first { it.id == 6 }.nome),
            RelazioneStudenteCorso(7, 15, "Mercoledì", "15:00", "C2", -1, 1, corso.first { it.id == 7 }.nome),
            RelazioneStudenteCorso(8, 15, "Giovedì", "09:00", "D1", -1, 1, corso.first { it.id == 8 }.nome),
            RelazioneStudenteCorso(9, 15, "Giovedì", "13:00", "D2", -1, 1, corso.first { it.id == 9 }.nome),
            RelazioneStudenteCorso(10, 15, "Venerdì", "10:00", "E1", 30, 0, corso.first { it.id == 10 }.nome),
            RelazioneStudenteCorso(11, 15, "Venerdì", "12:00", "E2", 18, 0, corso.first { it.id == 11 }.nome),
            RelazioneStudenteCorso(12, 15, "Lunedì", "14:00", "F1", 19, 0, corso.first { it.id == 12 }.nome),
            RelazioneStudenteCorso(13, 15, "Lunedì", "16:00", "F2", 20, 0, corso.first { it.id == 13 }.nome),
            RelazioneStudenteCorso(14, 15, "Martedì", "09:00", "G1", -1, 1, corso.first { it.id == 14 }.nome),
            RelazioneStudenteCorso(15, 15, "Martedì", "11:00", "G2", 30, 0, corso.first { it.id == 15 }.nome),
            RelazioneStudenteCorso(16, 15, "Mercoledì", "14:00", "H1", -1, 0, corso.first { it.id == 16 }.nome),
            RelazioneStudenteCorso(17, 15, "Mercoledì", "16:00", "H2", -1, 0, corso.first { it.id == 17 }.nome),
            RelazioneStudenteCorso(18, 15, "Giovedì", "10:00", "I1", -1, 0, corso.first { it.id == 18 }.nome)
        )


// Inserimento delle relazioni nel database usando forEach
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    corso.forEach { corso ->
                        dbViewModel.inserisciCorso(corso)
                    }
                    relazioni.forEach { relazione ->
                        dbViewModel.inserisciRelazioneStudenteCorso(relazione)
                    }
                    Log.d("MainActivityDEBUG", "Relazioni inserite correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento delle relazioni", e)
                }
            }
        }

        val cdl1 = CorsoDiLaurea(1, "Ingegneria Informatica")
        val cdl2 = CorsoDiLaurea(2, "Ingegneria Meccanica")
        val cdl3 = CorsoDiLaurea(3, "Ingegneria Electronica")

        //Li inserisco
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciCorsoDiLaurea(cdl1)
                    dbViewModel.inserisciCorsoDiLaurea(cdl2)
                    dbViewModel.inserisciCorsoDiLaurea(cdl3)
                    Log.d("MainActivityDEBUG", "Relazioni cdlCorso inserite correttamente")

                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento delle relazioni", e)
                }
            }
        }

        // Creazione delle relazioni CDL-Corsi (invertiti)
        val corsi = listOf(
            RelazioneCDLCorso(1, 1),
            RelazioneCDLCorso(2, 1),
            RelazioneCDLCorso(3, 1),
            RelazioneCDLCorso(6, 1),
            RelazioneCDLCorso(9, 1),

            RelazioneCDLCorso(4, 2),
            RelazioneCDLCorso(5, 2),
            RelazioneCDLCorso(7, 2),
            RelazioneCDLCorso(8, 2),
            RelazioneCDLCorso(14, 2),

            RelazioneCDLCorso(10, 3),
            RelazioneCDLCorso(11, 3),
            RelazioneCDLCorso(12, 3),
            RelazioneCDLCorso(15, 3),
            RelazioneCDLCorso(16, 3),
            RelazioneCDLCorso(17, 3)
        )

        // Iterazione per inserire nel database
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    corsi.forEach { corso ->
                        dbViewModel.inserisciRelazioneCDLCorso(corso)
                    }
                    Log.d("MainActivityDEBUG", "Relazioni CDL-Corsi inserite correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento delle relazioni CDL-Corso", e)
                }
            }
        }

        val piatti = listOf(
            Piatto(id = 1, tipo = 3, nome = "Bruschetta al pomodoro"),
            Piatto(id = 2, tipo = 3, nome = "Insalata caprese"),
            Piatto(id = 3, tipo = 1, nome = "Pasta aglio, olio e peperoncino"),
            Piatto(id = 4, tipo = 1, nome = "Lasagna alla bolognese"),
            Piatto(id = 5, tipo = 1, nome = "Risotto ai funghi"),
            Piatto(id = 6, tipo = 2, nome = "Ossobuco con polenta"),
            Piatto(id = 7, tipo = 2, nome = "Filetto di manzo al pepe verde"),
            Piatto(id = 8, tipo = 2, nome = "Branzino al forno con patate"),
            Piatto(id = 9, tipo = 3, nome = "Tiramisù"),
            Piatto(id = 10, tipo = 3, nome = "Panna cotta al caramello"),
            Piatto(id = 11, tipo = 2, nome = "Frittata di zucchine"),
            Piatto(id = 12, tipo = 1, nome = "Panino con prosciutto e formaggio"),
            Piatto(id = 13, tipo = 1, nome = "Zuppa di pomodoro"),
            Piatto(id = 14, tipo = 1, nome = "Insalata di riso"),
            Piatto(id = 15, tipo = 2, nome = "Toast al prosciutto e formaggio"),
            Piatto(id = 16, tipo = 1, nome = "Gnocchi al pesto"),
            Piatto(id = 17, tipo = 1, nome = "Spaghetti alla carbonara"),
            Piatto(id = 18, tipo = 2, nome = "Pollo alla cacciatora"),
            Piatto(id = 19, tipo = 2, nome = "Melanzane alla parmigiana"),
            Piatto(id = 20, tipo = 2, nome = "Baccalà alla vicentina"),
            Piatto(id = 21, tipo = 1, nome = "Pasta alla norma"),
            Piatto(id = 22, tipo = 2, nome = "Fegato alla veneziana"),
            Piatto(id = 23, tipo = 2, nome = "Arrosto di vitello"),
            Piatto(id = 24, tipo = 2, nome = "Tagliata di manzo con rucola e grana"),
            Piatto(id = 25, tipo = 1, nome = "Risotto allo zafferano"),
            Piatto(id = 26, tipo = 2, nome = "Anatra all'arancia"),
            Piatto(id = 27, tipo = 3, nome = "Zuppa inglese"),
            Piatto(id = 28, tipo = 3, nome = "Cassata siciliana"),
            Piatto(id = 29, tipo = 3, nome = "Strudel di mele"),
            Piatto(id = 30, tipo = 3, nome = "Cantucci con vin santo")
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    piatti.forEach { piatto ->
                        dbViewModel.inserisciPiatto(piatto)
                    }
                    Log.d("MainActivityDEBUG", "Piatti inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei piatti", e)
                }
            }
        }

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    // Esegui la query per ottenere i piatti di un tipo specifico
                    val tipo = 1 // Sostituisci con il tipo che desideri cercare
                    val piattii = dbViewModel.getPiattiByTipo(tipo)

                    // Esegui la query per ottenere un piatto tramite id
                    val piattoId = 1 // Sostituisci con l'ID che desideri cercare
                    val piattoo = dbViewModel.getPiattoById(piattoId)

                    // Esegui la query per ottenere la media ponderata di uno studente
                    val mediaPonderata = dbViewModel.getMediaPonderata(studenteTest.matricola)

                    // Log per verificare i risultati
                    Log.d("MainActivityDEBUGTEST", "Piatti di tipo '$tipo': $piattii")
                    Log.d("MainActivityDEBUGTEST", "Piatto con ID $piattoId: $piattoo")
                    Log.d("MainActivityDEBUGTEST", "Media ponderata per matricola \n$studenteTest: $mediaPonderata")

                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'esecuzione delle query", e)
                }
            }
        }


    }
}
