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
import com.example.progetto.dataBase.DBViewModel

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

        // Trova le view
        val textMatricola: EditText = findViewById(R.id.textUsername)
        val textPassword: EditText = findViewById(R.id.textPassword)
        val ricordami: CheckBox = findViewById(R.id.Ricordami)
        val invia: Button = findViewById(R.id.bottoneInvia)

        // Listener del bottone invia
        invia.setOnClickListener {
            val matricola: Int = textMatricola.text.toString().toInt()
            val pwd = textPassword.text.toString().trim()

            val controlloStudente = dbViewModel.studenteByMatricola(matricola)

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

                startActivity(intent)
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


