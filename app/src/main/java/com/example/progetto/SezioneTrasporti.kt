package com.example.progetto

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

class SezioneTrasporti : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel
    private lateinit var corsaAdapter: CorseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sezione_trasporti)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbViewModel = DBViewModel(application)

        val data: Calendar= Calendar.getInstance()
        val ora: Int= data.get(Calendar.HOUR_OF_DAY)
        Log.d("PullDebug", "ora: $ora")
        val minuto: Int= data.get(Calendar.MINUTE)
        val dataUtile= ora*100+minuto
        Log.d("PullDebug", "dataUtile: $dataUtile")

        val bottoneCerca: ImageButton = findViewById(R.id.bottoneCerca)
        val barra: EditText = findViewById(R.id.dest)
        val recyclerView: RecyclerView = findViewById(R.id.listacorse)
        corsaAdapter = CorseAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this as Context?)
        recyclerView.adapter = corsaAdapter
        dbViewModel.getPullmanByOrarioPartenza(dataUtile)?.observe(this as LifecycleOwner, Observer { corse -> corsaAdapter.submitList(corse) })
        bottoneCerca.setOnClickListener {
                val ricerca = barra.text.toString().toUpperCase().trim()
                if (ricerca.isEmpty()) {
                    dbViewModel.getPullmanByOrarioPartenza(dataUtile)?.observe(this as LifecycleOwner, Observer { corse -> corsaAdapter.submitList(corse) })

                }else {
                    Log.d("PullDebug", "ho cliccato il bottone con $ricerca e $dataUtile")
                    dbViewModel.getPullmanByOrarioPartenzaEDestinazione(dataUtile, ricerca)
                        ?.observe(
                            this as LifecycleOwner,
                            Observer { corse -> corsaAdapter.submitList(corse) })
                    Log.d("PullDebug", "ho preso la lista")
                }
        }
    }
}