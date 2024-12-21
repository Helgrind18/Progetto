package com.example.progetto

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
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dbViewModel: DBViewModel

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
                lifecycleScope.launch {
                    try {
                        // Esegui la query di database su un thread di I/O
                        val studente = withContext(Dispatchers.IO) {
                            // Chiamata al database in un thread separato
                            dbViewModel.studenteByMatricola(matricola)
                        }

                        // Una volta ottenuto il risultato, torna al thread principale
                        withContext(Dispatchers.Main) {
                            if (studente != null) {
                                if (studente.pswd == pwd) {
                                    // login riuscito
                                    Toast.makeText(this@LoginActivity, "Benvenuto ${studente.nome}", Toast.LENGTH_SHORT).show()
                                    // Passa alla HomeActivity
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
                                        putExtra("username", matricola)
                                    }
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(this@LoginActivity, "Password errata", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@LoginActivity, "Studente non trovato", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        // Log l'eccezione per aiutare con il debug
                        Log.e("LoginActivityDEBUG", "Errore durante il login", e)
                        Toast.makeText(this@LoginActivity, "Si è verificato un errore, riprova più tardi", Toast.LENGTH_SHORT).show()
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
