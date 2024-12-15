package com.example.progetto.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Aula(
    @PrimaryKey(autoGenerate = false)
    var cubo: Int,
    var piano: Int,
    var capienza: Int
)