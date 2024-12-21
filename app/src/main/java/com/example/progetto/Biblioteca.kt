package com.example.progetto

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.dataBase.DBViewModel
import com.example.progetto.Entity.Libro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Biblioteca : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biblioteca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbViewModel = DBViewModel(application)
          // Inizio inserimento libri
        val libro1 = Libro(name = "Il Signore degli Anelli", autore = "J.R.R. Tolkien", settore = "Fantasy")
        val libro2 = Libro(name = "Orgoglio e Pregiudizio", autore = "Jane Austen", settore = "Romanzo")
        val libro3 = Libro(name = "Le Cronache del Ghiaccio e del Fuoco: Il Trono di Spade", autore = "George R.R. Martin", settore = "Fantasy")
        val libro4 = Libro(name = "1984", autore = "George Orwell", settore = "Distopico")
        val libro5 = Libro(name = "Il Piccolo Principe", autore = "Antoine de Saint-Exupéry", settore = "Infanzia")
        val libro6 = Libro(name = "Delitto e Castigo", autore = "Fëdor Dostoevskij", settore = "Classico")
        val libro7 = Libro(name = "Cime tempestose", autore = "Emily Brontë", settore = "Gotico")
        val libro8 = Libro(name = "L'Odissea", autore = "Omero", settore = "Classico")
        val libro9 = Libro(name = "Moby Dick", autore = "Herman Melville", settore = "Avventura")
        val libro10 = Libro(name = "Il Processo", autore = "Franz Kafka", settore = "Modernismo")
        val libro11 = Libro(name = "Dieci piccoli indiani", autore = "Agatha Christie", settore = "Giallo")
        val libro12 = Libro(name = "Il Nome della Rosa", autore = "Umberto Eco", settore = "Storico")
        val libro13 = Libro(name = "Neuromante", autore = "William Gibson", settore = "Cyberpunk")
        val libro14 = Libro(name = "Fondazione", autore = "Isaac Asimov", settore = "Fantascienza")
        val libro15 = Libro(name = "Dune", autore = "Frank Herbert", settore = "Fantascienza")

        /*lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                dbViewModel.aggiungiLibro(libro1)
                dbViewModel.aggiungiLibro(libro2)
                dbViewModel.aggiungiLibro(libro3)
                dbViewModel.aggiungiLibro(libro4)
                dbViewModel.aggiungiLibro(libro5)
                dbViewModel.aggiungiLibro(libro6)
                dbViewModel.aggiungiLibro(libro7)
                dbViewModel.aggiungiLibro(libro8)
                dbViewModel.aggiungiLibro(libro9)
                dbViewModel.aggiungiLibro(libro10)
                dbViewModel.aggiungiLibro(libro11)
                dbViewModel.aggiungiLibro(libro12)
                dbViewModel.aggiungiLibro(libro13)
                dbViewModel.aggiungiLibro(libro14)
                dbViewModel.aggiungiLibro(libro15)
            }
            withContext(Dispatchers.Main) {
                // Mostra un messaggio di conferma
                Toast.makeText(this@Biblioteca, "Benvenuto nell'Area Biblioteca", Toast.LENGTH_SHORT).show()
            }
        }*/


    }
}