package com.example.noteplusadmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.noteplusadmin.data.database.AppDatabase
import com.example.noteplusadmin.data.entities.Nacionalidad
import com.example.noteplusadmin.data.entities.Persona
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class PersonaEstado {
    data object Idle    : PersonaEstado()
    data object Cargando : PersonaEstado()
    data class  Exito(val idPersona: Int) : PersonaEstado()
    data class  Error(val mensaje: String) : PersonaEstado()
}

class PersonaViewModel(private val db: AppDatabase) : ViewModel() {

    val nacionalidades: StateFlow<List<Nacionalidad>> = db.nacionalidadDao()
        .listarTodas()
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _estado = MutableStateFlow<PersonaEstado>(PersonaEstado.Idle)
    val estado: StateFlow<PersonaEstado> = _estado.asStateFlow()

    fun guardar(persona: Persona) {
        val error = validar(persona)
        if (error != null) {
            _estado.value = PersonaEstado.Error(error)
            return
        }
        _estado.value = PersonaEstado.Cargando
        viewModelScope.launch {
            try {
                val id = db.personaDao().insertar(persona)
                _estado.value = PersonaEstado.Exito(id.toInt())
            } catch (e: Exception) {
                _estado.value = PersonaEstado.Error("Error al guardar: ${e.message}")
            }
        }
    }

    fun resetEstado() { _estado.value = PersonaEstado.Idle }

    private fun validar(p: Persona): String? = when {
        p.tipoDocumento.isBlank()      -> "Selecciona el tipo de documento"
        p.numeroDocumento.isBlank()    -> "Ingresa el número de documento"
        p.primerNombre.isBlank()       -> "Ingresa el primer nombre"
        p.primerApellido.isBlank()     -> "Ingresa el primer apellido"
        p.fechaNacimiento.isBlank()    -> "Ingresa la fecha de nacimiento"
        p.celular.isBlank()            -> "Ingresa el número de celular"
        p.correoElectronico.isBlank()  -> "Ingresa el correo electrónico"
        p.direccionResidencia.isBlank()-> "Ingresa la dirección"
        p.idNacionalidad == 0          -> "Selecciona la nacionalidad"
        p.estado.isBlank()             -> "Selecciona el estado"
        else -> null
    }

    class Factory(private val db: AppDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = PersonaViewModel(db) as T
    }
}
