package com.example.progetto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.AreeBiblioteca.Area_Economica
import com.example.progetto.AreeBiblioteca.Area_Filosofica
import com.example.progetto.AreeBiblioteca.Area_Giuridica
import com.example.progetto.AreeBiblioteca.Area_Informatica
import com.example.progetto.AreeBiblioteca.Area_Linguistica
import com.example.progetto.AreeBiblioteca.Area_Matematica
import com.example.progetto.AreeBiblioteca.Area_Pedagogica
import com.example.progetto.AreeBiblioteca.Area_Psicologica
import com.example.progetto.AreeBiblioteca.Area_Storica
import com.example.progetto.dataBase.DBViewModel
import com.example.progetto.Entity.Libro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.progetto.R


class Biblioteca : AppCompatActivity() {

    private lateinit var dbViewModel: DBViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biblioteca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridLayoutBiblioteca)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottoneLinguistica: Button = findViewById(R.id.linguistica)
        val bottoneMatematica: Button = findViewById(R.id.matematica)
        val bottoneInformatica: Button = findViewById(R.id.informatica)
        val bottoneGiuridico: Button = findViewById(R.id.giuridica)
        val bottoneEconomico: Button = findViewById(R.id.economica)
        val bottoneStorico: Button = findViewById(R.id.storica)
        val bottoneFilosofico: Button = findViewById(R.id.filosofica)
        val bottonePsicologico: Button = findViewById(R.id.psicologica)
        val bottonePedagogico: Button = findViewById(R.id.pedagogica)

        dbViewModel = DBViewModel(application)

        // Inizio inserimento libri
        inserisciLibri()

        bottoneLinguistica.setOnClickListener {
            val intent = Intent(this, Area_Linguistica::class.java).apply {
            }
            startActivity(intent)
        }

        bottoneMatematica.setOnClickListener {
            val intent = Intent(this, Area_Matematica::class.java).apply {
            }
            startActivity(intent)
        }

        bottoneInformatica.setOnClickListener {
            val intent = Intent(this, Area_Informatica::class.java).apply {
            }
            startActivity(intent)
        }

        bottoneGiuridico.setOnClickListener {
            val intent = Intent(this, Area_Giuridica::class.java).apply {
            }
            startActivity(intent)
        }

        bottoneEconomico.setOnClickListener {
            val intent = Intent(this, Area_Economica::class.java).apply {
            }
            startActivity(intent)
        }

        bottoneStorico.setOnClickListener {
            val intent = Intent(this, Area_Storica::class.java).apply {
            }
            startActivity(intent)
        }

        bottoneFilosofico.setOnClickListener {
            val intent = Intent(this, Area_Filosofica::class.java).apply {
            }
            startActivity(intent)
        }

        bottonePsicologico.setOnClickListener {
            val intent = Intent(this, Area_Psicologica::class.java).apply {
            }
            startActivity(intent)
        }

        bottonePedagogico.setOnClickListener {
            val intent = Intent(this, Area_Pedagogica::class.java).apply {
            }
            startActivity(intent)
        }
    }

    private fun inserisciLibri() {
        val libro1 = Libro(name = "Il Signore degli Anelli", autore = "J.R.R. Tolkien", settore = "Fantasy")
        val libro2 = Libro(name = "Orgoglio e Pregiudizio", autore = "Jane Austen", settore = "Romanzo")
        val libro3 = Libro(name = "Le Cronache del Ghiaccio e del Fuoco: Il Trono di Spade", autore = "George R.R. Martin", settore = "Fantasy")
        val libro4 = Libro(name = "1984", autore = "George Orwell", settore = "linguistica")
        val libro5 = Libro(name = "Il Piccolo Principe", autore = "Antoine de Saint-Exupéry", settore = "linguistica")
        val libro6 = Libro(name = "Delitto e Castigo", autore = "Fëdor Dostoevskij", settore = "Classico")
        val libro7 = Libro(name = "Cime tempestose", autore = "Emily Brontë", settore = "Gotico")
        val libro8 = Libro(name = "L'Odissea", autore = "Omero", settore = "linguistica")
        val libro9 = Libro(name = "Moby Dick", autore = "Herman Melville", settore = "Avventura")
        val libro10 = Libro(name = "Il Processo", autore = "Franz Kafka", settore = "linguistica")
        val libro11 = Libro(name = "Dieci piccoli indiani", autore = "Agatha Christie", settore = "linguistica")
        val libro12 = Libro(name = "Il Nome della Rosa", autore = "Umberto Eco", settore = "Storico")
        val libro13 = Libro(name = "Neuromante", autore = "William Gibson", settore = "linguistica")
        val libro14 = Libro(name = "Fondazione", autore = "Isaac Asimov", settore = "Fantascienza")
        val libro15 = Libro(name = "Dune", autore = "Frank Herbert", settore = "linguistica")
        val libro16 = Libro(name = "La struttura delle lingue", autore = "Noam Chomsky", settore = "linguistica")
        val libro17 = Libro(name = "Introduzione alla linguistica generale", autore = "Ferdinand de Saussure", settore = "linguistica")
        val libro18 = Libro(name = "Linguistica generale", autore = "Morris Halle", settore = "linguistica")
        val libro19 = Libro(name = "Language and Mind", autore = "Noam Chomsky", settore = "linguistica")
        val libro20 = Libro(name = "Semantica e Pragmatica", autore = "Paul Grice", settore = "linguistica")
        val libro21 = Libro(name = "La lingua come sistema di segni", autore = "Ferdinand de Saussure", settore = "linguistica")
        val libro22 = Libro(name = "Lingua e cultura", autore = "Edward Sapir", settore = "linguistica")
        val libro23 = Libro(name = "I segni del linguaggio", autore = "Charles Peirce", settore = "linguistica")
        val libro24 = Libro(name = "Sintassi delle lingue del mondo", autore = "Joseph Greenberg", settore = "linguistica")
        val libro25 = Libro(name = "Linguistica cognitiva", autore = "George Lakoff", settore = "linguistica")
        val libro26 = Libro(name = "Fonologia e fonetica", autore = "Peter Ladefoged", settore = "linguistica")
        val libro27 = Libro(name = "Lingue e linguaggi", autore = "Roman Jakobson", settore = "linguistica")
        val libro28 = Libro(name = "Evoluzione del linguaggio", autore = "Derek Bickerton", settore = "linguistica")
        val libro29 = Libro(name = "Linguistica comparata", autore = "Franz Bopp", settore = "linguistica")
        val libro30 = Libro(name = "La grammatica universale", autore = "Noam Chomsky", settore = "linguistica")
        val libro31 = Libro(name = "Teoria della comunicazione", autore = "Roman Jakobson", settore = "linguistica")
        val libro32 = Libro(name = "Il significato nelle lingue", autore = "Paul Grice", settore = "linguistica")
        val libro33 = Libro(name = "Linguistica storica", autore = "August Schleicher", settore = "linguistica")
        val libro34 = Libro(name = "Sociolinguistica", autore = "William Labov", settore = "linguistica")
        val libro35 = Libro(name = "Psicolinguistica", autore = "Jean Berko Gleason", settore = "linguistica")
        val libro36 = Libro(name = "Linguaggio e società", autore = "Dell Hymes", settore = "linguistica")
        val libro37 = Libro(name = "Il potere del linguaggio", autore = "Deborah Tannen", settore = "linguistica")
        val libro38 = Libro(name = "Traduzione e linguistica", autore = "Eugene Nida", settore = "linguistica")
        val libro39 = Libro(name = "Grammatica del discorso", autore = "Michael Halliday", settore = "linguistica")
        val libro40 = Libro(name = "Lingue in contatto", autore = "Uriel Weinreich", settore = "linguistica")
        val libro41 = Libro(name = "Semiotica e linguistica", autore = "Umberto Eco", settore = "linguistica")
        val libro42 = Libro(name = "Analisi del testo linguistico", autore = "Teun van Dijk", settore = "linguistica")
        val libro43 = Libro(name = "Lingue artificiali", autore = "John Quijada", settore = "linguistica")
        val libro44 = Libro(name = "Pragmatica linguistica", autore = "Stephen Levinson", settore = "linguistica")
        val libro45 = Libro(name = "Linguaggio e cognizione", autore = "Ray Jackendoff", settore = "linguistica")

        lifecycleScope.launch {
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
                dbViewModel.aggiungiLibro(libro16)
                dbViewModel.aggiungiLibro(libro17)
                dbViewModel.aggiungiLibro(libro18)
                dbViewModel.aggiungiLibro(libro19)
                dbViewModel.aggiungiLibro(libro20)
                dbViewModel.aggiungiLibro(libro21)
                dbViewModel.aggiungiLibro(libro22)
                dbViewModel.aggiungiLibro(libro23)
                dbViewModel.aggiungiLibro(libro24)
                dbViewModel.aggiungiLibro(libro25)
                dbViewModel.aggiungiLibro(libro26)
                dbViewModel.aggiungiLibro(libro27)
                dbViewModel.aggiungiLibro(libro28)
                dbViewModel.aggiungiLibro(libro29)
                dbViewModel.aggiungiLibro(libro30)
                dbViewModel.aggiungiLibro(libro31)
                dbViewModel.aggiungiLibro(libro32)
                dbViewModel.aggiungiLibro(libro33)
                dbViewModel.aggiungiLibro(libro34)
                dbViewModel.aggiungiLibro(libro35)
                dbViewModel.aggiungiLibro(libro36)
                dbViewModel.aggiungiLibro(libro37)
                dbViewModel.aggiungiLibro(libro38)
                dbViewModel.aggiungiLibro(libro39)
                dbViewModel.aggiungiLibro(libro40)
                dbViewModel.aggiungiLibro(libro41)
                dbViewModel.aggiungiLibro(libro42)
                dbViewModel.aggiungiLibro(libro43)
                dbViewModel.aggiungiLibro(libro44)
                dbViewModel.aggiungiLibro(libro45)
            }
            withContext(Dispatchers.Main) {
                // Mostra un messaggio di conferma
                Toast.makeText(this@Biblioteca, "Benvenuto nell'Area Biblioteca", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
