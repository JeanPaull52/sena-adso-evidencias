package com.example.noteplusadmin.data.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import com.example.noteplusadmin.data.entities.Docente
import com.example.noteplusadmin.data.entities.Persona
import kotlinx.coroutines.flow.Flow

data class DocenteConPersona(
    @Embedded val docente: Docente,
    @Relation(
        parentColumn = "idPersona",
        entityColumn = "idPersona"
    )
    val persona: Persona
)

@Dao
interface DocenteDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(docente: Docente): Long

    @Transaction
    @Query("SELECT * FROM docente ORDER BY idDocente ASC")
    fun listarTodosConPersona(): Flow<List<DocenteConPersona>>
}
