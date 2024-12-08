package com.example.progetto

import android.content.Context
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