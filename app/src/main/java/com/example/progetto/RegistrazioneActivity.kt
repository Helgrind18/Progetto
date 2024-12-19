package com.example.progetto

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.progetto.Entity.Studente
import com.example.progetto.dataBase.DataBaseApp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrazioneActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrazione)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Questo serve per la creazione del singolo utente
        sharedPreferences = getSharedPreferences("DbLogin", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        //Recupero i dati dall'activity
        val matricolaEditText: EditText = findViewById(R.id.editMatricola)
        val cfEditText: EditText = findViewById(R.id.editCF)
        val pswdEditText: EditText = findViewById(R.id.editPswd)
        val nomeEditText: EditText = findViewById(R.id.editNome)
        val cognomeEditText: EditText = findViewById(R.id.editCognome)
        val iseeEditText: EditText = findViewById(R.id.editISEE)
        val emailEditText: EditText = findViewById(R.id.editEmail)
        val bottoneInvia: Button = findViewById(R.id.bottoneInvia)

        val matricola: Int = matricolaEditText.text.toString().toInt() // Parse to Int
        val cf: String = cfEditText.text.toString().trim()
        val pswd: String = pswdEditText.text.toString().trim()
        val nome: String = nomeEditText.text.toString().trim()
        val cognome: String = cognomeEditText.text.toString().trim()
        val isee: Long = iseeEditText.text.toString().toLong() // Parse to Long
        val email: String = emailEditText.text.toString().trim()
        val studente = Studente(matricola, cf, pswd, nome, cognome, isee, email)
        if(!(studenteCorretto(studente))){
            Toast.makeText(this,"Parametri errati", Toast.LENGTH_SHORT).show()
        }

        //definisco il db e lo inizializzo
        val db = Room.databaseBuilder(this, DataBaseApp::class.java, DataBaseApp.NAME).build()
        //In questo caso ho bisogno solo del DAO dello studente per salvare / recuperare lo studente
        val studenteDao = db.getStudenteDao()
        CoroutineScope(Dispatchers.IO).launch {
            studenteDao.inserisciStudente(studente)
        }

        //Una volta salvato l'utente passo ad HomeActivity
        bottoneInvia.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {  }
            startActivity(intent)
        }

    }

    private fun studenteCorretto(studente: Studente): Boolean {
        if(studente.matricola <= 0){
            return false
        }
        if(studente.CF.isEmpty()){
            return false
        }
        if(studente.pswd.isEmpty()) {
            return false
        }
        if(studente.nome.isEmpty()){
            return false
        }
        if(studente.cognome.isEmpty()){
            return false
        }
        if(studente.ISEE <= 0){
            return false
        }
        if(studente.email.isEmpty()) {
            return false
        }
        if(!studente.email.contains("@")){
            return false
        }
        return true
    }
}