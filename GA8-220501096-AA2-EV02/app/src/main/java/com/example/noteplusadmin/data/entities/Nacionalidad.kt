package com.example.noteplusadmin.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nacionalidad")
data class Nacionalidad(
    @PrimaryKey(autoGenerate = true) val idNacionalidad: Int = 0,
    val nombreNacionalidad: String
)
