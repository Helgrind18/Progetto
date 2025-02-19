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
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.Libro
import com.example.progetto.Entity.Schemi.Piatto
import com.example.progetto.Entity.Schemi.Pullman
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.progetto.Entity.Schemi.Studente
import com.example.progetto.R
import kotlinx.coroutines.delay

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
        }
        //vado alla schermata di registrazione
        bottoneReg.setOnClickListener {
            val intent = Intent(this, RegistrazioneActivity::class.java)
            Log.d("MainActivityDEBUG", "Registrazione button clicked")
            startActivity(intent)
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
            Corso(
                id = 1,
                nome = "Ingegneria del Software",
                CFU = 12,
                semestre = 1,
                anno = 3,
                descrizione = "Il corso fornisce conoscenze sui principi, le metodologie e gli strumenti dell'ingegneria del software."
            ),
            Corso(
                id = 2,
                nome = "Basi di Dati",
                CFU = 9,
                semestre = 2,
                anno = 3,
                descrizione = "Si studiano i fondamenti delle basi di dati, con particolare attenzione ai modelli relazionali e SQL."
            ),
            Corso(
                id = 3,
                nome = "Reti di Calcolatori",
                CFU = 6,
                semestre = 1,
                anno = 3,
                descrizione = "Il corso introduce i principi delle reti di calcolatori, i protocolli di comunicazione e la sicurezza delle reti."
            ),
            Corso(
                id = 4,
                nome = "Analisi Matematica 1",
                CFU = 9,
                semestre = 1,
                anno = 1,
                descrizione = "Studio delle funzioni reali, limiti, derivate, integrali e serie numeriche."
            ),
            Corso(
                id = 5,
                nome = "Fisica 1",
                CFU = 6,
                semestre = 2,
                anno = 1,
                descrizione = "Fondamenti di meccanica classica, dinamica e termodinamica."
            ),
            Corso(
                id = 6,
                nome = "Programmazione 1",
                CFU = 12,
                semestre = 1,
                anno = 1,
                descrizione = "Introduzione alla programmazione con focus su algoritmi e strutture dati di base."
            ),
            Corso(
                id = 7,
                nome = "Sistemi Operativi",
                CFU = 9,
                semestre = 2,
                anno = 2,
                descrizione = "Approfondimento sui principi e le funzionalità dei sistemi operativi moderni."
            ),
            Corso(
                id = 8,
                nome = "Ingegneria del Web",
                CFU = 6,
                semestre = 1,
                anno = 2,
                descrizione = "Tecnologie e metodologie per la progettazione e lo sviluppo di applicazioni web."
            ),
            Corso(
                id = 9,
                nome = "Algoritmi e Strutture Dati",
                CFU = 12,
                semestre = 2,
                anno = 2,
                descrizione = "Studio degli algoritmi avanzati e delle strutture dati fondamentali."
            ),
            Corso(
                id = 10,
                nome = "Calcolo Numerico",
                CFU = 6,
                semestre = 1,
                anno = 3,
                descrizione = "Tecniche numeriche per la risoluzione di problemi matematici e computazionali."
            ),
            Corso(
                id = 11,
                nome = "Intelligenza Artificiale",
                CFU = 9,
                semestre = 2,
                anno = 3,
                descrizione = "Introduzione alle tecniche di AI, machine learning e reti neurali."
            ),
            Corso(
                id = 12,
                nome = "Sicurezza Informatica",
                CFU = 6,
                semestre = 1,
                anno = 3,
                descrizione = "Principi di sicurezza informatica, crittografia e protezione dei sistemi."
            ),
            Corso(
                id = 13,
                nome = "Economia Aziendale",
                CFU = 6,
                semestre = 2,
                anno = 1,
                descrizione = "Fondamenti di economia e gestione aziendale."
            ),
            Corso(
                id = 14,
                nome = "Reti di Telecomunicazioni",
                CFU = 9,
                semestre = 1,
                anno = 2,
                descrizione = "Analisi delle reti di telecomunicazione e delle loro architetture."
            ),
            Corso(
                id = 15,
                nome = "Progettazione Software",
                CFU = 9,
                semestre = 1,
                anno = 3,
                descrizione = "Metodologie e strumenti per la progettazione di sistemi software complessi."
            ),
            Corso(
                id = 16,
                nome = "Sviluppo Mobile",
                CFU = 6,
                semestre = 1,
                anno = 3,
                descrizione = "Introduzione allo sviluppo di applicazioni mobili per Android e iOS."
            ),
            Corso(
                id = 17,
                nome = "Elaborazione di Immagini",
                CFU = 6,
                semestre = 2,
                anno = 3,
                descrizione = "Tecniche di elaborazione e analisi delle immagini digitali."
            ),
            Corso(
                id = 18,
                nome = "Matematica Discreta",
                CFU = 9,
                semestre = 1,
                anno = 1,
                descrizione = "Studio delle strutture discrete, teoria dei grafi e combinatoria."
            )
        )

        val relazioni = listOf(
            RelazioneStudenteCorso(1, 15, Calendar.MONDAY, "10:00", "A1", -1, 1, corso.first { it.id == 1 }.nome),
            RelazioneStudenteCorso(2, 15, Calendar.TUESDAY, "14:30", "B2", 30, 0, corso.first { it.id == 2 }.nome),
            RelazioneStudenteCorso(3, 15, Calendar.WEDNESDAY, "09:00", "C3", 29, 0, corso.first { it.id == 3 }.nome),
            RelazioneStudenteCorso(4, 15, Calendar.TUESDAY, "11:00", "B1", -1, 0, corso.first { it.id == 4 }.nome),
            RelazioneStudenteCorso(5, 15, Calendar.TUESDAY, "14:00", "B2", -1, 0, corso.first { it.id == 5 }.nome),
            RelazioneStudenteCorso(6, 15, Calendar.WEDNESDAY, "10:00", "C1", 28, 0, corso.first { it.id == 6 }.nome),
            RelazioneStudenteCorso(7, 15, Calendar.WEDNESDAY, "15:00", "C2", -1, 1, corso.first { it.id == 7 }.nome),
            RelazioneStudenteCorso(8, 15, Calendar.MONDAY, "09:00", "D1", -1, 1, corso.first { it.id == 8 }.nome),
            RelazioneStudenteCorso(9, 15, Calendar.THURSDAY, "13:00", "D2", -1, 1, corso.first { it.id == 9 }.nome),
            RelazioneStudenteCorso(10, 15, Calendar.FRIDAY, "10:00", "E1", 27, 0, corso.first { it.id == 10 }.nome),
            RelazioneStudenteCorso(11, 15, Calendar.FRIDAY, "12:00", "E2", 26, 0, corso.first { it.id == 11 }.nome),
            RelazioneStudenteCorso(12, 15, Calendar.MONDAY, "14:00", "F1", 25, 0, corso.first { it.id == 12 }.nome),
            RelazioneStudenteCorso(13, 15, Calendar.MONDAY, "16:00", "F2", 24, 0, corso.first { it.id == 13 }.nome),
            RelazioneStudenteCorso(14, 15, Calendar.MONDAY, "09:00", "G1", -1, 1, corso.first { it.id == 14 }.nome),
            RelazioneStudenteCorso(15, 15, Calendar.TUESDAY, "11:00", "G2", 23, 0, corso.first { it.id == 15 }.nome),
            RelazioneStudenteCorso(16, 15, Calendar.WEDNESDAY, "14:00", "H1", -1, 0, corso.first { it.id == 16 }.nome),
            RelazioneStudenteCorso(17, 15, Calendar.WEDNESDAY, "16:00", "H2", -1, 0, corso.first { it.id == 17 }.nome),
            RelazioneStudenteCorso(18, 15, Calendar.THURSDAY, "10:00", "I1", -1, 0, corso.first { it.id == 18 }.nome)
        )
        //Inserimento delle relazioni
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Inserisco i corsi
                corso.forEach { dbViewModel.inserisciCorso(it) }

                // Inserisco tutte le relazioni una per una, aspettando il completamento, al fine di evitare evenutali errori di confliitto col db
                relazioni.forEach { relazione ->
                    delay(10)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione) }

                Log.d("DEBUG", "Inserimento completato, aspetto la conferma del DB...")

                delay(500) // (facoltativo, aiuta Room a scrivere le modifiche)

                val lez = dbViewModel.getLezioni(Calendar.DAY_OF_WEEK, 15, 1, 1)
                Log.d("MainActivityDEBUGLez", "Lezioni trovate: $lez")

            } catch (e: Exception) {
                Log.e("MainActivityDEBUG", "Errore durante l'inserimento/recupero", e)
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


        val pullmanList = listOf(
            Pullman(id = 1, nomePullman = "Express 101", orarioPartenza = 800, destinazione = "ARCAVACATA"),
            Pullman(id = 2, nomePullman = "City Link", orarioPartenza = 930, destinazione = "CENTRO STORICO"),
            Pullman(id = 3, nomePullman = "Fast Travel", orarioPartenza = 1100, destinazione = "QUATTROMIGLIA"),
            Pullman(id = 4, nomePullman = "Night Rider", orarioPartenza = 2200, destinazione = "SANTO STEFANO"),
            Pullman(id = 5, nomePullman = "Morning Star", orarioPartenza = 600, destinazione = "COMMENDA"),
            Pullman(id = 6, nomePullman = "Weekend Express", orarioPartenza = 1400, destinazione = "SURDO"),
            Pullman(id = 7, nomePullman = "Sunset Trip", orarioPartenza = 1800, destinazione = "ROGES"),
            Pullman(id = 8, nomePullman = "Mountain Express", orarioPartenza = 700, destinazione = "SAPORITO"),
            Pullman(id = 9, nomePullman = "Sea Breeze", orarioPartenza = 1600, destinazione = "ARCAVACATA"),
            Pullman(id = 10, nomePullman = "Holiday Coach", orarioPartenza = 1200, destinazione = "CENTRO STORICO"),
            Pullman(id = 11, nomePullman = "Golden Route", orarioPartenza = 1000, destinazione = "QUATTROMIGLIA"),
            Pullman(id = 12, nomePullman = "Silver Line", orarioPartenza = 1300, destinazione = "SANTO STEFANO"),
            Pullman(id = 13, nomePullman = "Blue Sky", orarioPartenza = 1500, destinazione = "COMMENDA"),
            Pullman(id = 14, nomePullman = "Emerald Voyage", orarioPartenza = 1700, destinazione = "SURDO"),
            Pullman(id = 15, nomePullman = "Ruby Express", orarioPartenza = 1900, destinazione = "ROGES"),
            Pullman(id = 16, nomePullman = "Diamond Travel", orarioPartenza = 2100, destinazione = "SAPORITO"),
            Pullman(id = 17, nomePullman = "Pearl Bus", orarioPartenza = 500, destinazione = "ARCAVACATA"),
            Pullman(id = 18, nomePullman = "Opal Route", orarioPartenza = 1130, destinazione = "CENTRO STORICO"),
            Pullman(id = 19, nomePullman = "Sapphire Ride", orarioPartenza = 1330, destinazione = "QUATTROMIGLIA"),
            Pullman(id = 20, nomePullman = "Amber Path", orarioPartenza = 1530, destinazione = "SANTO STEFANO"),
            Pullman(id = 21, nomePullman = "Turquoise Express", orarioPartenza = 1730, destinazione = "COMMENDA"),
            Pullman(id = 22, nomePullman = "Coral Journey", orarioPartenza = 1930, destinazione = "SURDO"),
            Pullman(id = 23, nomePullman = "Onyx Adventure", orarioPartenza = 2130, destinazione = "ROGES"),
            Pullman(id = 24, nomePullman = "Jet Express", orarioPartenza = 2330, destinazione = "SAPORITO")
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    pullmanList.forEach { pullman ->
                        Log.d("MainActivityDEBUGPul","Inserisco il pullman $pullman")
                        dbViewModel.inserisciPullman(pullman)
                    }
                    Log.d("MainActivityDEBUGPul", "Pullman inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUGPul", "Errore durante l'inserimento dei pullman", e)
                }
            }

        }

    }
}
