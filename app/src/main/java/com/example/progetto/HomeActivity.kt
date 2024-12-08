package com.example.progetto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


//TOOD: Bisogna gestire quanto di seguito
/*
* All'accesso dell'utente bisogna fare il calcolo, prendendo la relazione Biblioteca - Studente, verificare quanti giorni mancano (sostanzialmente è un controllo sul counter) e segnalare se ci siano libri da restituire
*
* Se la scadenza è minore di 5, mando una notifica per segnalare di portare indietro il libro*/
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Saluto dell'utente
        val username = intent.getStringExtra("username")
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

        // Gestione dei bottoni: non appena si preme un bottone si apre una nuova activity, bisogna anche estrarre il nome dell'utente da intent
        orarioLezioni.setOnClickListener {
            val intent = Intent(this, OrarioLezioni::class.java).apply {
                putExtra("username", username)
            }
            startActivity(intent)
        }
        calendarioEsami.setOnClickListener {
            val intent = Intent(this, CalendarioEsami::class.java).apply {
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