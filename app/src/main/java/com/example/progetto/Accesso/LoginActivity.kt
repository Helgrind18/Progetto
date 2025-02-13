package com.example.progetto.Accesso

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dbViewModel: DBViewModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LoginActivityDEBUG", "onCreate called")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Inizializza SharedPreferences
        sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        dbViewModel = DBViewModel(application)

        // Trova le view
        val textMatricola: EditText = findViewById(R.id.textUsername)
        val textPassword: EditText = findViewById(R.id.textPassword)
        val ricordami: CheckBox = findViewById(R.id.Ricordami)
        val invia: Button = findViewById(R.id.bottoneInvia)
        // Listener del bottone invia
        invia.setOnClickListener {
            if (textPassword.text.isEmpty() || textMatricola.text.isEmpty()) {
                Log.d("LoginActivityDEBUG", "Matricola o password vuoti")
                Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show()
            } else {
            val matricola: Int = textMatricola.text.toString().toInt()
            val pwd = textPassword.text.toString().trim()
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val studente = dbViewModel.studenteByMatricola(matricola)
                        /*val relazioni = dbViewModel.getRelazioniByMatricola(matricola)
                        relazioni.forEach { relazione ->
                            Log.d("LoginActivityDEBUG", "Relazione trovata: $relazione")
                        }*/
                      /*  delay(500)
                        lifecycleScope.launch(Dispatchers.IO) {
                            val allRelazioni = dbViewModel.getAllRelazioniStudenteCorso()
                            allRelazioni.forEach { relazione ->
                                Log.d("LoginActivityDEBUG", "Relazione trovata: $relazione")
                            }
                        }*/

                        withContext(Dispatchers.IO){
                            val relazioni = dbViewModel.getAllRelazioniStudenteCorsoList()
                            relazioni.forEach { relazione ->
                                delay(10)
                                relazione.matricola = matricola
                                dbViewModel.inserisciRelazioneStudenteCorso(relazione)
                            }
                        }

                        withContext(Dispatchers.Main) {
                            if (studente != null) {
                                if (studente.pswd == pwd) {
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                                        putExtra("username", matricola)
                                    }
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this@LoginActivity, "Password errata", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("LoginActivityDEBUG", "Errore durante il login", e)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@LoginActivity, "Si è verificato un errore, riprova più tardi", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // Gestione salvataggio credenziali
            if (ricordami.isChecked) {
                salvaUtente(matricola, pwd)
                Toast.makeText(this, "Salvato", Toast.LENGTH_SHORT).show()
            } else {
                editor.clear()
                editor.apply()
            }
        }
    }
    }

    private fun salvaUtente(usr: Int, pwd: String) {
        editor.putInt("username", usr) // metto la chiave e il valore
        editor.putString("password", pwd) // metto la chiave e il valore
        editor.putBoolean("ricordami", true)
        editor.apply()  // salvo le modifiche
    }
}
