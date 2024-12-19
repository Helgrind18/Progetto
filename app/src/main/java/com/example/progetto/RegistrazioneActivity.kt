package com.example.progetto
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
import com.example.progetto.Entity.Studente
import com.example.progetto.dataBase.DBViewModel

class RegistrazioneActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var dbViewModel: DBViewModel // Aggiungi questa riga per il viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrazione)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Recupero i dati dall'activity
        val matricolaEditText: EditText = findViewById(R.id.editMatricola)
        val cfEditText: EditText = findViewById(R.id.editCF)
        val pswdEditText: EditText = findViewById(R.id.editPswd)
        val nomeEditText: EditText = findViewById(R.id.editNome)
        val cognomeEditText: EditText = findViewById(R.id.editCognome)
        val iseeEditText: EditText = findViewById(R.id.editISEE)
        val emailEditText: EditText = findViewById(R.id.editEmail)
        val bottoneInvia: Button = findViewById(R.id.bottoneInvia)

        // Inizializza il ViewModel
        dbViewModel = DBViewModel(application)

        bottoneInvia.setOnClickListener {
            val matricola: Int = matricolaEditText.text.toString().toInt()
            val cf: String = cfEditText.text.toString().trim()
            val pswd: String = pswdEditText.text.toString().trim()
            val nome: String = nomeEditText.text.toString().trim()
            val cognome: String = cognomeEditText.text.toString().trim()
            val isee: Long = iseeEditText.text.toString().toLong()
            val email: String = emailEditText.text.toString().trim()

            val studente = Studente(matricola, cf, pswd, nome, cognome, isee, email)

            if (!studenteCorretto(studente)) {
                Toast.makeText(this, "Parametri errati", Toast.LENGTH_SHORT).show()
            } else {
                //Salvo lo studente
                dbViewModel.inserisciStudente(studente)
                Toast.makeText(this, "Registrazione avvenuta con successo", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("username", matricola)
                }
                startActivity(intent)
            }
        }
    }

    private fun studenteCorretto(studente: Studente): Boolean {
        // Verifica che la matricola sia maggiore di 0
        if (studente.matricola <= 0) {
            Toast.makeText(this, "Matricola non valida. Deve essere un numero positivo.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che il codice fiscale non sia vuoto
        if (studente.CF.isEmpty()) {
            Toast.makeText(this, "Codice fiscale non può essere vuoto.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che la password non sia vuota e abbia almeno 8 caratteri
        if (studente.pswd.isEmpty()) {
            Toast.makeText(this, "Password non valida", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che il nome non sia vuoto
        if (studente.nome.isEmpty()) {
            Toast.makeText(this, "Nome non può essere vuoto.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che il cognome non sia vuoto
        if (studente.cognome.isEmpty()) {
            Toast.makeText(this, "Cognome non può essere vuoto.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che l'ISEE sia maggiore di 0
        if (studente.ISEE <= 0) {
            Toast.makeText(this, "ISEE non valido. Deve essere un valore positivo.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Verifica che l'email non sia vuota e contenga il simbolo '@'
        if (studente.email.isEmpty() || !studente.email.contains("@")) {
            Toast.makeText(this, "Email non valida. Assicurati che sia un indirizzo email valido.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Tutte le verifiche sono state superate
        return true
    }

}