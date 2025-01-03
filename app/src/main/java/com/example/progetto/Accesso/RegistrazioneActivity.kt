package com.example.progetto.Accesso

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Studente
import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrazioneActivity : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel // Aggiungi questa riga per il viewModel

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

        bottoneInvia.setOnClickListener {
            if (campiVuoti(matricolaEditText,
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

                val studente = Studente(matricola, cf, pswd, nome, cognome, isee, email)

                    lifecycleScope.launch {

                if (!studenteCorretto(studente) || studenteEsistente(studente)) {
                    Log.d("RegistrazioneActivityDEBUG", "Studente non valido")
                    Toast.makeText(this@RegistrazioneActivity, "Parametri non validi o studente esistente", Toast.LENGTH_SHORT).show()
                } else {
                    // Esegui l'inserimento nel database in un thread di I/O
                        try {
                            // Con Dispatcher.IO eseguiamo l'inserimento in un thread di background
                            withContext(Dispatchers.IO) {
                                Log.d("RegistrazioneActivityDEBUG","Inserimento dello studente in corso")
                                dbViewModel.inserisciStudente(studente)
                                Log.d(
                                    "RegistrazioneActivityDEBUG",
                                    "Studente inserito correttamente"
                                )
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
