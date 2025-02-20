package com.example.progetto

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    @SuppressLint("UseCompatLoadingForDrawables")
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

        // Tramite Calendar potrò stabilire il giorno della settimana corretto
        val calendar: Calendar = Calendar.getInstance()
        var giorno = calendar.get(Calendar.DAY_OF_WEEK)
        Log.d("OrarioLezioniDEBUG", "Giorno della settimana: $giorno")
        // Creo la RecyclerView e le assegno l'adapter corretto
        val recyclerView = findViewById<RecyclerView>(R.id.listaLezioni)
        lezioneAdapter = LezioneAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = lezioneAdapter


        // Creo una lista di TextView
        val sceltaGiorno = findViewById<TextView>(R.id.sceltaGiorno)
        var lunedi = findViewById<TextView>(R.id.clickLunedi)
        val martedi = findViewById<TextView>(R.id.clickMartedi)
        val mercoledi = findViewById<TextView>(R.id.clickMercoledi)
        val giovedi = findViewById<TextView>(R.id.clickGiovedì)
        val venerdi = findViewById<TextView>(R.id.clickVenerdi)

        val giorniTextViews = listOf(lunedi, martedi, mercoledi, giovedi, venerdi)

        // Se clicco sull'elemento di Layout corrispondente, sarà mostrata a schermo la lista dei giorni (creata tramite vari LinearLayout innestati)
        sceltaGiorno.setOnClickListener{
            val lista: LinearLayout = findViewById(R.id.listaGiorni)
            if(lista.visibility == LinearLayout.VISIBLE){
                lista.visibility = LinearLayout.GONE
            }else{
                lista.visibility = LinearLayout.VISIBLE
            }
        }

        // A seconda del giorno cliccato, mostrerò a schermo solo lezioni di quel giorno e andrò ad evidenziare il giorno scelto

        lunedi.setOnClickListener{
            ricoloraTextView(giorniTextViews,lunedi)
            giorno=Calendar.MONDAY
            aggiornaListaLezioni(username,giorno)
        }


        martedi.setOnClickListener {
            ricoloraTextView(giorniTextViews,martedi)
            giorno = Calendar.TUESDAY
            aggiornaListaLezioni(username, giorno)
        }


        mercoledi.setOnClickListener{
            ricoloraTextView(giorniTextViews,mercoledi)
            giorno=Calendar.WEDNESDAY
            aggiornaListaLezioni(username,giorno)
        }

        giovedi.setOnClickListener{
            ricoloraTextView(giorniTextViews,giovedi)
            giorno=Calendar.THURSDAY
            aggiornaListaLezioni(username,giorno)
        }

        venerdi.setOnClickListener{
            ricoloraTextView(giorniTextViews,venerdi)
            giorno=Calendar.FRIDAY
            aggiornaListaLezioni(username,giorno)
        }

        aggiornaListaLezioni(username,giorno)
    }

    // Funzione che ricolora in verde il giorno selezionato, per evidenziarlo all'utente
    private fun ricoloraTextView(giorniTextViews: List<TextView>, giorno: TextView) {
        for (textView in giorniTextViews) {
            if (textView != giorno) {
                textView.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
                textView.setTextColor(ContextCompat.getColor(this, R.color.black))
            }
        }
        giorno.setBackgroundColor(ContextCompat.getColor(this, R.color.verde_opaco))
        giorno.setTextColor(ContextCompat.getColor(this, R.color.white))
    }

    // Funzione che recupera lo studente, ottiene la lista delle lezioni con il giorno corretto e la mostra a schermo
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
