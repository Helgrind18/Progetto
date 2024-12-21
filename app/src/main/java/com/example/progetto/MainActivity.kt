package com.example.progetto
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.progetto.Entity.Studente

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


    }
}