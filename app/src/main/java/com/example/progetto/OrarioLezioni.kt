package com.example.progetto

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
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
            lifecycleScope.launch {
                Log.d("TasseDEBUG", "Inizio query per studente")
                try {
                    val studenteRecuperato = withContext(Dispatchers.IO) {
                        dbViewModel.studenteByMatricola(username)
                    }
                    if (studenteRecuperato != null) {
                        Log.d("TasseDEBUG", "Risultato studente: $studenteRecuperato")

                        // ✅ Assicurati di osservare LiveData nel thread principale
                        withContext(Dispatchers.Main) {
                            val anno:Int = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                            Log.d("OrarioLezioniDEBUG", "Anno corso : $anno")
                            var semestre: Int = 1
                            if(calendar.get(Calendar.MONTH) >= 3) {
                                semestre = 2
                            }
                            dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)
                                ?.observe(this@OrarioLezioni, Observer { lezioni ->
                                    Log.d("ListaDEBUG", "Numero lezioni ricevute: ${lezioni.size}")
                                    lezioneAdapter.submitList(lezioni)
                                })
                        }
                    } else {
                        Log.e("TasseDEBUG", "Studente non trovato")
                    }
                } catch (e: Exception) {
                    Log.e("TasseDEBUG", "Errore nel recupero studente", e)
                }
            }
        }
        val martedi = findViewById<TextView>(R.id.clickMartedi)
        martedi.setOnClickListener{
            giorno=Calendar.TUESDAY
            lifecycleScope.launch {
                Log.d("TasseDEBUG", "Inizio query per studente")
                try {
                    val studenteRecuperato = withContext(Dispatchers.IO) {
                        dbViewModel.studenteByMatricola(username)
                    }
                    if (studenteRecuperato != null) {
                        Log.d("TasseDEBUG", "Risultato studente: $studenteRecuperato")

                        // ✅ Assicurati di osservare LiveData nel thread principale
                        withContext(Dispatchers.Main) {
                            val anno:Int = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                            Log.d("OrarioLezioniDEBUG", "Anno corso : $anno")
                            var semestre: Int = 1
                            if(calendar.get(Calendar.MONTH) >= 3) {
                                semestre = 2
                            }
                            dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)
                                ?.observe(this@OrarioLezioni, Observer { lezioni ->
                                    Log.d("ListaDEBUG", "Numero lezioni ricevute: ${lezioni.size}")
                                    lezioneAdapter.submitList(lezioni)
                                })
                        }
                    } else {
                        Log.e("TasseDEBUG", "Studente non trovato")
                    }
                } catch (e: Exception) {
                    Log.e("TasseDEBUG", "Errore nel recupero studente", e)
                }
            }
        }
        val mercoledi = findViewById<TextView>(R.id.clickMercoledi)
        mercoledi.setOnClickListener{
            giorno=Calendar.WEDNESDAY
            lifecycleScope.launch {
                Log.d("TasseDEBUG", "Inizio query per studente")
                try {
                    val studenteRecuperato = withContext(Dispatchers.IO) {
                        dbViewModel.studenteByMatricola(username)
                    }
                    if (studenteRecuperato != null) {
                        Log.d("TasseDEBUG", "Risultato studente: $studenteRecuperato")

                        // ✅ Assicurati di osservare LiveData nel thread principale
                        withContext(Dispatchers.Main) {
                            val anno:Int = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                            Log.d("OrarioLezioniDEBUG", "Anno corso : $anno")
                            var semestre: Int = 1
                            if(calendar.get(Calendar.MONTH) >= 3) {
                                semestre = 2
                            }
                            dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)
                                ?.observe(this@OrarioLezioni, Observer { lezioni ->
                                    Log.d("ListaDEBUG", "Numero lezioni ricevute: ${lezioni.size}")
                                    lezioneAdapter.submitList(lezioni)
                                })
                        }
                    } else {
                        Log.e("TasseDEBUG", "Studente non trovato")
                    }
                } catch (e: Exception) {
                    Log.e("TasseDEBUG", "Errore nel recupero studente", e)
                }
            }
        }
        val giovedi = findViewById<TextView>(R.id.clickGiovedì)
        giovedi.setOnClickListener{
            giorno=Calendar.THURSDAY
            lifecycleScope.launch {
                Log.d("TasseDEBUG", "Inizio query per studente")
                try {
                    val studenteRecuperato = withContext(Dispatchers.IO) {
                        dbViewModel.studenteByMatricola(username)
                    }
                    if (studenteRecuperato != null) {
                        Log.d("TasseDEBUG", "Risultato studente: $studenteRecuperato")

                        // ✅ Assicurati di osservare LiveData nel thread principale
                        withContext(Dispatchers.Main) {
                            val anno:Int = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                            Log.d("OrarioLezioniDEBUG", "Anno corso : $anno")
                            var semestre: Int = 1
                            if(calendar.get(Calendar.MONTH) >= 3) {
                                semestre = 2
                            }
                            dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)
                                ?.observe(this@OrarioLezioni, Observer { lezioni ->
                                    Log.d("ListaDEBUG", "Numero lezioni ricevute: ${lezioni.size}")
                                    lezioneAdapter.submitList(lezioni)
                                })
                        }
                    } else {
                        Log.e("TasseDEBUG", "Studente non trovato")
                    }
                } catch (e: Exception) {
                    Log.e("TasseDEBUG", "Errore nel recupero studente", e)
                }
            }
        }
        val venerdi = findViewById<TextView>(R.id.clickVenerdi)
        venerdi.setOnClickListener{
            giorno=Calendar.FRIDAY
            lifecycleScope.launch {
                Log.d("TasseDEBUG", "Inizio query per studente")
                try {
                    val studenteRecuperato = withContext(Dispatchers.IO) {
                        dbViewModel.studenteByMatricola(username)
                    }
                    if (studenteRecuperato != null) {
                        Log.d("TasseDEBUG", "Risultato studente: $studenteRecuperato")

                        // ✅ Assicurati di osservare LiveData nel thread principale
                        withContext(Dispatchers.Main) {
                            val anno:Int = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                            Log.d("OrarioLezioniDEBUG", "Anno corso : $anno")
                            var semestre: Int = 1
                            if(calendar.get(Calendar.MONTH) >= 3) {
                                semestre = 2
                            }
                            dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)
                                ?.observe(this@OrarioLezioni, Observer { lezioni ->
                                    Log.d("ListaDEBUG", "Numero lezioni ricevute: ${lezioni.size}")
                                    lezioneAdapter.submitList(lezioni)
                                })
                        }
                    } else {
                        Log.e("TasseDEBUG", "Studente non trovato")
                    }
                } catch (e: Exception) {
                    Log.e("TasseDEBUG", "Errore nel recupero studente", e)
                }
            }
        }




        lifecycleScope.launch {
            Log.d("TasseDEBUG", "Inizio query per studente")
            try {
                val studenteRecuperato = withContext(Dispatchers.IO) {
                    dbViewModel.studenteByMatricola(username)
                }
                if (studenteRecuperato != null) {
                    Log.d("TasseDEBUG", "Risultato studente: $studenteRecuperato")

                    // ✅ Assicurati di osservare LiveData nel thread principale
                    withContext(Dispatchers.Main) {
                        val anno:Int = calendar.get(Calendar.YEAR) - studenteRecuperato.annoImmatricolazione
                        Log.d("OrarioLezioniDEBUG", "Anno corso : $anno")
                        var semestre: Int = 1
                        if(calendar.get(Calendar.MONTH) >= 3) {
                            semestre = 2
                        }
                        dbViewModel.getLezioni(giorno, studenteRecuperato.matricola, anno, semestre)
                            ?.observe(this@OrarioLezioni, Observer { lezioni ->
                                Log.d("ListaDEBUG", "Numero lezioni ricevute: ${lezioni.size}")
                                lezioneAdapter.submitList(lezioni)
                            })
                    }
                } else {
                    Log.e("TasseDEBUG", "Studente non trovato")
                }
            } catch (e: Exception) {
                Log.e("TasseDEBUG", "Errore nel recupero studente", e)
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
