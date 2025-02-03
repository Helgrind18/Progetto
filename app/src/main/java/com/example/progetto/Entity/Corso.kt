package com.example.progetto.Entity
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Index

@Entity(
    tableName = "corso",
    indices = [Index(value = ["nome"], unique = true)]  // Imposta nomeCorso come univoco
)
data class Corso(
    @ColumnInfo(name = "corsoId") @PrimaryKey val id: Int,
    @ColumnInfo(name = "nome")val nome: String,
    val CFU: Int,
    val semestre: Int,
    val anno: Int,
    val descrizione: String
)
