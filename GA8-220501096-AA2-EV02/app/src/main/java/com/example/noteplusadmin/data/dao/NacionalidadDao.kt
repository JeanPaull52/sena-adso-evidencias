package com.example.noteplusadmin.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteplusadmin.data.entities.Nacionalidad
import kotlinx.coroutines.flow.Flow

@Dao
interface NacionalidadDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(nacionalidad: Nacionalidad): Long

    @Query("SELECT * FROM nacionalidad ORDER BY nombreNacionalidad ASC")
    fun listarTodas(): Flow<List<Nacionalidad>>
}
