package com.example.progetto.Accesso

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.PrestitoConLibro
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

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.inserisciStudente(studenteTest)
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
            iSBN = 1234567890123L
        )

        val libroTest2 = Libro(
            name = "La psicologia del futuro",
            autore = "Giovanni Rossi",
            settore = "Psicologia",
            iSBN = 1234567890456L
        )

        val libroTest3 = Libro(
            name = "Introduzione alla filosofia",
            autore = "Alessandro Verdi",
            settore = "Filosofia",
            iSBN = 1234567890789L
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

        // Creazione di prestiti per ciascun libro
        val prestitoTest1 = PrestitoConLibro(
            matricolaStudente = studenteTest.matricola,
            nameLibro = libroTest1.name,
            autoreLibro = libroTest1.autore,
            dataScadenza = 20241230
        )

        val prestitoTest2 = PrestitoConLibro(
            matricolaStudente = studenteTest.matricola,
            nameLibro = libroTest2.name,
            autoreLibro = libroTest2.autore,
            dataScadenza = 20250115
        )

        val prestitoTest3 = PrestitoConLibro(
            matricolaStudente = studenteTest.matricola,
            nameLibro = libroTest3.name,
            autoreLibro = libroTest3.autore,
            dataScadenza = 20250220
        )

        Log.d("MainActivityDEBUG", "PrestitoTest1: $prestitoTest1")
        Log.d("MainActivityDEBUG", "PrestitoTest2: $prestitoTest2")
        Log.d("MainActivityDEBUG", "PrestitoTest3: $prestitoTest3")

        // Aggiunta dei prestiti al database
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    dbViewModel.aggiungiPrestito(prestitoTest1)
                    dbViewModel.aggiungiPrestito(prestitoTest2)
                    dbViewModel.aggiungiPrestito(prestitoTest3)
                    Log.d("MainActivityDEBUG", "Prestiti inseriti correttamente")
                } catch (e: Exception) {
                    Log.e("MainActivityDEBUG", "Errore durante l'inserimento dei prestiti", e)
                }
            }
        }

    }
}
