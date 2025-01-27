package com.example.progetto

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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
import java.util.Calendar
import java.util.Date

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
        var studente: Studente = Studente( 1, "","","","",0,"",false,false,false,false)
        dbViewModel = DBViewModel(application)
        val username = intent.getIntExtra("username",1)
        val data: Calendar= Calendar.getInstance()
        val mese: Int = data.get(Calendar.MONTH) + 1
        val giorno: Int = data.get(Calendar.DAY_OF_MONTH)
        val anno: Int = data.get(Calendar.YEAR)
        val dataCorrente = "$giorno/$mese/$anno"

        lifecycleScope.launch {

            Log.d("TasseDEBUG", "Inizio query per studente")
            // Esegui la query di database su un thread di I/O
            studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username)!!  // Query al database
            }
            Log.d("TasseDEBUG", "Risultato query: $studente")
            /*// Una volta che la query è completata, torna al main thread per aggiornare la UI
            withContext(Dispatchers.Main) {
            }*/
        }

        val iseeStudente = studente.isee

        val tassa : Double = calcoloTassa(iseeStudente)
        val tassaConMora: Double= tassa*1.05
        val info: TextView = findViewById(R.id.infor)
        info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)


        if (mese == Calendar.SEPTEMBER && giorno>1 && giorno<30){
            Toast.makeText(this, "PRIMO RAMO IF", Toast.LENGTH_LONG).show()
            val primaRataTv: TextView = findViewById(R.id.primarataTV)
            primaRataTv.text="Prima Rata: $tassa euro"
            primaRataTv.visibility=TextView.VISIBLE
            val bottone1 = findViewById<Button>(R.id.bottonePagamento1)
            bottone1.visibility=Button.VISIBLE
            bottone1.setOnClickListener{
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                studente.tassa1=true
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            dbViewModel.inserisciStudente(studente)
                            Log.d("TasseDEBUG", "Studente inserito correttamente")
                        } catch (e: Exception) {
                            Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                        }
                    }
                }
                primaRataTv.visibility= LinearLayout.GONE
                bottone1.visibility=Button.GONE
                info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                val sep1: View = findViewById(R.id.sep1)
                sep1.visibility=View.GONE
            }
        }else if (mese == Calendar.OCTOBER && giorno>1 && giorno<30){
            Toast.makeText(this, "SECONDO RAMO IF", Toast.LENGTH_LONG).show()
            if (!studente.tassa1){
                val primaRataTv: TextView = findViewById(R.id.primarataTV)
                primaRataTv.text="Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility=TextView.VISIBLE
                val bottone1 = findViewById<Button>(R.id.bottonePagamento1)
                bottone1.visibility=Button.VISIBLE
                bottone1.setOnClickListener{
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    studente.tassa1=true
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                dbViewModel.inserisciStudente(studente)
                                Log.d("TasseDEBUG", "Studente inserito correttamente")
                            } catch (e: Exception) {
                                Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                            }
                        }
                    }
                    primaRataTv.visibility=LinearLayout.GONE
                    bottone1.visibility= Button.GONE
                    info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                    val sep1: View = findViewById(R.id.sep1)
                    sep1.visibility=View.GONE
                }
            }
            val secondaRataTv: TextView = findViewById(R.id.secondaTV)
            secondaRataTv.text="Seconda Rata: $tassa euro"
            secondaRataTv.visibility=TextView.VISIBLE
            val bottone2 = findViewById<Button>(R.id.bottonePagamento2)
            bottone2.visibility=Button.VISIBLE
            bottone2.setOnClickListener {
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                studente.tassa2=true
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            dbViewModel.inserisciStudente(studente)
                            Log.d("TasseDEBUG", "Studente inserito correttamente")
                        } catch (e: Exception) {
                            Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                        }
                    }
                }
                secondaRataTv.visibility = LinearLayout.GONE
                bottone2.visibility= Button.GONE
                info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                val sep2: View = findViewById(R.id.sep2)
                sep2.visibility=View.GONE
            }

        }else if (mese == Calendar.FEBRUARY && giorno>1 && giorno<28){
            Toast.makeText(this, "TERZO RAMO IF", Toast.LENGTH_LONG).show()
            if (! studente.tassa1 && ! studente.tassa2){
                val primaRataTv: TextView = findViewById(R.id.primarataTV)
                primaRataTv.text="Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility= TextView.GONE
                val bottone1 = findViewById<Button>(R.id.bottonePagamento1)
                bottone1.visibility=Button.VISIBLE
                bottone1.setOnClickListener{
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    studente.tassa1=true
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                dbViewModel.inserisciStudente(studente)
                                Log.d("TasseDEBUG", "Studente inserito correttamente")
                            } catch (e: Exception) {
                                Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                            }
                        }
                    }
                    primaRataTv.visibility=LinearLayout.GONE
                    bottone1.visibility=Button.GONE
                    info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                    val sep1: View = findViewById(R.id.sep1)
                    sep1.visibility=View.GONE
                }
                val secondaRataTv: TextView = findViewById(R.id.secondaTV)
                secondaRataTv.text="Seconda Rata con mora: $tassaConMora euro"
                secondaRataTv.visibility=TextView.VISIBLE
                val bottone2 = findViewById<Button>(R.id.bottonePagamento2)
                bottone2.visibility=Button.VISIBLE
                bottone2.setOnClickListener {
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    studente.tassa2=true
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                dbViewModel.inserisciStudente(studente)
                                Log.d("TasseDEBUG", "Studente inserito correttamente")
                            } catch (e: Exception) {
                                Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                            }
                        }
                    }
                    secondaRataTv.visibility = LinearLayout.GONE
                    bottone2.visibility=Button.GONE
                    info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                    val sep2: View = findViewById(R.id.sep2)
                    sep2.visibility=View.GONE
                }
            }
            val terzaRataTv: TextView=findViewById(R.id.terzarataTV)
            terzaRataTv.text="Terza Rata: $tassa euro"
            terzaRataTv.visibility=TextView.VISIBLE
            val bottone3 = findViewById<Button>(R.id.bottonePagamento3)
            bottone3.visibility=Button.VISIBLE
            bottone3.setOnClickListener {
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                studente.tassa3=true
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            dbViewModel.inserisciStudente(studente)
                            Log.d("TasseDEBUG", "Studente inserito correttamente")
                        } catch (e: Exception) {
                            Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                        }
                    }
                }
                terzaRataTv.visibility = LinearLayout.GONE
                bottone3.visibility=Button.GONE
                info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                val sep3: View = findViewById(R.id.sep3)
                sep3.visibility=View.GONE
            }
        }else if (mese== Calendar.MAY && giorno>1 && giorno<31){
            Toast.makeText(this, "QUARTO RAMO IF", Toast.LENGTH_LONG).show()
            if (! studente.tassa1 && ! studente.tassa2 && !studente.tassa3){
                val primaRataTv: TextView = findViewById(R.id.primarataTV)
                primaRataTv.visibility=TextView.VISIBLE
                primaRataTv.text="Prima Rata con mora: $tassaConMora euro"
                val bottone1 = findViewById<Button>(R.id.bottonePagamento1)
                bottone1.visibility=Button.VISIBLE
                bottone1.setOnClickListener{
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    studente.tassa1=true
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                dbViewModel.inserisciStudente(studente)
                                Log.d("TasseDEBUG", "Studente inserito correttamente")
                            } catch (e: Exception) {
                                Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                            }
                        }
                    }
                    primaRataTv.visibility=LinearLayout.GONE
                    bottone1.visibility=Button.GONE
                    info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                    val sep1: View = findViewById(R.id.sep1)
                    sep1.visibility=View.GONE

                }
                val secondaRataTv: TextView = findViewById(R.id.secondaTV)
                secondaRataTv.text="Seconda Rata con mora: $tassaConMora euro"
                secondaRataTv.visibility=TextView.VISIBLE
                val bottone2 = findViewById<Button>(R.id.bottonePagamento2)
                bottone2.visibility=Button.VISIBLE
                bottone2.setOnClickListener {
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    studente.tassa2=true
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                dbViewModel.inserisciStudente(studente)
                                Log.d("TasseDEBUG", "Studente inserito correttamente")
                            } catch (e: Exception) {
                                Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                            }
                        }
                    }
                    secondaRataTv.visibility = LinearLayout.GONE
                    bottone2.visibility=Button.GONE
                    info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                    val sep2: View = findViewById(R.id.sep2)
                    sep2.visibility=View.GONE
                }
                val terzaRataTv: TextView=findViewById(R.id.terzarataTV)
                terzaRataTv.text="Terza Rata con mora: $tassaConMora euro"
                terzaRataTv.visibility= TextView.VISIBLE
                val bottone3 = findViewById<Button>(R.id.bottonePagamento3)
                bottone3.visibility=Button.VISIBLE
                bottone3.setOnClickListener {
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    studente.tassa3=true
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            try {
                                dbViewModel.inserisciStudente(studente)
                                Log.d("TasseDEBUG", "Studente inserito correttamente")
                            } catch (e: Exception) {
                                Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                            }
                        }
                    }
                    terzaRataTv.visibility = LinearLayout.GONE
                    bottone3.visibility=Button.GONE
                    info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
                    val sep3: View = findViewById(R.id.sep3)
                    sep3.visibility=View.GONE
                }
            }
            val quartaRataTv: TextView=findViewById(R.id.quartarataTV)
            quartaRataTv.text="Quarta Rata: $tassa euro"
            quartaRataTv.visibility=TextView.VISIBLE
            val bottone4 = findViewById<Button>(R.id.bottonePagamento4)
            bottone4.visibility=Button.VISIBLE
            bottone4.setOnClickListener {
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                studente.tassa4=true
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        try {
                            dbViewModel.inserisciStudente(studente)
                            Log.d("TasseDEBUG", "Studente inserito correttamente")
                        } catch (e: Exception) {
                            Log.e("TasseDEBUG", "Errore durante l'inserimento degli studenti", e)
                        }
                    }
                }
                quartaRataTv.visibility = LinearLayout.GONE
                bottone4.visibility=Button.GONE
                info.text= testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
            }
        }
    }

    private fun testoInfo(
        bool1: Boolean,
        bool2: Boolean,
        bool3: Boolean,
        bool4: Boolean
    ): String {
        if (!bool1 && !bool2 && !bool3 && !bool4){
         return "Nessuna rata ancora pagata!"
        }
        var info: StringBuilder= StringBuilder()
        if (bool1){
            info.append("Prima Rata pagata\n")
            info.append("\n")
        }
        if (bool2){
            info.append("Seconda Rata pagata\n")
            info.append("\n")
        }
        if (bool3){
            info.append("Terza Rata pagata\n")
            info.append("\n")
        }
        if (bool4){
            info.append("Quarta Rata pagata\n")
            info.append("\n")
        }
        return info.toString()
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