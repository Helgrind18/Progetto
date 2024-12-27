package com.example.progetto.Accesso

import android.content.Intent
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
            matricola = 15, // Nuovo valore per la matricola
            cf = "CODFSC67890", // Nuovo codice fiscale
            pswd = "a", // Nuova password
            nome = "Giuseppe", // Nuovo nome
            cognome = "Bianchi", // Nuovo cognome
            isee = 18000L,// Nuovo valore ISEE
            email = "giuseppe.bianchi@studenti.unical.it", // Nuovo email
        )
        val studente1 = Studente(
            matricola = 1001,
            cf = "RSSMRA85M01H501U",
            pswd = "password123",
            nome = "Mario",
            cognome = "Rossi",
            isee = 20000,
            email = "mario.rossi@example.com"
        )

        val studente2 = Studente(
            matricola = 1002,
            cf = "VRDLGI90F03H501V",
            pswd = "mypassword",
            nome = "Luigi",
            cognome = "Verdi",
            isee = 15000,
            email = "luigi.verdi@example.com"
        )

        lifecycleScope.launch {
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


        //SOLO PER VERIFICA: AGGIUNGO UN LIBRO ED UN PRESTITO
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
            matricolaStudente = studenteTest.matricola
        )

        // Aggiunta dei libri al database
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.aggiungiLibro(libroTest1)
                    dbViewModel.aggiungiLibro(libroTest2)
                    dbViewModel.aggiungiLibro(libroTest3)
                    Log.d("MainActivityDEBUG", "Libri inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei libri", e)
                }
            }
        }

        val corso1 = Corso(id = 1, nome = "Ingegneria del Software", CFU = 12)
        val corso2 = Corso(id = 2, nome = "Basi di Dati", CFU = 9)
        val corso3 = Corso(id = 3, nome = "Reti di Calcolatori", CFU = 6)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciCorso(corso1)
                    dbViewModel.inserisciCorso(corso2)
                    dbViewModel.inserisciCorso(corso3)
                    Log.d("MainActivityDEBUG", "Corsi inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei corsi", e)
                }
            }
        }

        val relazione1 = RelazioneStudenteCorso(corsoId = 1, matricola = 1001) // Mario segue Ingegneria del Software
        val relazione2 = RelazioneStudenteCorso(corsoId = 2,matricola = 1001) // Mario segue Basi di Dati
        val relazione3 = RelazioneStudenteCorso(corsoId = 3, matricola = 1002) // Luigi segue Reti di Calcolatori

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione1)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione2)
                    dbViewModel.inserisciRelazioneStudenteCorso(relazione3)
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


        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val studentiInReti : List<Int>? = dbViewModel.getStudentiDiCorso(3)
                    studentiInReti?.forEach { studente ->
                        Log.d("getStudentiDiCorso", "Studente in Reti: $studente")
                    }
                } catch (e: Exception) {
                    Log.e("getStudentiDiCorso", "Errore durante il recupero degli studenti", e)
                }
            }
        }
    }
}
