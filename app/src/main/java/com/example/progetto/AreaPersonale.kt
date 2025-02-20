package com.example.progetto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.AreeBiblioteca.Prestiti_Personali
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AreaPersonale : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_area_personale)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val nome : TextView = findViewById(R.id.nomeUtente)
        val separatore: TextView=findViewById(R.id.separatore)
        separatore.text="INFORMAZIONI PERSONALI"

        dbViewModel = DBViewModel(application)
        //Recupero l'intero studente tramite il nome utente
        val username = intent.getIntExtra("username",1)
        lifecycleScope.launch {
            Log.d("AreaPersonaleDEBUG", "Inizio query per studente")
            // Eseguo la query di database su un thread di I/O
            val studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username) // Query al database
            }
            Log.d("AreaPersonaleDEBUG", "Risultato query: $studente")
            nome.text=studente.toString()

            val prestiti=withContext(Dispatchers.IO){
                dbViewModel.getLibriByStudente(username)
            }
            if (prestiti!=null){
                val separatore: TextView=findViewById(R.id.sep2)
                separatore.visibility=View.VISIBLE
                separatore.text="PRESTITI PERSONALI"
                val bottone:Button=findViewById(R.id.bottonePrestiti)
                bottone.visibility=View.VISIBLE
                // se l'utente clicca sul bottone, verrà portato alla pagina dei libri presi in prestito
                bottone.setOnClickListener{
                    val intent = Intent(this@AreaPersonale, Prestiti_Personali::class.java)
                    intent.putExtra("username",username)
                    startActivity(intent)
                }
            }
        }

    }
}