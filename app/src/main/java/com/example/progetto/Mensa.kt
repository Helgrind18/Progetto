package com.example.progetto

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Studente
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Mensa : AppCompatActivity() {
    //TODO: implementazione scan qr e stampa menù giornaliero, mostrare anche, per l'utente, quanti pasti ha fatto
    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mensa)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var studente: Studente = Studente(1, "", "", "", "", 0, "", 0,false, false, false, false,0)
        val username = intent.getIntExtra("username", 1)
        dbViewModel = DBViewModel(application)
        val REQUEST_IMAGE_CAPTURE = 1

        lifecycleScope.launch {
            Log.d("TasseDEBUG", "Inizio query per studente")
            // Esegui la query di database su un thread di I/O
            studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username)!!  // Query al database
            }
            Log.d("TasseDEBUG", "Risultato query: $studente")

            val iseeStudente = studente.isee
            val costo: Double = calcolaCostoMensa(iseeStudente)
            val info: TextView = findViewById(R.id.infoUtente)
            info.text = testoInfo(studente, costo)

            // Configura i bottoni di pagamento
            val menu: TextView= findViewById(R.id.menu)
            menu.text="Prova,\n ci vuole il random"
            val bottonePagamento1: Button= findViewById(R.id.bottone)
            bottonePagamento1.setOnClickListener {
                dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE)
                studente.pastiEffettuati++
                dbViewModel.inserisciStudente(studente)
                info.text = testoInfo(studente, costo)
            }

        }

    }



    private fun testoInfo(studente: Studente, costo: Double): String{
        var ris: StringBuilder= StringBuilder()
        ris.append("Matricola: ${studente.matricola}")
        ris.append("\n")
        ris.append("Pasti Usufruiti: ${studente.pastiEffettuati}")
        ris.append("\n")
        ris.append("Costo Menù: $costo€")
        ris.append("\n")
        return ris.toString()
    }

    private fun calcolaCostoMensa(iseeStudente: Long): Double {
        return if (iseeStudente>=0 && iseeStudente<=6000) {
            1.70
        }else if (iseeStudente>6000 && iseeStudente<=10000) {
            2.40
        }else if (iseeStudente>10000 && iseeStudente<=16000) {
            3.00
        }else if (iseeStudente>16000 && iseeStudente<=26500) {
            3.70
        }else
            4.40
    }

    private fun dispatchTakePictureIntent(request: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, request)
        } catch (e: ActivityNotFoundException) {
        }
    }

}

