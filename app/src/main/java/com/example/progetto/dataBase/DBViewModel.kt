package com.example.progetto.dataBase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente
import com.example.progetto.MainActivity
import kotlinx.coroutines.launch

class DBViewModel: ViewModel() {

    val dataBaseStudenti = MainActivity.dataBaseApp.getStudenteDao()
    val dataBaseLibri = MainActivity.dataBaseApp.getLibroDao()
    val dataBaseAule = MainActivity.dataBaseApp.getAulaDao()

    val studentList: LiveData<List<com.example.progetto.Entity.Studente>> = dataBaseStudenti.getAll()
    val bookList: LiveData<List<Libro>> = dataBaseLibri.getAll()
    val classList: LiveData<List<Aula>> = dataBaseAule.getAll()

    fun aggiungiStudente(studente: Studente){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseStudenti.inserisciStudente(studente)
        }
    }

    fun eliminaStudente(matricola: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseStudenti.rimuoviStudente(matricola)
        }
    }

    fun aggiungiLibro(libro: Libro){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseLibri.inserisciLibro(libro)
        }
    }

    fun eliminaLibro(ISBN: Long){
        viewModelScope.launch(Dispatchers.IO) {

            dataBaseLibri.rimuoviLibro(ISBN)
        }
    }

    fun aggiungiAula(aula: Aula){
        viewModelScope.launch(Dispatchers.IO) {

            dataBaseAule.inserisciAula(aula)
        }
    }

    fun eliminaAula(cubo: Int){
        viewModelScope.launch(Dispatchers.IO) {

            dataBaseAule.rimuoviAula(cubo)
        }
    }



}


