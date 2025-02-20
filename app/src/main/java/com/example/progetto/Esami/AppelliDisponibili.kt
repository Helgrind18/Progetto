package com.example.progetto.Esami

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import androidx.lifecycle.Observer
import java.util.Calendar

class AppelliDisponibili : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel
    private lateinit var esamiAdapter: RelazioneStudenteCorsoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_appelli_disponibili)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val anno: Int= Calendar.getInstance().get(Calendar.YEAR)
        Log.d("AppelliDisponibiliDEBUG", "Anno corrente: $anno")

        // inizializzazione della RecyclerView e dell'adapter
        val recyclerView = findViewById<RecyclerView>(R.id.lista)
        esamiAdapter = RelazioneStudenteCorsoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = esamiAdapter
        dbViewModel= ViewModelProvider(this).get(DBViewModel::class.java)


        //Recupero la matricola
        val username = intent.getIntExtra("username",1)
        Log.d("AppelliDisponibiliDEBUG", "Username ricevuto: $username")

        // recupero gli esami e tramite la RecyclerView li mostro all'utente
        dbViewModel.getEsamiPrenotabili(username, anno)?.observe(this, Observer { esami ->
            Log.d("AppelliDisponibiliDEBUG", "Esami ricevuti: ${esami.size}")
            esami.forEach { Log.d("AppelliDisponibiliDEBUG", "Esame: $it") }
            esamiAdapter.submitList(esami)
        })



    }
}