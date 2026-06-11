package com.example.noteplusadmin.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.noteplusadmin.data.entities.Usuario

@Dao
interface UsuarioDao {

    @Query(
        """
        SELECT * FROM usuario
        WHERE nombreUsuario = :nombreUsuario
          AND contrasenaHash = :contrasenaHash
          AND estado = 'Activo'
        LIMIT 1
        """
    )
    suspend fun buscarPorCredenciales(nombreUsuario: String, contrasenaHash: String): Usuario?
}
