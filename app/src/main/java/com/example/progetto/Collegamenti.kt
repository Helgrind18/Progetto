package com.example.progetto

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Collegamenti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_collegamenti)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Associazione dei click ai link
        intentImplicitoBrowser(R.id.sitoUnical, R.id.bottoneSito, "https://www.unical.it")
        intentImplicitoBrowser(R.id.Soscr,R.id.bottonesoscr, "https://soscr.unical.it")
        intentImplicitoBrowser(R.id.insta, R.id.bottoneinsta,"https://www.instagram.com/unical_official/")
        intentImplicitoBrowser(R.id.twitter, R.id.bottoneTwitter,"https://x.com/UniCalPortale/status/1640303607147905026")
    }

    // Funzione per impostare il click listener su un TextView
    private fun intentImplicitoBrowser(textViewId: Int, bottone: Int, url: String) {
        //Questa funzione prende l'ID del TextView e l'URL da aprire.
        val textView = findViewById<TextView>(textViewId)
        val bottone = findViewById<Button>(bottone)
        bottone.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            //Utilizziamo Intent.ACTION_VIEW con un Uri.parse(url) per indirizzare al browser l'apertura dell'URL
            startActivity(intent)
        }
    }
}
