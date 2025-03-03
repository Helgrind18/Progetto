package com.example.progetto

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.Entity.Relazioni.RelazioneStudenteCorso
import com.example.progetto.Entity.Schemi.Studente
import com.example.progetto.Esami.EsameAdapter
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Libretto : AppCompatActivity() {


    private lateinit var dbViewModel: DBViewModel
    private lateinit var esameListAdapter: EsameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libretto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbViewModel = DBViewModel(application)
        var lista: List<RelazioneStudenteCorso> = emptyList()
        val username = intent.getIntExtra("username", 1)
        // Utlizzando uno studente "dummy", reuperreò in seguito le informazioni dello studente
        var studente: Studente =Studente(1, "", "", "", "", 0, "", 0, false, false, false, false, 0)
        // Associazione della RecyclerView e dell'adapter
        val recyclerView = findViewById<RecyclerView>(R.id.lista)
        esameListAdapter = EsameAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = esameListAdapter
        // Recupero l'intera lista degli esami dello studente tramite la sua matricola
        dbViewModel.getEsamiDiStudenteLD(username)?.observe(
            this,
            Observer{ esami -> esameListAdapter.submitList(esami)
            })


        lifecycleScope.launch {
            Log.d("LibDEBUG", "Inizio query per studente")
            // Recupero l'intero studente
            try {
                studente = withContext(Dispatchers.IO) {
                    dbViewModel.studenteByMatricola(username)!!
                }
                Log.d("LibDEBUG", "Risultato studente: $studente")
            } catch (e: Exception) {
                Log.e("LibDEBUG", "Errore nel recupero studente", e)
            }

            // Questa lista di esami servirà per mostrare il numero di esami superati
            try {
                lista = withContext(Dispatchers.IO) {
                    dbViewModel.getEsamiDiStudente(username)!!
                }
                Log.d("LibDEBUG", "Risultato esami: $lista")
            } catch (e: Exception) {
                Log.e("LibDEBUG", "Errore nel recupero esami", e)
            }

            // Calcolo della media tramite la coroutine e l'update della UI
            try {
                val media = withContext(Dispatchers.IO) {
                    dbViewModel.getMedia(studente.matricola) ?: 0.0
                }
                Log.d("LibDEBUG", "Risultato media: $media")
                try {
                    val mediaPonderata = withContext(Dispatchers.IO) {
                        dbViewModel.getMediaPonderata(studente.matricola) ?: 0.0
                    }
                    Log.d("LibDEBUG", "Risultato media ponderata: $mediaPonderata")
                    val info: TextView = findViewById(R.id.infoLibretto)
                    info.text = testoInfo(media, mediaPonderata, lista)
                } catch (e: Exception) {
                    Log.e("LibDEBUG", "Errore nel recupero media ponderata", e)
                }

            } catch (e: Exception) {
                Log.e("LibDEBUG", "Errore nel recupero media", e)
            }

            // Gestione del bottone che al click mostrerà la lista degli esami
            val listaBottone: Button = findViewById(R.id.bottoneLista)
            listaBottone.setOnClickListener {
                val listaEs: TextView= findViewById(R.id.intEsami)
                val progressBar: ProgressBar = findViewById(R.id.progressBar)
                progressBar.visibility = ProgressBar.VISIBLE
                // Anche qui abbiamo scelto di aggiungere un'animazione di caricamento
                Handler(Looper.getMainLooper()).postDelayed({
                    listaBottone.visibility= Button.GONE
                    progressBar.visibility = View.GONE
                    listaEs.visibility= TextView.VISIBLE
                    recyclerView.visibility= RecyclerView.VISIBLE
                }, 1000)
            }
        }


    }

    // Funzione per il corretto aggiornamento del testo dei Layout
    private fun testoInfo(
        media: Double,
        mediaPonderata: Double,
        lista: List<RelazioneStudenteCorso>
    ): String {
        var ris: StringBuilder = StringBuilder()
        ris.append("Esami superati: ${lista.size}")
        ris.append("\n")
        ris.append("Media: ${media}/30.0")
        ris.append("\n")
        ris.append("Media ponderata: ${mediaPonderata}/30.0")
        ris.append("\n")
        Log.d("TasseDEBUG", "Testo info: $ris")
        return ris.toString()
    }


}