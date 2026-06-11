package com.example.noteplusadmin.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "persona",
    foreignKeys = [
        ForeignKey(
            entity = Nacionalidad::class,
            parentColumns = ["idNacionalidad"],
            childColumns = ["idNacionalidad"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class Persona(
    @PrimaryKey(autoGenerate = true) val idPersona: Int = 0,
    @ColumnInfo(index = true) val idNacionalidad: Int,
    val tipoDocumento: String,
    val numeroDocumento: String,
    val primerNombre: String,
    val segundoNombre: String? = null,
    val primerApellido: String,
    val segundoApellido: String? = null,
    val fechaNacimiento: String,
    val telefonoFijo: String? = null,
    val celular: String,
    val correoElectronico: String,
    val direccionResidencia: String,
    val estado: String
)
