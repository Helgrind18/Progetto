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
import com.example.progetto.Entity.Schemi.Studente
import com.example.progetto.dataBase.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

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

        var studente: Studente = Studente(1, "", "", "", "", 0, "", 0,false, false, false, false,0)
        dbViewModel = DBViewModel(application)
        val username = intent.getIntExtra("username", 1)
        val data: Calendar = Calendar.getInstance()
        val mese: Int = data.get(Calendar.MONTH)


        lifecycleScope.launch {
            Log.d("TasseDEBUG", "Inizio query per studente")
            // Esegui la query di database su un thread di I/O
            studente = withContext(Dispatchers.IO) {
                dbViewModel.studenteByMatricola(username)!!  // Query al database
            }
            Log.d("TasseDEBUG", "Risultato query: $studente")

            val iseeStudente = studente.isee
            val tassa: Double = calcoloTassa(iseeStudente)
            val tassaConMora: Double = tassa * 1.05
            val info: TextView = findViewById(R.id.infor)
            info.text = testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)

            // Configura i bottoni di pagamento
            setupPaymentButtons(studente, tassa, tassaConMora, mese)
        }
    }

    private fun setupPaymentButtons(studente: Studente, tassa: Double, tassaConMora: Double, mese: Int) {
        val bottonePagamento1 = findViewById<Button>(R.id.bottonePagamento1)
        val bottonePagamento2 = findViewById<Button>(R.id.bottonePagamento2)
        val bottonePagamento3 = findViewById<Button>(R.id.bottonePagamento3)
        val bottonePagamento4 = findViewById<Button>(R.id.bottonePagamento4)

        // Gestisci il primo pagamento
        if (mese==Calendar.SEPTEMBER) {
            if (studente.tassa1) {
                bottonePagamento1.visibility = Button.GONE
            } else {
                val primaRataTv: TextView= findViewById(R.id.primarataTV)
                primaRataTv.text = "Prima Rata: $tassa euro"
                primaRataTv.visibility = TextView.VISIBLE
                val sep1: View= findViewById(R.id.sep1)
                sep1.visibility = View.VISIBLE
                bottonePagamento1.visibility = Button.VISIBLE
                bottonePagamento1.setOnClickListener {
                    handlePayment(studente, "tassa1", tassa, bottonePagamento1, primaRataTv, sep1)
                }
            }
        }else if (mese== Calendar.OCTOBER){
            if (studente.tassa1) {
                bottonePagamento1.visibility = Button.GONE
            } else {
                val primaRataTv: TextView= findViewById(R.id.primarataTV)
                primaRataTv.text = "Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility = TextView.VISIBLE
                val sep1: View= findViewById(R.id.sep1)
                sep1.visibility = View.VISIBLE
                bottonePagamento1.visibility = Button.VISIBLE
                bottonePagamento1.setOnClickListener {
                    handlePayment(studente, "tassa1", tassa, bottonePagamento1, primaRataTv,sep1)
                }
            }
            if (studente.tassa2) {
                bottonePagamento2.visibility = Button.GONE
            } else {
                val secondaRataTv: TextView= findViewById(R.id.secondaTV)
                secondaRataTv.text= "Seconda Rata: $tassa euro"
                secondaRataTv.visibility= TextView.VISIBLE
                val sep2: View= findViewById(R.id.sep2)
                sep2.visibility = View.VISIBLE
                bottonePagamento2.visibility = Button.VISIBLE
                bottonePagamento2.setOnClickListener {
                    handlePayment(studente, "tassa2", tassa, bottonePagamento2, secondaRataTv, sep2)
                }
            }
        }else if (mese== Calendar.FEBRUARY){
            if (studente.tassa1) {
                bottonePagamento1.visibility = Button.GONE
            } else {
                val primaRataTv: TextView= findViewById(R.id.primarataTV)
                primaRataTv.text = "Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility = TextView.VISIBLE
                val sep1: View= findViewById(R.id.sep1)
                sep1.visibility = View.VISIBLE
                bottonePagamento1.visibility = Button.VISIBLE
                bottonePagamento1.setOnClickListener {
                    handlePayment(studente, "tassa1", tassa, bottonePagamento1, primaRataTv,sep1)
                }
            }
            if (studente.tassa2) {
                bottonePagamento2.visibility = Button.GONE
            } else {
                val secondaRataTv: TextView= findViewById(R.id.secondaTV)
                secondaRataTv.text= "Seconda Rata con mora: $tassaConMora euro"
                secondaRataTv.visibility= TextView.VISIBLE
                val sep2: View= findViewById(R.id.sep2)
                sep2.visibility = View.VISIBLE
                bottonePagamento2.visibility = Button.VISIBLE
                bottonePagamento2.setOnClickListener {
                    handlePayment(studente, "tassa2", tassa, bottonePagamento2, secondaRataTv, sep2)
                }
            }
            if (studente.tassa3) {
                bottonePagamento3.visibility = Button.GONE
            } else {
                val terzaRataTv: TextView= findViewById(R.id.terzarataTV)
                terzaRataTv.text="Terza Rata: $tassa euro"
                terzaRataTv.visibility= TextView.VISIBLE
                val sep3: View= findViewById(R.id.sep3)
                sep3.visibility = View.VISIBLE
                bottonePagamento3.visibility = Button.VISIBLE
                bottonePagamento3.setOnClickListener {
                    handlePayment(studente, "tassa3", tassa, bottonePagamento3, terzaRataTv,sep3)
                }
            }
        }else if (mese== Calendar.MAY) {
            if (studente.tassa1) {
                bottonePagamento1.visibility = Button.GONE
            } else {
                val primaRataTv: TextView= findViewById(R.id.primarataTV)
                primaRataTv.text = "Prima Rata con mora: $tassaConMora euro"
                primaRataTv.visibility = TextView.VISIBLE
                val sep1: View= findViewById(R.id.sep1)
                sep1.visibility = View.VISIBLE
                bottonePagamento1.visibility = Button.VISIBLE
                bottonePagamento1.setOnClickListener {
                    handlePayment(studente, "tassa1", tassa, bottonePagamento1, primaRataTv, sep1)
                }
            }
            if (studente.tassa2) {
                bottonePagamento2.visibility = Button.GONE
            } else {
                val secondaRataTv: TextView= findViewById(R.id.secondaTV)
                secondaRataTv.text= "Seconda Rata con mora: $tassaConMora euro"
                secondaRataTv.visibility= TextView.VISIBLE
                val sep2: View= findViewById(R.id.sep2)
                sep2.visibility = View.VISIBLE
                bottonePagamento2.visibility = Button.VISIBLE
                bottonePagamento2.setOnClickListener {
                    handlePayment(studente, "tassa2", tassa, bottonePagamento2, secondaRataTv, sep2)
                }
            }
            if (studente.tassa3) {
                bottonePagamento3.visibility = Button.GONE
            } else {
                val terzaRataTv: TextView= findViewById(R.id.terzarataTV)
                terzaRataTv.text="Terza Rata con mora: $tassaConMora euro"
                terzaRataTv.visibility= TextView.VISIBLE
                bottonePagamento3.visibility = Button.VISIBLE
                val sep3: View= findViewById(R.id.sep3)
                sep3.visibility = View.VISIBLE
                bottonePagamento3.setOnClickListener {
                    handlePayment(studente, "tassa3", tassa, bottonePagamento3, terzaRataTv,sep3)
                }
            }
            if (studente.tassa4) {
                bottonePagamento4.visibility = Button.GONE
            } else {
                val quartarataTv: TextView= findViewById(R.id.quartarataTV)
                quartarataTv.text="Quarta Rata: $tassa euro"
                quartarataTv.visibility= TextView.VISIBLE
                val sep3: View= findViewById(R.id.sep3)
                sep3.visibility = View.VISIBLE
                bottonePagamento4.visibility = Button.VISIBLE
                bottonePagamento4.setOnClickListener {
                    handlePayment(studente, "tassa4", tassa, bottonePagamento4, quartarataTv,sep3)
                }
            }
        }
    }

    private fun handlePayment(studente: Studente, tassaName: String, tassaAmount: Double, bottone: Button, textView: TextView, view: View) {
        // Imposta la tassa come pagata nel modello Studente
        when (tassaName) {
            "tassa1" -> studente.tassa1 = true
            "tassa2" -> studente.tassa2 = true
            "tassa3" -> studente.tassa3 = true
            "tassa4" -> studente.tassa4 = true
        }

        // Aggiorna il database
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

        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        progressBar.visibility = ProgressBar.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.GONE
            textView.visibility= TextView.GONE
            bottone.visibility = Button.GONE
            view.visibility= View.GONE
            updateInfo(studente)
            Toast.makeText(this, "Pagamento effettuato", Toast.LENGTH_LONG).show()
        }, 3000)

    }

    private fun updateInfo(studente: Studente) {
        val info: TextView = findViewById(R.id.infor)
        info.text = testoInfo(studente.tassa1, studente.tassa2, studente.tassa3, studente.tassa4)
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
        var info: StringBuilder = StringBuilder()
        if (bool1) {
            info.append("Prima Rata pagata\n")
            info.append("\n")
        }
        if (bool2) {
            info.append("Seconda Rata pagata\n")
            info.append("\n")
        }
        if (bool3) {
            info.append("Terza Rata pagata\n")
            info.append("\n")
        }
        if (bool4) {
            info.append("Quarta Rata pagata\n")
            info.append("\n")
        }
        return info.toString()
    }


    private fun calcoloTassa(iseeStudente: Long?): Double {
        if (iseeStudente != null) {
            if (iseeStudente <= 27726.79) {
                return 130.00
            }
            if (27726.80 <= iseeStudente && iseeStudente <= 55453.58) {
                return 140.00
            }
            return 160.00
        }
        return -1.00
    }

}