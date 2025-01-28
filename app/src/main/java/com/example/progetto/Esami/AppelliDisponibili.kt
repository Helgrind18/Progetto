package com.example.progetto.Esami

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
import java.util.Calendar

class AppelliDisponibili : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel
    private lateinit var esamiAdapter: EsamiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_appelli_disponibili)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var cal: Calendar= Calendar.getInstance()
        val anno: Int=cal.get(Calendar.YEAR)


        val recyclerView = findViewById<RecyclerView>(R.id.lista)
        esamiAdapter = EsamiAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = esamiAdapter
        dbViewModel= ViewModelProvider(this).get(DBViewModel::class.java)
        dbViewModel.getEsamiPrenotabili(intent.getIntExtra("username",1),anno)?.observe(this, Observer{ esami -> esamiAdapter.submitList(esami)
        })


    }
}