package com.example.progetto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // Gestione dei bottoni: non appena si preme un bottone si apre una nuova activity, bisogna anche estrarre il nome dell'utente da intent
        orarioLezioni.setOnClickListener {
            val intent = Intent(this, OrarioLezioni::class.java).apply {
                putExtra("username", username)
            }
        }
    }
}