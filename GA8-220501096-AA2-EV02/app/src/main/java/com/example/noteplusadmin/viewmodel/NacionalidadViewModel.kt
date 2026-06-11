package com.example.noteplusadmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.noteplusadmin.data.database.AppDatabase
import com.example.noteplusadmin.data.entities.Nacionalidad
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class NacionalidadGuardarEstado {
    data object Idle  : NacionalidadGuardarEstado()
    data object Exito : NacionalidadGuardarEstado()
    data class  Error(val mensaje: String) : NacionalidadGuardarEstado()
}

class NacionalidadViewModel(private val db: AppDatabase) : ViewModel() {

    val nacionalidades: StateFlow<List<Nacionalidad>> = db.nacionalidadDao()
        .listarTodas()
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _guardarEstado = MutableStateFlow<NacionalidadGuardarEstado>(NacionalidadGuardarEstado.Idle)
    val guardarEstado: StateFlow<NacionalidadGuardarEstado> = _guardarEstado.asStateFlow()

    fun insertar(nombre: String) {
        if (nombre.isBlank()) {
            _guardarEstado.value = NacionalidadGuardarEstado.Error("El nombre no puede estar vacío")
            return
        }
        viewModelScope.launch {
            val resultado = db.nacionalidadDao().insertar(
                Nacionalidad(nombreNacionalidad = nombre.trim())
            )
            _guardarEstado.value = if (resultado > 0) {
                NacionalidadGuardarEstado.Exito
            } else {
                NacionalidadGuardarEstado.Error("Esa nacionalidad ya existe")
            }
        }
    }

    fun resetEstado() { _guardarEstado.value = NacionalidadGuardarEstado.Idle }

    class Factory(private val db: AppDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = NacionalidadViewModel(db) as T
    }
}
