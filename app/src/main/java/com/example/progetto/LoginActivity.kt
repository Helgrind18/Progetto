package com.example.progetto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
            val matricola: Int = textMatricola.text.toString().toInt()
            val pwd = textPassword.text.toString().trim()

            lifecycleScope.launch {
                // Esegui la query di database su un thread di I/O
                val studente = withContext(Dispatchers.IO) {
                    dbViewModel.studenteByMatricola(matricola)  // Query al database
                }

                // Una volta che la query è completata, torna al main thread per aggiornare la UI
                withContext(Dispatchers.Main) {
                    if (studente != null) {
                        Toast.makeText(this@LoginActivity, "Benvenuto ${studente.nome}", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Studente non trovato", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            if (matricola <= 0 || pwd.isEmpty()) {
                Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show()
            } else {

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("username", matricola)
                }

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
        editor.putInt("username", usr) //metto la chiave e il valore
        editor.putString("password", pwd) //metto la chiave e il valore
        editor.putBoolean("ricordami", true)
        editor.apply()  //salvo le modifiche
    }

}


