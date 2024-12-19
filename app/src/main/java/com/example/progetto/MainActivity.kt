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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.progetto.dataBase.DataBaseApp
import com.example.progetto.dataBase.StudenteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    companion object {
        lateinit var dataBaseApp: DataBaseApp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("DbLogin", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        val db = Room.databaseBuilder(this, DataBaseApp::class.java, DataBaseApp.NAME).build()
        //In questo caso ho bisogno solo del DAO dello studente per salvare / recuperare lo studente
        val studenteDao = db.getStudenteDao()

        //Bisogna gestire il login tramite il pulsante Invia
        val textMatricola: EditText = findViewById(R.id.textUsername)
        val textPassword: EditText = findViewById(R.id.textPassword)

        //Bisogna gestire anche la memorizzazione dell'utente
        val ricordami: CheckBox = findViewById(R.id.Ricordami)

        //Quando l'utente clicca sul bottone Invia, bisogna andare a controllare che i dati siano corretti
        //prima di tutto controllo se l'utente sia già registrato




        val invia: Button = findViewById(R.id.bottoneInvia)
        invia.setOnClickListener {
            // Recupera i dati inseriti dall'utente
            val usr = textMatricola.text.toString().trim() // Rimuovo gli spazi bianchi
            val pwd = textPassword.text.toString().trim()

            if (usr.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show()
            }

            //TODO: Da provare perchè a me fa crashare l'app
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val studente = studenteDao.getStudenteByMatricola(usr.toInt()).value
                    if (studente.isNullOrEmpty()) {
                        // Utente non trovato, avvia RegistrazioneActivity
                        Toast.makeText(this@MainActivity, "Proseguiamo con la registrazione", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, RegistrazioneActivity::class.java)
                        startActivity(intent)
                    } else {
                        // Utente trovato, avvia HomeActivity
                        val intent = Intent(this@MainActivity, HomeActivity::class.java).apply {
                            putExtra("username", usr)
                        }
                        if (ricordami.isChecked) {
                            salvaUtente(usr, pwd)
                            Toast.makeText(this@MainActivity, "Salvato", Toast.LENGTH_SHORT).show()
                        } else {
                            editor.clear()
                            editor.apply()
                        }
                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Errore durante l'accesso: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




    private fun salvaUtente(usr: String, pwd: String) {
        editor.putString("username", usr) //metto la chiave e il valore
        editor.putString("password", pwd) //metto la chiave e il valore
        editor.putBoolean("ricordami", true)
        editor.apply()  //salvo le modifiche
    }

    private fun loadUtente(
        textUsername: EditText,
        textPassword: EditText,
        ricordami: CheckBox
    ): Boolean {
        val UsernameSalvato = sharedPreferences.getString("username", "")
        if (UsernameSalvato == "") {
            return false
        }
        val PasswordSalvata = sharedPreferences.getString("password", "")
        val Ricordami = sharedPreferences.getBoolean("ricordami", false)
        if (Ricordami) {
            textUsername.setText(UsernameSalvato)
            textPassword.setText(PasswordSalvata)
            ricordami.isChecked = true
        }
        return true
    }
}