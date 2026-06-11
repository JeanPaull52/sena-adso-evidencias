package com.example.noteplusadmin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.noteplusadmin.data.dao.DocenteDao
import com.example.noteplusadmin.data.dao.NacionalidadDao
import com.example.noteplusadmin.data.dao.PersonaDao
import com.example.noteplusadmin.data.dao.RolDao
import com.example.noteplusadmin.data.dao.UsuarioDao
import com.example.noteplusadmin.data.entities.Docente
import com.example.noteplusadmin.data.entities.Nacionalidad
import com.example.noteplusadmin.data.entities.Persona
import com.example.noteplusadmin.data.entities.Rol
import com.example.noteplusadmin.data.entities.Usuario
import java.security.MessageDigest

@Database(
    entities = [
        Nacionalidad::class,
        Persona::class,
        Rol::class,
        Usuario::class,
        Docente::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nacionalidadDao(): NacionalidadDao
    abstract fun personaDao(): PersonaDao
    abstract fun rolDao(): RolDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun docenteDao(): DocenteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "noteplusadmin.db"
                )
                    .addCallback(SeedCallback())
                    .build()
                    .also { INSTANCE = it }
            }

        private fun sha256(input: String): String {
            val bytes = MessageDigest.getInstance("SHA-256")
                .digest(input.toByteArray(Charsets.UTF_8))
            return bytes.joinToString("") { "%02x".format(it) }
        }

        private class SeedCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                // Nacionalidades
                listOf(
                    "Colombiana", "Venezolana", "Ecuatoriana", "Peruana",
                    "Boliviana", "Brasileña", "Argentina", "Chilena",
                    "Mexicana", "Estadounidense", "Española", "Otra"
                ).forEach { nombre ->
                    db.execSQL(
                        "INSERT INTO nacionalidad (nombreNacionalidad) VALUES (?)",
                        arrayOf(nombre)
                    )
                }

                // Roles
                db.execSQL(
                    "INSERT INTO rol (nombreRol, descripcion) VALUES (?, ?)",
                    arrayOf("Administrador", "Acceso total al sistema")
                )
                db.execSQL(
                    "INSERT INTO rol (nombreRol, descripcion) VALUES (?, ?)",
                    arrayOf("Docente", "Gestión de actividades académicas")
                )

                // Persona del administrador
                db.execSQL(
                    """
                    INSERT INTO persona (
                        idNacionalidad, tipoDocumento, numeroDocumento,
                        primerNombre, primerApellido,
                        fechaNacimiento, celular, correoElectronico,
                        direccionResidencia, estado
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                    """.trimIndent(),
                    arrayOf(
                        1, "CC", "1000000001",
                        "Admin", "Sistema",
                        "1990-01-01", "3000000000",
                        "admin@noteplusadmin.com",
                        "Sede principal", "Activo"
                    )
                )

                // Usuario administrador — contraseña por defecto: admin2026#
                val adminHash = sha256("admin2026#")
                db.execSQL(
                    """
                    INSERT INTO usuario (
                        idRol, idPersona, nombreUsuario,
                        contrasenaHash, fechaCreacion, estado
                    ) VALUES (?, ?, ?, ?, ?, ?)
                    """.trimIndent(),
                    arrayOf(1, 1, "admin", adminHash, "2026-01-01", "Activo")
                )
            }
        }
    }
}
