package com.example.progetto

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class OrarioLezioni : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_orario_lezioni)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //TODO: inserire codice per visualizzare l'orario delle lezioni
        //L'utente è collegato ad una serie di corsi, quindi a schermo dovremmo avere una serie di corsi

    }
    fun giornoToString(giorno: Int): String {
        return when (giorno) {
            Calendar.SUNDAY -> "Domenica"
            Calendar.MONDAY -> "Lunedì"
            Calendar.TUESDAY -> "Martedì"
            Calendar.WEDNESDAY -> "Mercoledì"
            Calendar.THURSDAY -> "Giovedì"
            Calendar.FRIDAY -> "Venerdì"
            Calendar.SATURDAY -> "Sabato"
            else -> "Sconosciuto"
        }
    }

}