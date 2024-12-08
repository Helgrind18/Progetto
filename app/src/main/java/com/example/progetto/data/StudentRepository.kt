package com.example.progetto.data

import androidx.lifecycle.LiveData

class StudentRepository (private val studenteDao: StudenteDAO){
    //è una classe astratta per accedere a dati studenti dal database
    val readAllData: LiveData<List<Studente>> = studenteDao.getAllStudents()

    suspend fun addStudent(studente: Studente){
        studenteDao.inserisciStudente(studente)
    }
}