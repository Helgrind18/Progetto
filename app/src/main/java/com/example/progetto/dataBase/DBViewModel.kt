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

    fun aggiungiStudente(matricola: Int,codiceFiscale: String, nome: String, cognome: String, dataDiNascita: Int, ISEE: Long){
        viewModelScope.launch(Dispatchers.IO) {
            var email= "$codiceFiscale@studenti.unical.it"
            val studente= Studente(matricola,codiceFiscale,nome,cognome,dataDiNascita,ISEE,email)
            dataBaseStudenti.inserisciStudente(studente)
        }
    }

    fun eliminaStudente(matricola: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseStudenti.rimuoviStudente(matricola)
        }
    }

    /////////////// LIBRO //////////////////////////

    fun aggiungiLibro(libro: Libro){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseLibri.inserisciLibro(libro)
        }
    }

    fun aggiungiLibro(iSBN: Long){
        viewModelScope.launch(Dispatchers.IO){
            val libro= Libro(iSBN)
            dataBaseLibri.inserisciLibro(libro)
        }
    }

    fun eliminaLibro(iSBN: Long){
        viewModelScope.launch(Dispatchers.IO) {

            dataBaseLibri.rimuoviLibro(iSBN)
        }
    }


    ///////////////// AULA ///////////////////7

    fun aggiungiAula(aula: Aula){
        viewModelScope.launch(Dispatchers.IO) {

            dataBaseAule.inserisciAula(aula)
        }
    }

    fun aggiungiAula(cubo: Int, pano: Int, capieza: Int){
        viewModelScope.launch(Dispatchers.IO){
            val aula= Aula(cubo,pano,capieza)
            dataBaseAule.inserisciAula(aula)
        }
    }

    fun eliminaAula(cubo: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseAule.rimuoviAula(cubo)
        }
    }



}


