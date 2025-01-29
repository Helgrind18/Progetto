package com.example.progetto

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Corso
import com.example.progetto.Entity.RelazioneStudenteCorso
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Libretto : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libretto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbViewModel = DBViewModel(application)
        var lista: List<RelazioneStudenteCorso> = emptyList()
        val username= intent.getIntExtra("username",1)

        lifecycleScope.launch {
            Log.d("TasseDEBUG", "Inizio query per studente")
            // Esegui la query di database su un thread di I/O
            lista = withContext(Dispatchers.IO) {
                dbViewModel.getEsamiDiStudente(username)!!  // Query al database
            }
            Log.d("TasseDEBUG", "Risultato query: $lista")


            val media: Double = calcolaMedia(lista)
            val mediaPonderata: Double= calcolaMediaPonderata(lista, dbViewModel)
            Log.d("MEDIE", "MEDIE CALCOLATE $media, $mediaPonderata" )
            val info: TextView = findViewById(R.id.infoEsame)
            info.text = testoInfo(username, media, mediaPonderata, lista)

            /*
            gestisciProgressBar(lista.size)

             */
        }

    }

    private fun calcolaMedia(lista: List<RelazioneStudenteCorso>): Double {
        var somma = 0.0
        for (c in lista) {
            somma += c.voto
        }
        return somma / lista.size
    }

    private fun calcolaMediaPonderata(lista: List<RelazioneStudenteCorso>, dbViewModel: DBViewModel): Double {
        var somma: Double = 0.0
        var sommaCFU: Double= 0.0
        for (c in lista) {
            val cfu: Int= trovaCFU(c.corsoId, dbViewModel)
            if (cfu==0){
                Log.d("ZERO", "C'è uno zero in corrispondenza di ${c.corsoId}")
            }
            somma += c.voto * cfu
            sommaCFU+= cfu
        }
        return (somma/sommaCFU)
    }

    private fun trovaCFU(id: Int, db: DBViewModel): Int{
        var cfu: Int= 0
        lifecycleScope.launch{
            withContext(Dispatchers.IO) {
                cfu = withContext(Dispatchers.IO) {
                    db.getCorsoById(id)!!.CFU // Query al database
                }
                Log.d("ZEROCFU", "Risultato query: $cfu con corso $id")
            }
        }
        return cfu

    }

    private fun testoInfo(username: Int, media: Double, mediaPonderata: Double, lista: List<RelazioneStudenteCorso>): String {
     var ris: StringBuilder= StringBuilder()
     ris.append("Esami superati: ${lista.size}")
     ris.append("Media: $media")
     ris.append("Media ponderata: $mediaPonderata")
    return ris.toString()
    }

    private fun gestisciProgressBar(size: Int){
        val progress: ProgressBar= findViewById(R.id.progresso)
        progress.visibility = ProgressBar.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            progress.visibility = View.GONE
            progress.progress=size
        }, 100)
    }
}