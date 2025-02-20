package com.example.progetto

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.progetto.Entity.Schemi.Piatto
import com.example.progetto.Entity.Schemi.Studente
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlin.random.Random

class Mensa : AppCompatActivity() {

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

        // Usiamo uno studente "dummy" per recuperare successivamente le informazioni dello studente tramite la sua matricola
        var studente: Studente = Studente(1, "", "", "", "", 0, "", 0,false, false, false, false,0)
        val username = intent.getIntExtra("username", 1)
        dbViewModel = DBViewModel(application)
        // valore da usare per richiedere l'intent alla fotocamerna, come letto sulla documentazione
        val REQUEST_IMAGE_CAPTURE = 1

        // Creazione di un oggetto Calendar per ottenere la data corrente, serviranno per la generazione del seed
        val calendar= Calendar.getInstance()
        val mese= calendar.get(Calendar.MONTH)
        val giorno= calendar.get(Calendar.DAY_OF_MONTH)
        val anno= calendar.get(Calendar.YEAR)
        // Generazione del seed per il random, in questo modo, mi assicuròc he ogni giorno avrò una combinazione diversa di piatti
        // Con questo stratagemma, ogni volta che apro l'Activity nella stessa giornata, avrò sempre lo steso menù
        val seed = mese+giorno+anno
        val random: Random= Random(seed)
        // impongo seed come parametro della funzione random per imporre un upper bound e un lower bound (implicito) pari a 0, come descritto nella documentazione
        val ris= random.nextInt(seed)
        Log.d("TasseDEBUG", "Risultato random: $ris")


        // genero liste vuote per i piatti

        var listaPrimi: List<Piatto> = emptyList()
        var listaSecondi: List<Piatto> = emptyList()
        var listaContorni: List<Piatto> = emptyList()

        lifecycleScope.launch {
            Log.d("TasseDEBUG", "Inizio query per studente")
            // Eseguo la query di database su un thread di I/O
            studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username)!!  // Query al database
            }
            Log.d("TasseDEBUG", "Risultato query: $studente")

            val iseeStudente = studente.isee
            //Calcolo quanto deve pagare lo studente per mangiare a mensa, secondo i valori di Isee come mostrato nel sito dell'Università
            val costo: Double = calcolaCostoMensa(iseeStudente)
            val info: TextView = findViewById(R.id.infoUtente)
            info.text = testoInfo(studente, costo)



            // Aggiorno le liste vuote create in precedenza, ognuna con il sio ripettivo tipo di piatto

            listaPrimi= withContext(Dispatchers.IO) {
                dbViewModel.getPiattiByTipo(1)!!
            }
            Log.d("TasseDEBUG", "Lista primi: $listaPrimi")
            listaSecondi= withContext(Dispatchers.IO) {
                dbViewModel.getPiattiByTipo(2)!!
            }
            Log.d("TasseDEBUG", "Lista secondi: $listaSecondi")
            listaContorni= withContext(Dispatchers.IO) {
                dbViewModel.getPiattiByTipo(3)!!
            }
            Log.d("TasseDEBUG", "Lista contorni: $listaContorni")



            // Prendo il piatto "del giorno" effettuando la divisione intera tra il valore ottenuto con Random e la lunghezza della lista, così da non sforare mai la dimensione
            val primo= listaPrimi[ris%listaPrimi.size]
            Log.d("TasseDEBUG", "Primo: $primo")
            val secondo= listaSecondi[ris%listaSecondi.size]
            Log.d("TasseDEBUG", "Secondo: $secondo")
            val contorno= listaContorni[ris%listaContorni.size]
            Log.d("TasseDEBUG", "Contorno: $contorno")


            // Aggiorno le informazioni dei Layout
            val menu: TextView= findViewById(R.id.menu)
            menu.text=mostraMenu(primo, secondo, contorno)
            val bottonePagamento1: Button= findViewById(R.id.bottone)
            // Configura i bottoni di pagamento
            bottonePagamento1.setOnClickListener {
                dispatchTakePictureIntent(REQUEST_IMAGE_CAPTURE)
                studente.pastiEffettuati++
                dbViewModel.inserisciStudente(studente)
                info.text = testoInfo(studente, costo)
                Toast.makeText(this@Mensa, "Pagamento effettuato\n Buon appetito!", Toast.LENGTH_SHORT).show()
            }

        }

    }


    // Funzione per la corretta creazione del testo da mostrare a schermo
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

    // Funzione per il calcolo del costo del ticket mensa
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


    // Funzione per invocare l'intent alla fotocamera, come mostrato sulla documentazione
    private fun dispatchTakePictureIntent(request: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, request)
        } catch (e: ActivityNotFoundException) {
        }
    }

    // Funzione per la corretta creazione del testo dei piatti del giorno da mostrare a schermo
    private fun mostraMenu(primo: Piatto, secondo: Piatto, contorno: Piatto): String{
        var ris: StringBuilder= StringBuilder()
        ris.append("PRIMO PIATTO: ${primo.nome}")
        ris.append("\n")
        ris.append("SECONDO PIATTO: ${secondo.nome}")
        ris.append("\n")
        ris.append("CONTORNO: ${contorno.nome}")
        ris.append("\n")
        return ris.toString()
    }

}

