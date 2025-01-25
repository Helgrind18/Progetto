package com.example.progetto

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

class Tasse : AppCompatActivity() {
    private lateinit var dbViewModel: DBViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tasse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Versione grezza senza CFU
        var studente: Studente? = null
        dbViewModel = DBViewModel(application)
        val username = intent.getIntExtra("username",1)
        lifecycleScope.launch {

            Log.d("TasseDEBUG", "Inizio query per studente")
            // Esegui la query di database su un thread di I/O
            studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username)  // Query al database
            }
            Log.d("TasseDEBUG", "Risultato query: $studente")


            /*// Una volta che la query è completata, torna al main thread per aggiornare la UI
            withContext(Dispatchers.Main) {

            }*/
        }

        val iseeStudente = studente?.isee

        val tassa : Double = calcoloTassa(iseeStudente)





    //TODO: Versione coi CFU, in base ad un quantitativo di CFU scende la rata di una percentuale
    //Eventualmente, con un grande numero di CFU si annulla completamente


    }

    private fun calcoloTassa(iseeStudente: Long?): Double {
        if (iseeStudente != null) {
            if(iseeStudente <= 27726.79){
                return 130.00
            }
            if(27726.80 <= iseeStudente && iseeStudente <= 55453.58){
                return 140.00
            }
            return 160.00
        }
        return -1.00
    }

}