package com.example.noteplusadmin.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rol")
data class Rol(
    @PrimaryKey(autoGenerate = true) val idRol: Int = 0,
    val nombreRol: String,
    val descripcion: String? = null
)
