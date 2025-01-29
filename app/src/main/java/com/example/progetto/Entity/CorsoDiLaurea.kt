package com.example.progetto.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CorsoDiLaurea")
data class CorsoDiLaurea(
    @PrimaryKey
    @ColumnInfo(name = "corsoLaureaId") val cdlId: Int,
    @ColumnInfo(name = "nome") val nomeCDL: String,
)
