package com.example.progetto

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database (context : Context): SQLiteOpenHelper(context, "Database",
    null, 1)  {
    override fun onCreate(db: SQLiteDatabase) {
        //Questo metodo viene chiamato quando il db viene creato per la prima volta
        createTableStudenti(db)
        createTableProfessori(db)
        createTableCorso(db)
        createTableAula(db)
        createTableBiblioteca(db)
        createTableEsami(db)
        createTableFrequentaCorso(db)
        createTableAulaCorso(db)
        createTableLibriPrestito(db)
        createTablePrenotazioneEsame(db)
    }

    private fun createTablePrenotazioneEsame(db: SQLiteDatabase) {

    }

    private fun createTableLibriPrestito(db: SQLiteDatabase) {

    }

    private fun createTableAulaCorso(db: SQLiteDatabase) {

    }

    private fun createTableFrequentaCorso(db: SQLiteDatabase) {

    }

    private fun createTableEsami(db: SQLiteDatabase) {

    }

    private fun createTableBiblioteca(db: SQLiteDatabase) {

    }

    private fun createTableAula(db: SQLiteDatabase) {

    }

    private fun createTableCorso(db: SQLiteDatabase) {

    }

    private fun createTableProfessori(db: SQLiteDatabase) {

    }

    private fun createTableStudenti(db: SQLiteDatabase) {

    }

    //Dobbiamo costruire le tuple di valori di caricare nel db
    fun insertStudenti(matricola : String, nome : String, cognome : String, email : String, password : String) : Boolean{
        val db = this.writableDatabase //identifichiamo un'istanza in scrittura del database
        val values = ContentValues().apply {
            //Così definisco i valori da inserire nel db
            //ContentValues associa i nomi delle colonne della tabella ai rispettivi valori da inserire
            put("matricola", matricola)
            put("nome", nome)
            put("cognome", cognome)
            put("email", email)
            put("password", password)
        }
        val result = db.insert("Studenti", null, values) // Tento l'inserimento dei dati
        return result != -1L // Controllo l'effettivo inserimento dei dati
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Studenti")
        db.execSQL("DROP TABLE IF EXISTS Professori")
        db.execSQL("DROP TABLE IF EXISTS Corso")
        db.execSQL("DROP TABLE IF EXISTS Aula")
        db.execSQL("DROP TABLE IF EXISTS Biblioteca")
        db.execSQL("DROP TABLE IF EXISTS Esami")
        db.execSQL("DROP TABLE IF EXISTS FrequentaCorso")
        db.execSQL("DROP TABLE IF EXISTS AulaCorso")
        db.execSQL("DROP TABLE IF EXISTS LibriPrestito")
        db.execSQL("DROP TABLE IF EXISTS PrenotazioneEsame")
        onCreate(db)
    }
}