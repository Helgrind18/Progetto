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
import com.example.progetto.Entity.Libro
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
            tassa4 = false
        )

        val studente1 = Studente(
            matricola = 1001,
            cf = "RSSMRA85M01H501U",
            pswd = "password123",
            nome = "Mario",
            cognome = "Rossi",
            isee = 20000,
            email = "mario.rossi@example.com",
            annoImmatricolazione = 2022,
            tassa1 = false,
            tassa2 = false,
            tassa3 = false,
            tassa4 = false
        )

        val studente2 = Studente(
            matricola = 1002,
            cf = "VRDLGI90F03H501V",
            pswd = "mypassword",
            nome = "Luigi",
            cognome = "Verdi",
            isee = 15000,
            email = "luigi.verdi@example.com",
            annoImmatricolazione = 2022,
            tassa1 = false,
            tassa2 = false,
            tassa3 = false,
            tassa4 = false
        )

       /* lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciStudente(studenteTest)
                    dbViewModel.inserisciStudente(studente1)
                    dbViewModel.inserisciStudente(studente2)
                    Log.d("MainActivityDEBUG", "Studente inserito correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dello studente", e)
                }
            }
        }
*/

        // Creazione di 3 studenti
        val studente4 = Studente(
            matricola = 1004,
            cf = "RSSMRA85M01H500U",
            pswd = "password123",
            nome = "Mario",
            cognome = "Rossi",
            isee = 20000,
            email = "mario.rossi@example.com",
            annoImmatricolazione = 2022,
            tassa1 = false,
            tassa2 = false,
            tassa3 = false,
            tassa4 = false
        )

        val studente5 = Studente(
            matricola = 1005,
            cf = "VRDLGI90F03H501V",
            pswd = "mypassword",
            nome = "Luigi",
            cognome = "Verdi",
            isee = 15000,
            email = "luigi.verdi@example.com",
            annoImmatricolazione = 2022,
            tassa1 = false,
            tassa2 = false,
            tassa3 = false,
            tassa4 = false
        )

        val studente6 = Studente(
            matricola = 1006,
            cf = "TSTFNC75F01H500Y",
            pswd = "securepassword",
            nome = "Francesco",
            cognome = "Toni",
            isee = 22000,
            email = "francesco.toni@example.com",
            annoImmatricolazione = 2022,
            tassa1 = false,
            tassa2 = false,
            tassa3 = false,
            tassa4 = false
        )

