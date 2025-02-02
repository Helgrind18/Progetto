package com.example.progetto

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.AreeBiblioteca.LibroAdapter
import com.example.progetto.Entity.Corso
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.Entity.Studente
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
        var studente: Studente =
            Studente(1, "", "", "", "", 0, "", 0, false, false, false, false, 0)

        val recyclerView = findViewById<RecyclerView>(R.id.lista)
        esameListAdapter = EsameAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = esameListAdapter
        dbViewModel.getEsamiDiStudenteLD(username)?.observe(
            this,
            Observer{ esami -> esameListAdapter.submitList(esami)
            })


        lifecycleScope.launch {
            Log.d("TasseDEBUG", "Inizio query per studente")
            try {
                studente = withContext(Dispatchers.IO) {
                    dbViewModel.studenteByMatricola(username)!!
                }
                Log.d("TasseDEBUG", "Risultato studente: $studente")
            } catch (e: Exception) {
                Log.e("TasseDEBUG", "Errore nel recupero studente", e)
            }

            try {
                lista = withContext(Dispatchers.IO) {
                    dbViewModel.getEsamiDiStudente(username)!!
                }
                Log.d("TasseDEBUG", "Risultato esami: $lista")
                gestisciProgressBar(lista.size)
            } catch (e: Exception) {
                Log.e("TasseDEBUG", "Errore nel recupero esami", e)
            }

            try {
                val media = withContext(Dispatchers.IO) {
                    dbViewModel.getMedia(studente.matricola) ?: 0.0
                }
                Log.d("TasseDEBUG", "Risultato media: $media")
                try {
                    val mediaPonderata = withContext(Dispatchers.IO) {
                        dbViewModel.getMediaPonderata(studente.matricola) ?: 0.0
                    }
                    Log.d("TasseDEBUG", "Risultato media ponderata: $mediaPonderata")
                    val info: TextView = findViewById(R.id.infoLibretto)
                    info.text = testoInfo(username, media, mediaPonderata, lista)
                } catch (e: Exception) {
                    Log.e("TasseDEBUG", "Errore nel recupero media ponderata", e)
                }

            } catch (e: Exception) {
                Log.e("TasseDEBUG", "Errore nel recupero media", e)
            }
        }


    }

    private fun testoInfo(
        username: Int,
        media: Double,
        mediaPonderata: Double,
        lista: List<RelazioneStudenteCorso>
    ): String {
        var ris: StringBuilder = StringBuilder()
        ris.append("Esami superati: ${lista.size}")
        ris.append("\n")
        ris.append("Media: $media")
        ris.append("\n")
        ris.append("Media ponderata: $mediaPonderata")
        ris.append("\n")
        Log.d("TasseDEBUG", "Testo info: ${ris.toString()}")
        return ris.toString()
    }

    private fun gestisciProgressBar(size: Int) {
        val progress: ProgressBar = findViewById(R.id.progresso)
        progress.visibility = ProgressBar.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            progress.progress = size
        }, 100)
    }

}