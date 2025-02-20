package com.example.progetto.AreeBiblioteca

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Schemi.Libro
import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Libro_Riutilizzabile : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libro_riutilizzabile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //recupero dei dati passati passati tramite Intent

        val nome= intent.getStringExtra("nome")
        val autore= intent.getStringExtra("autore")
        val matricola= intent.getIntExtra("username",0)
        Log.d("BiblioDebu", "Recupero della matricola: $matricola")

        // aggiorno l'intestazione della pagina con il nome del libro
        val nomeLibro: TextView = findViewById(R.id.nomeLibro)
        nomeLibro.text = nome


        // Aggiorno il resto degli elementi di layout con le informazioni del libro

        dbViewModel = DBViewModel(application)
        val testoNome: TextView = findViewById(R.id.TestoNome)
        testoNome.text = nome
        val autoreTV: TextView = findViewById(R.id.TestoAutore)
        autoreTV.text = autore
        var libro: Libro = Libro("","","",0,"")
        lifecycleScope.launch {
            try {
                libro = withContext(Dispatchers.IO) {
                    dbViewModel.getLibroByNomeAutore(nome.toString(), autore.toString())!!
                }
                // aggiorno gli elementi di layout con le informazioni del libro prese dal database
                Log.d("BiblioDebu", "Recupero del libro ${libro.name}")
                val settoreTv: TextView= findViewById(R.id.TestoSettore)
                settoreTv.text=libro.settore
                val sinossiTv: TextView= findViewById(R.id.TestoSinossi)
                sinossiTv.text=libro.sinossi
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //gestione del pulsante di richiesta

            val bottoneRichiesta: TextView = findViewById(R.id.bottoneRichiesta)
            val progressBar: ProgressBar = findViewById(R.id.progressBar)

            // se il libro è stato prenotato da qualcuno, il pulsante diventa invisibile
            if (libro.matricolaStudente!=null) {
                bottoneRichiesta.visibility= Button.GONE
            }

            bottoneRichiesta.setOnClickListener {
                // gestione della progress bar che servirà per mostrare un'animazione a schermo
                progressBar.visibility = ProgressBar.VISIBLE
                progressBar.progress = 0
                lifecycleScope.launch {
                    try {
                        val success = withContext(Dispatchers.IO) {
                            libro.matricolaStudente = matricola
                            dbViewModel.aggiungiLibro(libro)
                            true
                        }
                        // con la variabile success posso capire se la richiesta è andata a buon fine o meno, se è andata a buon fine, avrò aggionrato anche il campo matricola nellìistanza di database del libro

                        if (success) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@Libro_Riutilizzabile, "Prestito preso in carico", Toast.LENGTH_LONG).show()
                                // tramite una gestione intelligente degli Handler, è possibile simulare un'animazione della progress bar, in questo caso, l'animazione è di 2 secondi
                                Handler(Looper.getMainLooper()).postDelayed({
                                    progressBar.visibility = ProgressBar.GONE
                                    Toast.makeText(this@Libro_Riutilizzabile, "Libro preso in prestito!", Toast.LENGTH_LONG).show()
                                    finish()
                                }, 2000)
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@Libro_Riutilizzabile, "Errore durante la richiesta", Toast.LENGTH_LONG).show()
                                Handler(Looper.getMainLooper()).postDelayed({
                                    progressBar.visibility = ProgressBar.GONE
                                    finish()
                                }, 2000)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Libro_Riutilizzabile, "Errore durante la richiesta", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}