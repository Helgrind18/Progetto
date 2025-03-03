package com.example.progetto.AreeBiblioteca

import android.os.Bundle
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

class Prestiti_Personali : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel
    private lateinit var libroListAdapter: LibroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prestiti_personali)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getIntExtra("username", 1)

        val recyclerView = findViewById<RecyclerView>(R.id.lista)
        libroListAdapter = LibroAdapter(this,username)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = libroListAdapter
        dbViewModel= ViewModelProvider(this).get(DBViewModel::class.java)
        dbViewModel.getLibriByStudente(intent.getIntExtra("username",1))
            .observe(this, Observer{ libri -> libroListAdapter.submitList(libri)
            })

    }


}