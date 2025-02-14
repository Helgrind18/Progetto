package com.example.progetto

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        var giorno = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("OrarioLezioniDEBUG", "Giorno della settimana: $giorno")
        // ✅ Inizializza l'adapter PRIMA della coroutine
        val recyclerView = findViewById<RecyclerView>(R.id.listaLezioni)
        lezioneAdapter = LezioneAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = lezioneAdapter

        val sceltaGiorno = findViewById<TextView>(R.id.sceltaGiorno)
        sceltaGiorno.setOnClickListener{
            val lista: LinearLayout = findViewById(R.id.listaGiorni)
            if(lista.visibility == LinearLayout.VISIBLE){
                lista.visibility = LinearLayout.GONE
            }else{
                lista.visibility = LinearLayout.VISIBLE
            }
        }

        val lunedi = findViewById<TextView>(R.id.clickLunedi)
        lunedi.setOnClickListener{
            giorno=Calendar.MONDAY
            aggiornaListaLezioni(username,giorno)
        }

        val martedi = findViewById<TextView>(R.id.clickMartedi)
        martedi.setOnClickListener {
            giorno = Calendar.TUESDAY
            aggiornaListaLezioni(username, giorno)
        }

        val mercoledi = findViewById<TextView>(R.id.clickMercoledi)
        mercoledi.setOnClickListener{
            giorno=Calendar.WEDNESDAY
            aggiornaListaLezioni(username,giorno)
        }

        val giovedi = findViewById<TextView>(R.id.clickGiovedì)
        giovedi.setOnClickListener{
            giorno=Calendar.THURSDAY
            aggiornaListaLezioni(username,giorno)
        }

        val venerdi = findViewById<TextView>(R.id.clickVenerdi)
        venerdi.setOnClickListener{
            giorno=Calendar.FRIDAY
            aggiornaListaLezioni(username,giorno)
        }

        aggiornaListaLezioni(username,giorno)
    }

    private fun aggiornaListaLezioni(username: Int, giorno: Int) {
        lifecycleScope.launch {
            try {
                val studenteRecuperato = withContext(Dispatchers.IO) {
                    dbViewModel.studenteByMatricola(username)
                }
                if (studenteRecuperato != null) {
                    withContext(Dispatchers.Main) {
                        val calendar = Calendar.getInstance()
                        var anno = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                        //Gestione per un piccolo errore, se lo studente si immatricola nel 2025 allora risulta con anno = 0
                        if(anno == 0){
                            anno = 1
                        }
                        val semestre = if (calendar.get(Calendar.MONTH) >= 3) 2 else 1

                        dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)?.observe(this@OrarioLezioni, Observer { lezioni -> lezioneAdapter.submitList(lezioni) })
                    }
                } else {
                    Log.e("OrarioLezioni", "Studente non trovato")
                }
            } catch (e: Exception) {
                Log.e("OrarioLezioni", "Errore nel recupero studente", e)
            }
        }
    }


}
