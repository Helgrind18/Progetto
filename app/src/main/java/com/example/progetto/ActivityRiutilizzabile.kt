package com.example.progetto

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Corso
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ActivityRiutilizzabile : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riutilizzabile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbViewModel = DBViewModel(application)
        val nomeCorso: TextView = findViewById(R.id.nomeCorso)
        nomeCorso.text = intent.getStringExtra("nome")
        var corso: Corso = Corso(0,"",0,0,0,"")
        val id = intent.getIntExtra("id", 1)
        lifecycleScope.launch {
            Log.d("ActivityRiutilizzabileDEBUG", "Inizio query ")
            try {
                corso = withContext(Dispatchers.IO) {
                    dbViewModel.getCorsoById(id)!!
                }
                val semestre: TextView = findViewById(R.id.TestoSemestre)
                semestre.text = corso.semestre.toString()
                val anno: TextView = findViewById(R.id.TestoAnno)
                anno.text = corso.anno.toString()
                val cfu: TextView= findViewById(R.id.TestoCFU)
                cfu.text = corso.CFU.toString()
                val desc: TextView= findViewById(R.id.TestoDesc)
                desc.text = corso.descrizione

                Log.d("ActivityRiutilizzabileDEBUG", "Risultato corso: $corso")
            }catch (e: Exception){
                Log.e("ActivityRiutilizzabileDEBUG", "Errore nel recupero corso", e)
            }
        }

    }

    private fun descrizioneCorso(corso: Corso): String {
        var ris: StringBuilder = StringBuilder()
        ris.append("CFU: ${corso.CFU}")
        ris.append("\n")
        ris.append("CFU: ${corso.id}")
        ris.append("\n")
        ris.append("Descrizione: ${corso.descrizione}")
        ris.append("\n")
        return ris.toString()
    }
}