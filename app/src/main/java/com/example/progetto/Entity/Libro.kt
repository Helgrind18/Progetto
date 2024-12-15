package com.example.progetto.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Libro(
    @PrimaryKey(autoGenerate = true)
    var ISBN: Long
)