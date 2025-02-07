package com.example.progetto

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.InvalidationTracker
import com.example.progetto.Entity.Schemi.Studente
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class OrarioLezioni : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel
    private lateinit var lezioneAdapter: LezioneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_orario_lezioni)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbViewModel = DBViewModel(application)
        val username = intent.getIntExtra("username", 1)

        val calendar: Calendar = Calendar.getInstance()
        val giorno= calendar.get(Calendar.DAY_OF_WEEK)
        var studente: Studente =
            Studente(1, "", "", "", "", 0, "", 0, false, false, false, false, 0)

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
                val recyclerView = findViewById<RecyclerView>(R.id.listaLezioni)
                lezioneAdapter = LezioneAdapter()
                recyclerView.layoutManager = LinearLayoutManager(this as Context?)
                recyclerView.adapter = lezioneAdapter
                dbViewModel.getLezioni(giorno, studente.matricola,3,2)?.observe(
                    this as LifecycleOwner,
                    Observer{ lezioni -> lezioneAdapter.submitList(lezioni)
                    })
                Log.d("ORARIOBUG", "Risultato ${lezioneAdapter.currentList}")
            }catch (e: Exception) {
                Log.e("TasseDEBUG", "Errore nel recupero lezioni", e)
            }

        }


    }
    fun giornoToString(giorno: Int): String {
        return when (giorno) {
            Calendar.SUNDAY -> "Domenica"
            Calendar.MONDAY -> "Lunedì"
            Calendar.TUESDAY -> "Martedì"
            Calendar.WEDNESDAY -> "Mercoledì"
            Calendar.THURSDAY -> "Giovedì"
            Calendar.FRIDAY -> "Venerdì"
            Calendar.SATURDAY -> "Sabato"
            else -> "Sconosciuto"
        }
    }

}