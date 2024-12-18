package com.example.progetto.dataDARIMUOVERE

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DbViewModel(application: Application) : AndroidViewModel(application)  {
    private val db = AppDatabase.getDatabase(application).studenteDao()
    val allStudenti = db.getAllStudenti()

    fun insert(studente: Studente) {
        viewModelScope.launch {
            db.insert(studente)
        }
    }

}
