package com.example.noteplusadmin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "docente",
    foreignKeys = [
        ForeignKey(
            entity = Persona::class,
            parentColumns = ["idPersona"],
            childColumns = ["idPersona"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Docente(
    @PrimaryKey(autoGenerate = true) val idDocente: Int = 0,
    @ColumnInfo(index = true) val idPersona: Int,
    val fechaIngreso: String,
    val especialidad: String,
    val estadoLaboral: String
)
