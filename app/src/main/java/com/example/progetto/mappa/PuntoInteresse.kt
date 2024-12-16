package com.example.progetto.mappa

//Si usa una data class
//In quanto essa viene usata per contere dati, allora quello che si fa è definire i dati e poi controllarli
data class PuntoInteresse (
    val nome: String,
    val latitudine: Double,
    val longitudine: Double,
    val raggio: Float
)

/*
* nome: Il nome del punto di interesse
* latitudine e longitudine: Le coordinate del punto di interesse
* raggio: Il raggio entro il quale si vuole essere notificati*/