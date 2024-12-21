package com.example.progetto.dataBase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewModelScope
import com.example.progetto.Entity.Aula
import com.example.progetto.Entity.Libro
import com.example.progetto.Entity.Studente
import com.example.progetto.MainActivity
import kotlinx.coroutines.launch

//Collega i dati (DAO/Database) con l’interfaccia utente. Contiene tutta la logica per interagire con il db
class DBViewModel(application: Application): AndroidViewModel(application){


    private val studenteDAO = DataBaseApp.getDatabase(application).getStudenteDao()
    private val libroDAO = DataBaseApp.getDatabase(application).getLibroDao()
    private val aulaDAO = DataBaseApp.getDatabase(application).getAulaDao()

    //Funzione per restituire tutti gli studenti
    fun getAllStudenti(): LiveData<List<Studente>> {
        return studenteDAO.getAll()
    }

    //Funzione per inserire un nuovo studente
    fun inserisciStudente(studente: Studente){
        // Esegui l'inserimento nel background (chiamata sincrona tramite DAO)
       viewModelScope.launch(Dispatchers.IO) {
           studenteDAO.inserisciStudente(studente)
       }
    }

    //Funzione per prendere uno studente tramite la matricola
    fun studenteByMatricola(matricola: Int): Studente? {
        return studenteDAO.getStudenteByMatricola(matricola)
    }

    fun eliminaStudenteByMatricola(matricola: Int){
        viewModelScope.launch(Dispatchers.IO) {
            studenteDAO.rimuoviStudenteByMatricola(matricola)
        }
    }

    /////////////// LIBRO //////////////////////////

    fun aggiungiLibro(libro: Libro){
        viewModelScope.launch(Dispatchers.IO) {
            libroDAO.inserisciLibro(libro)
        }
    }

    fun aggiungiLibro(iSBN: Long, name: String, autore: String, settore: String){
        viewModelScope.launch(Dispatchers.IO){
            val libro= Libro(iSBN, name,autore, settore)
            libroDAO.inserisciLibro(libro)
        }
    }

    fun eliminaLibro(iSBN: Long){
        viewModelScope.launch(Dispatchers.IO) {
            libroDAO.rimuoviLibro(iSBN)
        }
    }





























    /*val dataBaseStudenti = MainActivity.dataBaseApp.getStudenteDao()
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

    fun aggiungiStudente(matricola: Int,codiceFiscale: String, pswd: String, nome: String, cognome: String, ISEE: Long, email: String){
        viewModelScope.launch(Dispatchers.IO) {
            var email= "$codiceFiscale@studenti.unical.it"
            val studente= Studente(matricola,codiceFiscale,pswd,nome,cognome,ISEE,email)
            dataBaseStudenti.inserisciStudente(studente)
        }
    }

    fun eliminaStudente(matricola: Int){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseStudenti.rimuoviStudenteByMatricola(matricola)
        }
    }

    fun eliminaStudente(studente: Studente){
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseStudenti.rimuoviStudente(studente)
        }
    }

    fun studenteByMatricola(matricola: Int): Studente?{
        val studente= null
        viewModelScope.launch(Dispatchers.IO) {
            val studente= dataBaseStudenti.getStudenteByMatricola(matricola)
        }
        return studente
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


*/
}