// Creazione di 3 libri
        val libro4 = Libro(
            name = "Introduzione alla Psicologia",
            autore = "John Smith",
            settore = "Psicologia",
            iSBN = 1234567890123L,
            matricolaStudente = studente1.matricola
        )

        val libro5 = Libro(
            name = "Teoria della Relatività",
            autore = "Albert Einstein",
            settore = "Fisica",
            iSBN = 9876543210987L,
            matricolaStudente = studente2.matricola
        )

        val libro6 = Libro(
            name = "Storia delle Idee Filosofiche",
            autore = "Sofia Lorenzi",
            settore = "Filosofia",
            iSBN = 4567891234567L,
            matricolaStudente = studente6.matricola
        )

        // Creazione di più libri per test
        val libroTest1 = Libro(
            name = "Il codice della mente straordinaria",
            autore = "Vittorino Andreoli",
            settore = "Psicologia",
            iSBN = 1234567890123L,
            matricolaStudente = studenteTest.matricola
        )

        val libroTest2 = Libro(
            name = "La psicologia del futuro",
            autore = "Giovanni Rossi",
            settore = "Psicologia",
            iSBN = 1234567890456L,
            matricolaStudente = null
        )

        val libroTest3 = Libro(
            name = "Introduzione alla filosofia",
            autore = "Alessandro Verdi",
            settore = "Filosofia",
            iSBN = 1234567890789L,
            matricolaStudente = null
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciStudente(studenteTest)
                    dbViewModel.inserisciStudente(studente1)
                    dbViewModel.inserisciStudente(studente2)
                    dbViewModel.inserisciStudente(studente4)
                    dbViewModel.inserisciStudente(studente5)
                    dbViewModel.inserisciStudente(studente6)
                    Log.d("MainActivityDEBUG", "Studenti inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento degli studenti", e)
                }
            }

            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.aggiungiLibro(libroTest1)
                    dbViewModel.aggiungiLibro(libroTest2)
                    dbViewModel.aggiungiLibro(libroTest3)
                    dbViewModel.aggiungiLibro(libro4)
                    dbViewModel.aggiungiLibro(libro5)
                    dbViewModel.aggiungiLibro(libro6)
                    Log.d("MainActivityDEBUG", "Libri inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei libri", e)
                }
            }
        }

        val corso1 = Corso(id = 1, nome = "Ingegneria del Software", CFU = 12,3)
        val corso2 = Corso(id = 2, nome = "Basi di Dati", CFU = 9,3)
        val corso3 = Corso(id = 3, nome = "Reti di Calcolatori", CFU = 6,3)
        val corso4 = Corso(id = 4, nome = "Analisi Matematica 1", CFU = 9, anno = 1)
        val corso5 = Corso(id = 5, nome = "Fisica 1", CFU = 6, anno = 1)
        val corso6 = Corso(id = 6, nome = "Programmazione 1", CFU = 12, anno = 1)
        val corso7 = Corso(id = 7, nome = "Sistemi Operativi", CFU = 9, anno = 2)
        val corso8 = Corso(id = 8, nome = "Ingegneria del Web", CFU = 6, anno = 2)
        val corso9 = Corso(id = 9, nome = "Algoritmi e Strutture Dati", CFU = 12, anno = 2)
        val corso10 = Corso(id = 10, nome = "Calcolo Numerico", CFU = 6, anno = 3)
        val corso11 = Corso(id = 11, nome = "Intelligenza Artificiale", CFU = 9, anno = 3)
        val corso12 = Corso(id = 12, nome = "Sicurezza Informatica", CFU = 6, anno = 3)
        val corso13 = Corso(id = 13, nome = "Economia Aziendale", CFU = 6, anno = 1)
        val corso14 = Corso(id = 14, nome = "Reti di Telecomunicazioni", CFU = 9, anno = 2)
        val corso15 = Corso(id = 15, nome = "Progettazione Software", CFU = 9, anno = 3)
        val corso16 = Corso(id = 16, nome = "Sviluppo Mobile", CFU = 6, anno = 3)
        val corso17 = Corso(id = 17, nome = "Elaborazione di Immagini", CFU = 6, anno = 3)
        val corso18 = Corso(id = 18, nome = "Matematica Discreta", CFU = 9, anno = 1)


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciCorso(corso1)
                    dbViewModel.inserisciCorso(corso2)
                    dbViewModel.inserisciCorso(corso3)
                    dbViewModel.inserisciCorso(corso4)
                    dbViewModel.inserisciCorso(corso5)
                    dbViewModel.inserisciCorso(corso6)
                    dbViewModel.inserisciCorso(corso7)
                    dbViewModel.inserisciCorso(corso8)
                    dbViewModel.inserisciCorso(corso9)
                    dbViewModel.inserisciCorso(corso10)
                    dbViewModel.inserisciCorso(corso11)
                    dbViewModel.inserisciCorso(corso12)
                    dbViewModel.inserisciCorso(corso13)
                    dbViewModel.inserisciCorso(corso14)
                    dbViewModel.inserisciCorso(corso15)
                    dbViewModel.inserisciCorso(corso16)
                    dbViewModel.inserisciCorso(corso17)
                    dbViewModel.inserisciCorso(corso18)
                    Log.d("MainActivityDEBUG", "Corsi inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei corsi", e)
                }
            }
        }

        // Esempi di relazione con valori per giorno, ora e aula
        val relazione1 = RelazioneStudenteCorso(
            corsoId = 1,
            matricola = 15,
            giorno = "Lunedì",
            ora = "10:00",
            aula = "A1",
            voto=-1,
            1
        ) // Mario segue Ingegneria del Software

        val relazione2 = RelazioneStudenteCorso(
            corsoId = 2,
            matricola = 15,
            giorno = "Martedì",
            ora = "14:30",
            aula = "B2",
            voto=-1,
            0
        ) // Mario segue Basi di Dati

        val relazione3 = RelazioneStudenteCorso(
            corsoId = 3,
            matricola = 15,
            giorno = "Mercoledì",
            ora = "09:00",
            aula = "C3",
            voto=-1,
            0
        ) // Luigi segue Reti di Calcolatori

        val relazione4 = RelazioneStudenteCorso(
            corsoId = 4,
            matricola = 15,
            giorno = "Martedì",
            ora = "11:00",
            aula = "B1",
            voto = -1,
           0
        )

        val relazione5 = RelazioneStudenteCorso(
            corsoId = 5,
            matricola = 15,
            giorno = "Martedì",
            ora = "14:00",
            aula = "B2",
            voto = -1,
            0
        )

        val relazione6 = RelazioneStudenteCorso(
            corsoId = 6,
            matricola = 15,
            giorno = "Mercoledì",
            ora = "10:00",
            aula = "C1",
            voto = -1,
            0
        )

        val relazione7 = RelazioneStudenteCorso(
            corsoId = 7,
            matricola = 15,
            giorno = "Mercoledì",
            ora = "15:00",
            aula = "C2",
            voto = -1,
            1
        )

        val relazione8 = RelazioneStudenteCorso(
            corsoId = 8,
            matricola = 15,
            giorno = "Giovedì",
            ora = "09:00",
            aula = "D1",
            voto = -1,
            1
        )

        val relazione9 = RelazioneStudenteCorso(
            corsoId = 9,
            matricola = 15,
            giorno = "Giovedì",
            ora = "13:00",
            aula = "D2",
            voto = -1,
            1
        )

        val relazione10 = RelazioneStudenteCorso(
            corsoId = 10,
            matricola = 15,
            giorno = "Venerdì",
            ora = "10:00",
            aula = "E1",
            voto = -1,
           0
        )
        val relazione11 = RelazioneStudenteCorso(
            corsoId = 11,
            matricola = 15,
            giorno = "Venerdì",
            ora = "12:00",
            aula = "E2",
            voto = 18,
            0
        )

        val relazione12 = RelazioneStudenteCorso(
            corsoId = 12,
            matricola = 15,
            giorno = "Lunedì",
            ora = "14:00",
            aula = "F1",
            voto = 19,
            0
        )

        val relazione13 = RelazioneStudenteCorso(
            corsoId = 13,
            matricola = 15,
            giorno = "Lunedì",
            ora = "16:00",
            aula = "F2",
            voto = 20,
            0
        )

        val relazione14 = RelazioneStudenteCorso(
            corsoId = 14,
            matricola = 15,
            giorno = "Martedì",
            ora = "09:00",
            aula = "G1",
            voto = -1,
            1
        )

        val relazione15 = RelazioneStudenteCorso(
            corsoId = 15,
            matricola = 15,
            giorno = "Martedì",
            ora = "11:00",
            aula = "G2",
            voto = 30,
            0
        )

        val relazione16 = RelazioneStudenteCorso(
            corsoId = 16,
            matricola = 15,
            giorno = "Mercoledì",
            ora = "14:00",
            aula = "H1",
            voto = -1,
            0
        )

        val relazione17 = RelazioneStudenteCorso(
            corsoId = 17,
            matricola = 15,
            giorno = "Mercoledì",
            ora = "16:00",
            aula = "H2",
            voto = -1,
            0
        )
        val relazione18 = RelazioneStudenteCorso(
            corsoId = 18,
            matricola = 15,
            giorno = "Giovedì",
            ora = "10:00",
            aula = "I1",
            voto = -1,
            0
        )
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione1)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione2)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione3)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione4)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione5)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione6)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione7)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione8)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione9)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione10)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione11)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione12)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione13)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione14)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione15)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione16)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione17)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione18)
                    Log.d("MainActivityDEBUG", "Relazioni inserite correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento delle relazioni", e)
                }
            }
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val corsiDiMario: List<Int>? = dbViewModel.getCorsiSeguitiDaStudente(1001)
                    corsiDiMario?.forEach { corso ->
                        Log.d("getCorsiSeguitiDaStudente", "Corso seguito da Mario: $corso")
                    }
                } catch (e: Exception) {
                    Log.e("getCorsiSeguitiDaStudente", "Errore durante il recupero dei corsi", e)
                }
            }
        }


    }
}
