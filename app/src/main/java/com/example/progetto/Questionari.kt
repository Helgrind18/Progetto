package com.example.progetto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Questionari : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_questionari)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val invioFeedbackButton = findViewById<Button>(R.id.invioFeedback)
        val feedbackEditText: EditText = findViewById(R.id.feedback)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        invioFeedbackButton.setOnClickListener {
            if (radioGroup.checkedRadioButtonId == -1) {
                Toast.makeText(this, "Selezionare una risposta!", Toast.LENGTH_LONG).show()
            }else {
                if (!feedbackEditText.text.toString().isEmpty()) {
                    Toast.makeText(this, "Feedback inviato!", Toast.LENGTH_LONG).show()
                    radioGroup.clearCheck()
                    feedbackEditText.text.clear()
                    finish()
                } else {
                    Toast.makeText(this, "Inserire un feedback!", Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}