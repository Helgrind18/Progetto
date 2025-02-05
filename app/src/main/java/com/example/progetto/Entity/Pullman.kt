package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pullman")
data class Pullman(
    @ColumnInfo(name = "id") @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "nomePullman")val nomePullman: String,
    @ColumnInfo(name = "orarioPartenza")val orarioPartenza: Int,
    @ColumnInfo(name = "destinazione")val destinazione: String
)
