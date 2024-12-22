package com.example.progetto.Entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "Aula")
data class Aula(
    @PrimaryKey(autoGenerate = false)
    var cubo: Int=0,
    var piano: Int,
    var capienza: Int
)

