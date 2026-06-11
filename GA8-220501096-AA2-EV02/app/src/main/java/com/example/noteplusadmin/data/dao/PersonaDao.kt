package com.example.noteplusadmin.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.noteplusadmin.data.entities.Persona

@Dao
interface PersonaDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(persona: Persona): Long
}
