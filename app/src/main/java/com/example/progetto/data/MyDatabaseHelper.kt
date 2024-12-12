package com.example.progetto.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "mydatabase.db" , null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        //Creazione della tabella
        db.execSQL("CREATE TABLE studenti (_matricola INTEGER PRIMARY KEY, nome TEXT, cognome TEXT);")
        //con "_" vado a specificare che la chiave sia autoincrementale
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion : Int) {
        //Questo metodo viene chiamato quando la versione del db presente
        //nel dispositivo è diversa dalla versione specificata nel costruttore, o quando
        //viene aggiornata l'app e la nuova versione richiede una struttura di db diversa
        db.execSQL("DROP TABLE IF EXISTS studenti;")
        onCreate(db)
    }

    //Funzione per inserire un nuovo studente
    fun insertStudent(matricola: Int, nome: String, cognome: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply{
            put("matricola", matricola)
            put("nome", nome)
            put("cognome", cognome)
        }
        val result = db.insert("studenti", null, values)
        return result != -1L
    }

    //Query: conteggio studenti con nome "nome"
    fun countStudentsWithName(nome: String): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM studenti WHERE nome = 'm' ", arrayOf(nome))
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

}