package com.example.progetto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto.AreeBiblioteca.LibroAdapter
import com.example.progetto.Esami.CorsoAdapter
import com.example.progetto.dataBase.DBViewModel

class PianoStudi : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel
    private lateinit var corsoListAdapter1: CorsoAdapter
    private lateinit var corsoListAdapter2: CorsoAdapter
    private lateinit var corsoListAdapter3: CorsoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_piano_studi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        dbViewModel= ViewModelProvider(this).get(DBViewModel::class.java)
        val matricola= intent.getIntExtra("username",1)

        val lista1 = findViewById<RecyclerView>(R.id.lista1anno)
        lista1.layoutManager = LinearLayoutManager(this)
        corsoListAdapter1 = CorsoAdapter(this)
        lista1.adapter = corsoListAdapter1
        dbViewModel.getEsamiDaFareDiUnAnno(matricola,1)?.observe(this, Observer{ corsi -> corsoListAdapter1.submitList(corsi)
        })

        val lista2 = findViewById<RecyclerView>(R.id.lista2anno)
        lista2.layoutManager = LinearLayoutManager(this)
        corsoListAdapter2 = CorsoAdapter(this)
        lista2.adapter = corsoListAdapter2
        dbViewModel.getEsamiDaFareDiUnAnno(matricola,2)?.observe(this, Observer{ corsi -> corsoListAdapter2.submitList(corsi)
        })

        val lista3 = findViewById<RecyclerView>(R.id.lista3anno)
        lista3.layoutManager = LinearLayoutManager(this)
        corsoListAdapter3 = CorsoAdapter(this)
        lista3.adapter = corsoListAdapter3
        dbViewModel.getEsamiDaFareDiUnAnno(matricola,3)?.observe(this, Observer{ corsi -> corsoListAdapter3.submitList(corsi)
        })
    }
}