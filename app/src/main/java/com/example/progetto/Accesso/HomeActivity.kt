package com.example.progetto.Accesso

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.AreaPersonale
import com.example.progetto.BachecaEsiti
import com.example.progetto.AreeBiblioteca.Biblioteca
import com.example.progetto.Esami.CalendarioEsami
import com.example.progetto.Collegamenti
import com.example.progetto.Esami.AppelliDisponibili
import com.example.progetto.Libretto
import com.example.progetto.Mensa
import com.example.progetto.OrarioLezioni
import com.example.progetto.PianoStudi
import com.example.progetto.Questionari
import com.example.progetto.R
import com.example.progetto.Tasse
import com.example.progetto.dataBase.DBViewModel
import com.example.progetto.mappa.Mappa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//TOOD: Bisogna gestire quanto di seguito
/*
* All'accesso dell'utente bisogna fare il calcolo, prendendo la relazione Biblioteca - Studente, verificare quanti giorni mancano (sostanzialmente è un controllo sul counter) e segnalare se ci siano libri da restituire
*
* Se la scadenza è minore di 5, mando una notifica per segnalare di portare indietro il libro*/
class HomeActivity : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Saluto dell'utente
        val username = intent.getIntExtra("username",1)
        Toast.makeText(this, "Benvenuto $username", Toast.LENGTH_SHORT).show()

        val orarioLezioni: Button = findViewById(R.id.orarioLezioni)
        val calendarioEsami: Button = findViewById(R.id.calendarioEsami)
        val mensa: Button = findViewById(R.id.mensa)
        val tasse: Button = findViewById(R.id.tasse)
        val libretto: Button = findViewById(R.id.libretto)
        val bachecaEsiti: Button = findViewById(R.id.bachecaEsiti)
        val questionari: Button = findViewById(R.id.questionari)
        val pianoStudi: Button = findViewById(R.id.pianoStudi)
        val collegamenti: Button = findViewById(R.id.collegamenti)
        val mappa: Button = findViewById(R.id.mappa)
        val biblioteca: Button = findViewById(R.id.biblioteca)
        val areaPersonale : Button = findViewById(R.id.areaPersonale)

        Log.d("HomeActivityDEBUG", "Username: $username")

        //TODO: Recuperare eventuali libri da restituire (verificare mediante la dataDiScadenza)

        dbViewModel = DBViewModel(application)
        //Ora cerco di risalire allo studente
        // Usa coroutines per eseguire la query in background con Dispatchers.IO
        lifecycleScope.launch {

            Log.d("HomeActivityDEBUG", "Inizio query per studente")
            // Esegui la query di database su un thread di I/O
            val studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username)  // Query al database
            }
            Log.d("HomeActivityDEBUG", "Risultato query: $studente")


            // Una volta che la query è completata, torna al main thread per aggiornare la UI
            withContext(Dispatchers.Main) {
                if (studente != null) {
                    Toast.makeText(this@HomeActivity, "Benvenuto ${studente.nome}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@HomeActivity, "Studente non trovato", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Gestione dei bottoni: non appena si preme un bottone si apre una nuova activity, bisogna anche estrarre il nome dell'utente da intent
        orarioLezioni.setOnClickListener {
            val intent = Intent(this, OrarioLezioni::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        calendarioEsami.setOnClickListener {
            val intent = Intent(this, AppelliDisponibili::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        mensa.setOnClickListener {
            val intent = Intent(this, Mensa::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        tasse.setOnClickListener {
            val intent = Intent(this, Tasse::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        libretto.setOnClickListener {
            val intent = Intent(this, Libretto::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        bachecaEsiti.setOnClickListener {
            val intent = Intent(this, BachecaEsiti::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        questionari.setOnClickListener {
            val intent = Intent(this, Questionari::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        pianoStudi.setOnClickListener {
            val intent = Intent(this, PianoStudi::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        collegamenti.setOnClickListener {
            val intent = Intent(this, Collegamenti::class.java).apply {
                //Non serve passare username, è un rimando a tutti i collegamenti dell'università
            }
            startActivity(intent)
        }
        mappa.setOnClickListener {
            val intent = Intent(this, Mappa::class.java).apply {

            }
            startActivity(intent)
        }
        biblioteca.setOnClickListener {
            val intent = Intent(this, Biblioteca::class.java).apply {
                Log.d("HomeActivityDEBUG", "Username prima di passare alla biblioteca: $username")
                putExtra("username", username)
            }
            startActivity(intent)
        }
        areaPersonale.setOnClickListener {
            val intent = Intent(this, AreaPersonale::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }

    }
}