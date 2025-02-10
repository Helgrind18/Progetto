package com.example.progetto.Accesso

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Corso
import com.example.progetto.Entity.Schemi.Studente

import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrazioneActivity : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrazione)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.d("RegistrazioneActivity", "onCreate called")

        // Recupero i dati dall'activity
        val matricolaEditText: EditText = findViewById(R.id.editMatricola)
        val cfEditText: EditText = findViewById(R.id.editCF)
        val pswdEditText: EditText = findViewById(R.id.editPswd)
        val nomeEditText: EditText = findViewById(R.id.editNome)
        val cognomeEditText: EditText = findViewById(R.id.editCognome)
        val iseeEditText: EditText = findViewById(R.id.editISEE)
        val emailEditText: EditText = findViewById(R.id.editEmail)
        val bottoneInvia: Button = findViewById(R.id.bottoneInvia)

        // Inizializza il ViewModel
        dbViewModel = DBViewModel(application)
        val sceltaCDL: TextView = findViewById(R.id.scegliCDL)
        val lista: RecyclerView = findViewById(R.id.listaCDL)
       /* cdladapter = CDLAdapter()
        lista.layoutManager = LinearLayoutManager(this as Context?)
        lista.adapter = cdladapter
        dbViewModel= ViewModelProvider(this as ViewModelStoreOwner).get(DBViewModel::class.java)
        dbViewModel.getAllCDL()?.observe(this as LifecycleOwner, Observer { corsi ->
            cdladapter.submitList(corsi)
        })
        Log.d("RegBug", "Lista corsi caricata ${cdladapter.itemCount}")
        sceltaCDL.setOnClickListener {
            if (lista.visibility == View.GONE) {
                lista.visibility = View.VISIBLE
            } else {
                lista.visibility = View.GONE
            }
        }*/

        bottoneInvia.setOnClickListener {
            if (campiVuoti(
                    matricolaEditText,
                    cfEditText,
                    pswdEditText,
                    nomeEditText,
                    cognomeEditText,
                    iseeEditText,
                    emailEditText
                )
            ) {
                Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show()
            } else {
                val matricola: Int = matricolaEditText.text.toString().toInt()
                val cf: String = cfEditText.text.toString().trim()
                val pswd: String = pswdEditText.text.toString().trim()
                val nome: String = nomeEditText.text.toString().trim()
                val cognome: String = cognomeEditText.text.toString().trim()
                val isee: Long = iseeEditText.text.toString().toLong()
                val email: String = emailEditText.text.toString().trim()

                val studente = Studente(
                    matricola,
                    cf,
                    pswd,
                    nome,
                    cognome,
                    isee,
                    email,
                    0,
                    false,
                    false,
                    false,
                    false,
                    0
                )
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
                        semestre = 2,
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
                    RelazioneStudenteCorso(1, matricola, Calendar.MONDAY, "10:00", "A1", -1, 1, corso.first { it.id == 1 }.nome),
                    RelazioneStudenteCorso(2, matricola, Calendar.TUESDAY, "14:30", "B2", -1, 0, corso.first { it.id == 2 }.nome),
                    RelazioneStudenteCorso(3, matricola, Calendar.WEDNESDAY, "09:00", "C3", -1, 0, corso.first { it.id == 3 }.nome),
                    RelazioneStudenteCorso(4, matricola, Calendar.TUESDAY, "11:00", "B1", -1, 0, corso.first { it.id == 4 }.nome),
                    RelazioneStudenteCorso(5, matricola, Calendar.TUESDAY, "14:00", "B2", -1, 0, corso.first { it.id == 5 }.nome),
                    RelazioneStudenteCorso(6, matricola, Calendar.WEDNESDAY, "10:00", "C1", -1, 0, corso.first { it.id == 6 }.nome),
                    RelazioneStudenteCorso(7, matricola, Calendar.WEDNESDAY, "15:00", "C2", -1, 1, corso.first { it.id == 7 }.nome),
                    RelazioneStudenteCorso(8, matricola, Calendar.MONDAY, "09:00", "D1", -1, 1, corso.first { it.id == 8 }.nome),
                    RelazioneStudenteCorso(9, matricola, Calendar.THURSDAY, "13:00", "D2", -1, 1, corso.first { it.id == 9 }.nome),
                    RelazioneStudenteCorso(10, matricola, Calendar.FRIDAY, "10:00", "E1", -1, 0, corso.first { it.id == 10 }.nome),
                    RelazioneStudenteCorso(11, matricola, Calendar.FRIDAY, "12:00", "E2", -1, 0, corso.first { it.id == 11 }.nome),
                    RelazioneStudenteCorso(12, matricola, Calendar.MONDAY, "14:00", "F1", -1, 0, corso.first { it.id == 12 }.nome),
                    RelazioneStudenteCorso(13, matricola, Calendar.MONDAY, "16:00", "F2", -1, 0, corso.first { it.id == 13 }.nome),
                    RelazioneStudenteCorso(14, matricola, Calendar.MONDAY, "09:00", "G1", -1, 1, corso.first { it.id == 14 }.nome),
                    RelazioneStudenteCorso(15, matricola, Calendar.TUESDAY, "11:00", "G2", -1, 0, corso.first { it.id == 15 }.nome),
                    RelazioneStudenteCorso(16, matricola, Calendar.WEDNESDAY, "14:00", "H1", -1, 0, corso.first { it.id == 16 }.nome),
                    RelazioneStudenteCorso(17, matricola, Calendar.WEDNESDAY, "16:00", "H2", -1, 0, corso.first { it.id == 17 }.nome),
                    RelazioneStudenteCorso(18, matricola, Calendar.THURSDAY, "10:00", "I1", -1, 0, corso.first { it.id == 18 }.nome)
                )

                lifecycleScope.launch {

                    if (!studenteCorretto(studente) || studenteEsistente(studente)) {
                        Log.d("RegistrazioneActivityDEBUG", "Studente non valido")
                        Toast.makeText(
                            this@RegistrazioneActivity,
                            "Parametri non validi o studente esistente",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Esegui l'inserimento nel database in un thread di I/O
                        try {
                            // Con Dispatcher.IO eseguiamo l'inserimento in un thread di background
                            withContext(Dispatchers.IO) {


                                Log.d(
                                    "RegistrazioneActivityDEBUG",
                                    "Inserimento dello studente in corso"
                                )
                                dbViewModel.inserisciStudente(studente)
                                Log.d(
                                    "RegistrazioneActivityDEBUG",
                                    "Studente inserito correttamente"
                                )
                                // Inserisci i corsi
                                corso.forEach { dbViewModel.inserisciCorso(it) }

                                // Inserisci tutte le relazioni una per una, aspettando il completamento
                                relazioni.forEach { relazione ->
                                    delay(10)
                                    dbViewModel.inserisciRelazioneStudenteCorso(relazione)
                                }

                                Log.d("DEBUGRegistrazioneActivity", "Inserimento completato, aspetto la conferma del DB...")

                                // ⚠️ ASPETTA che Room completi gli inserimenti con una piccola pausa ⚠️
                                delay(500) // (facoltativo, aiuta Room a scrivere le modifiche)

                                // Recupera le relazioni solo dopo il completamento degli inserimenti
                                val allRelazioni = dbViewModel.getAllRelazioniStudenteCorso()
                                allRelazioni.forEach { relazione ->
                                    Log.d("DEBUGRegistrazioneActivity", "Relazione trovata: $relazione")
                                }
                            }

                            // Una volta inserito lo studente, torniamo al thread principale per aggiornare la UI
                            withContext(Dispatchers.Main) {
                                // Mostra il messaggio di successo
                                Toast.makeText(
                                    this@RegistrazioneActivity,
                                    "Registrazione avvenuta con successo",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Creiamo l'intent per passare alla HomeActivity
                                val intent = Intent(
                                    this@RegistrazioneActivity,
                                    HomeActivity::class.java
                                ).apply {
                                    putExtra("username", matricola)
                                }
                                // Avvia l'activity
                                startActivity(intent)
                            }
                        } catch (e: Exception) {
                            // In caso di errore, visualizza un messaggio di errore
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@RegistrazioneActivity,
                                    "Errore nella registrazione: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }

            }
        }
    }

    private suspend fun studenteEsistente(studente: Studente): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val studenteEsistente = dbViewModel.studenteByMatricola(studente.matricola)
                studenteEsistente != null
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RegistrazioneActivity,
                        "Errore nella registrazione: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                false // Ritorna false in caso di errore
            }
        }
    }


    private fun campiVuoti(
        matricolaEditText: EditText,
        cfEditText: EditText,
        pswdEditText: EditText,
        nomeEditText: EditText,
        cognomeEditText: EditText,
        iseeEditText: EditText,
        emailEditText: EditText
    ): Boolean {

        return matricolaEditText.text.isEmpty() ||
                cfEditText.text.isEmpty() ||
                pswdEditText.text.isEmpty() ||
                nomeEditText.text.isEmpty() ||
                cognomeEditText.text.isEmpty() || iseeEditText.text.isEmpty() || emailEditText.text.isEmpty()
    }

    private fun studenteCorretto(studente: Studente): Boolean {
        // Verifica che la matricola sia maggiore di 0
        if (studente.matricola <= 0) {
            Toast.makeText(
                this,
                "Matricola non valida. Deve essere un numero positivo.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        // Verifica che il codice fiscale non sia vuoto
        if (studente.cf.isEmpty()) {
            Toast.makeText(this, "Codice fiscale non può essere vuoto.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che la password non sia vuota e abbia almeno 8 caratteri
        if (studente.pswd.isEmpty()) {
            Toast.makeText(this, "Password non valida", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che il nome non sia vuoto
        if (studente.nome.isEmpty()) {
            Toast.makeText(this, "Nome non può essere vuoto.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che il cognome non sia vuoto
        if (studente.cognome.isEmpty()) {
            Toast.makeText(this, "Cognome non può essere vuoto.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che l'ISEE sia maggiore di 0
        if (studente.isee <= 0) {
            Toast.makeText(
                this,
                "ISEE non valido. Deve essere un valore positivo.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        // Verifica che l'email non sia vuota e contenga il simbolo '@'
        if (studente.email.isEmpty() || !studente.email.contains("@")) {
            Toast.makeText(
                this,
                "Email non valida. Assicurati che sia un indirizzo email valido.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        // Tutte le verifiche sono state superate
        return true
    }
}
