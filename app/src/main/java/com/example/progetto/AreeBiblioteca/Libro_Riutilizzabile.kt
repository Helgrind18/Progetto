package com.example.progetto.AreeBiblioteca

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Libro
import com.example.progetto.R
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Libro_Riutilizzabile : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_libro_riutilizzabile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nome= intent.getStringExtra("nome")
        val autore= intent.getStringExtra("autore")
        val matricola= intent.getIntExtra("username",0)
        Log.d("LibroDebu", "Recupero della matricola: $matricola")
        val nomeLibro: TextView = findViewById(R.id.nomeLibro)
        nomeLibro.text = nome

        dbViewModel = DBViewModel(application)
        val testoNome: TextView = findViewById(R.id.TestoNome)
        testoNome.text = nome
        val autoreTV: TextView = findViewById(R.id.TestoAutore)
        autoreTV.text = autore
        var libro: Libro=Libro("","","",0,"")
        lifecycleScope.launch {
            try {
                libro = withContext(Dispatchers.IO) {
                    dbViewModel.getLibroByNomeAutore(nome.toString(), autore.toString())!!
                }
                val settoreTv: TextView= findViewById(R.id.TestoSettore)
                settoreTv.text=libro.settore
                val sinossiTv: TextView= findViewById(R.id.TestoSinossi)
                sinossiTv.text=libro.sinossi
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //TODO: Da gestire, l'app crasha se clicco

            val bottoneRichiesta: TextView= findViewById(R.id.bottoneRichiesta)
            bottoneRichiesta.setOnClickListener {
                lifecycleScope.launch {
                     withContext(Dispatchers.IO) {
                         try {
                             libro.matricolaStudente=matricola
                             Log.d("LibroDebu", "Libro: $libro")
                             dbViewModel.aggiungiLibro(libro)
                         }catch (e: Exception){
                             e.printStackTrace()
                         }

                    }
                }
            }
        }
    }
}