package com.example.noteplusadmin.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteplusadmin.data.entities.Rol
import kotlinx.coroutines.flow.Flow

@Dao
interface RolDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(rol: Rol): Long

    @Query("SELECT * FROM rol ORDER BY nombreRol ASC")
    fun listarTodos(): Flow<List<Rol>>
}
