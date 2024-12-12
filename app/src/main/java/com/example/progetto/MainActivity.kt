package com.example.progetto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progetto.data.MyDatabaseHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Bisogna gestire il login tramite il pulsante Invia
        val textUsername : EditText = findViewById(R.id.textUsername)
        val textPassword : EditText = findViewById(R.id.textPassword)
        
        //Bisogna gestire anche la memorizzazione dell'utente
        val ricordami : CheckBox = findViewById(R.id.Ricordami)

        val db = MyDatabaseHelper(this)
        
        //Quando l'utente clicca sul bottone Invia, bisogna andare a controllare che i dati siano corretti
        val invia :Button = findViewById(R.id.bottoneInvia)
        invia.setOnClickListener {
            //Innanzitutto si controllano i dati inseriti dall'utente
            val usr = textUsername.text.toString().trim() // Rimuovo gli spazi bianchi
            val pwd = textPassword.text.toString().trim()
            if (usr.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Inserisci tutti i dati", Toast.LENGTH_SHORT).show()
            }
            else{
                //Se sono qua vuol dire che l'utente abbia inserito correttamente i dati, allora posso mostrargli una nuova activity
                //Per passare ad un'altra activity si usa l'intent
                //Sarà un intent parametrico in quanto a schermo verrà mostrato il nome dell'utente
                val intent = Intent(this,HomeActivity::class.java).apply {
                    putExtra("username",usr)
                    ricordami.setOnClickListener { _, isChecked ->
                        if (isChecked){
                            //Salva la matricola
                            Toas
                        }
                    }
                }
                //Avvio effettivo dell'intent
                startActivity(intent)
            }
        }
        
    }
}