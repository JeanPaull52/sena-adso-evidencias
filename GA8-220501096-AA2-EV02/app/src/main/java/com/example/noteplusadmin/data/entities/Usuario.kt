package com.example.noteplusadmin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuario",
    foreignKeys = [
        ForeignKey(
            entity = Rol::class,
            parentColumns = ["idRol"],
            childColumns = ["idRol"],
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = Persona::class,
            parentColumns = ["idPersona"],
            childColumns = ["idPersona"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true) val idUsuario: Int = 0,
    @ColumnInfo(index = true) val idRol: Int,
    @ColumnInfo(index = true) val idPersona: Int,
    val nombreUsuario: String,
    val contrasenaHash: String,
    val fechaCreacion: String,
    val estado: String
)
