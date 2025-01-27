package com.example.progetto

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
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
        var studente: Studente? = null
        dbViewModel = DBViewModel(application)
        val username = intent.getIntExtra("username",1)
        val data: Calendar= Calendar.getInstance()
        val mese: Int = data.get(Calendar.MONTH) + 1
        val giorno: Int = data.get(Calendar.DAY_OF_MONTH)
        val anno: Int = data.get(Calendar.YEAR)
        val dataCorrente = "$giorno/$mese/$anno"
        var counter=0

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
        val tassaConMora: Double= tassa*1.05

        if (mese == Calendar.SEPTEMBER && giorno>1 && giorno<30){
            val primaRataTv: TextView = findViewById(R.id.primarataTV)
            primaRataTv.visibility=TextView.VISIBLE
            primaRataTv.text="Prima Rata: $tassa euro"
            val bottone = findViewById<Button>(R.id.bottonePagamento)
            bottone.visibility=Button.VISIBLE
            bottone.setOnClickListener{
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                counter++
                primaRataTv.visibility= LinearLayout.GONE
                bottone.visibility=Button.GONE
            }
        }else if (mese == Calendar.OCTOBER && giorno>1 && giorno<30){
            if (counter<1){
                val primaRataTv: TextView = findViewById(R.id.primarataTV)
                primaRataTv.text="Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility=TextView.VISIBLE
                val bottone = findViewById<Button>(R.id.bottonePagamento)
                bottone.visibility=Button.VISIBLE
                bottone.setOnClickListener{
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    counter++
                    primaRataTv.visibility=LinearLayout.GONE
                    bottone.visibility= Button.GONE
                }
            }
            val secondaRataTv: TextView = findViewById(R.id.secondaTV)
            secondaRataTv.text="Seconda Rata: $tassa euro"
            secondaRataTv.visibility=TextView.VISIBLE
            val bottone = findViewById<Button>(R.id.bottonePagamento)
            bottone.visibility=Button.VISIBLE
            bottone.setOnClickListener {
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                counter++
                secondaRataTv.visibility = LinearLayout.GONE
                bottone.visibility= Button.GONE
            }

        }else if (mese == Calendar.FEBRUARY && giorno>1 && giorno<28){
            if (counter<2){
                val primaRataTv: TextView = findViewById(R.id.primarataTV)
                primaRataTv.text="Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility= TextView.GONE
                val bottone1 = findViewById<Button>(R.id.bottonePagamento)
                bottone1.visibility=Button.VISIBLE
                bottone1.setOnClickListener{
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    counter++
                    primaRataTv.visibility=LinearLayout.GONE
                    bottone1.visibility=Button.GONE
                }
                val secondaRataTv: TextView = findViewById(R.id.secondaTV)
                secondaRataTv.text="Seconda Rata: $tassaConMora euro"
                secondaRataTv.visibility=TextView.VISIBLE
                val bottone2 = findViewById<Button>(R.id.bottonePagamento)
                bottone2.visibility=Button.VISIBLE
                bottone2.setOnClickListener {
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    counter++
                    secondaRataTv.visibility = LinearLayout.GONE
                    bottone2.visibility=Button.GONE
                }
            }
            val terzaRataTv: TextView=findViewById(R.id.terzarataTV)
            terzaRataTv.text="Terza Rata: $tassa euro"
            terzaRataTv.visibility=TextView.VISIBLE
            val bottone = findViewById<Button>(R.id.bottonePagamento)
            bottone.visibility=Button.VISIBLE
            bottone.setOnClickListener {
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                counter++
                terzaRataTv.visibility = LinearLayout.GONE
                bottone.visibility=Button.GONE
            }
        }else if (mese== Calendar.MAY && giorno>1 && giorno<31){
            if (counter<3){
                val primaRataTv: TextView = findViewById(R.id.primarataTV)
                primaRataTv.visibility=TextView.VISIBLE
                primaRataTv.text="Prima Rata con mora: $tassaConMora euro"
                val bottone1 = findViewById<Button>(R.id.bottonePagamento)
                bottone1.visibility=Button.VISIBLE
                bottone1.setOnClickListener{
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    counter++
                    primaRataTv.visibility=LinearLayout.GONE
                }
                val secondaRataTv: TextView = findViewById(R.id.secondaTV)
                secondaRataTv.text="Seconda Rata: $tassaConMora euro"
                secondaRataTv.visibility=TextView.VISIBLE
                val bottone2 = findViewById<Button>(R.id.bottonePagamento)
                bottone2.visibility=Button.VISIBLE
                bottone2.setOnClickListener {
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    counter++
                    secondaRataTv.visibility = LinearLayout.GONE
                }
                val terzaRataTv: TextView=findViewById(R.id.terzarataTV)
                terzaRataTv.text="Terza Rata: $tassaConMora euro"
                terzaRataTv.visibility= TextView.VISIBLE
                val bottone3 = findViewById<Button>(R.id.bottonePagamento)
                bottone3.visibility=Button.VISIBLE
                bottone3.setOnClickListener {
                    Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                    counter++
                    terzaRataTv.visibility = LinearLayout.GONE
                    bottone3.visibility=Button.GONE
                }
            }
            val quartaRataTv: TextView=findViewById(R.id.quartarataTV)
            quartaRataTv.text="Quarta Rata: $tassa euro"
            quartaRataTv.visibility=TextView.VISIBLE
            val bottone = findViewById<Button>(R.id.bottonePagamento)
            bottone.visibility=Button.VISIBLE
            bottone.setOnClickListener {
                Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
                counter++
                quartaRataTv.visibility = LinearLayout.GONE
                bottone.visibility=Button.GONE
            }
        }

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