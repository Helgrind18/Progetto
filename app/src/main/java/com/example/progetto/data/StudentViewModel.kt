package com.example.progetto.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel (application: Application): AndroidViewModel(application) {
    //Il suo compito è quello di fornire i dati necessari alla visualizzazione
    //Funziona come da tramite fra la repository e la parte grafica
    private val readAllData: LiveData<List<Studente>>
    private val repository: StudentRepository
    init {
        val studentDao = StudentDatabase.getDatabase(application).studenteDao()
        repository = StudentRepository(studentDao)
        readAllData = repository.readAllData
    }

    fun addStudente(studente: Studente){
        viewModelScope.launch(Dispatchers.IO){
            repository.addStudent(studente)
        }
    }
}