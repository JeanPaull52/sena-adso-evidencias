package com.example.noteplusadmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.noteplusadmin.data.dao.DocenteConPersona
import com.example.noteplusadmin.data.database.AppDatabase
import com.example.noteplusadmin.data.entities.Docente
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class DocenteEstado {
    data object Idle     : DocenteEstado()
    data object Cargando : DocenteEstado()
    data object Exito    : DocenteEstado()
    data class  Error(val mensaje: String) : DocenteEstado()
}

class DocenteViewModel(private val db: AppDatabase) : ViewModel() {

    val docentes: StateFlow<List<DocenteConPersona>> = db.docenteDao()
        .listarTodosConPersona()
        .stateIn(
            scope        = viewModelScope,
            started      = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _estado = MutableStateFlow<DocenteEstado>(DocenteEstado.Idle)
    val estado: StateFlow<DocenteEstado> = _estado.asStateFlow()

    fun guardar(idPersona: Int, fechaIngreso: String, especialidad: String, estadoLaboral: String) {
        when {
            fechaIngreso.isBlank()  -> { _estado.value = DocenteEstado.Error("Ingresa la fecha de ingreso"); return }
            especialidad.isBlank()  -> { _estado.value = DocenteEstado.Error("Ingresa la especialidad"); return }
            estadoLaboral.isBlank() -> { _estado.value = DocenteEstado.Error("Selecciona el estado laboral"); return }
        }
        _estado.value = DocenteEstado.Cargando
        viewModelScope.launch {
            try {
                db.docenteDao().insertar(
                    Docente(
                        idPersona     = idPersona,
                        fechaIngreso  = fechaIngreso,
                        especialidad  = especialidad,
                        estadoLaboral = estadoLaboral
                    )
                )
                _estado.value = DocenteEstado.Exito
            } catch (e: Exception) {
                _estado.value = DocenteEstado.Error("Error al guardar: ${e.message}")
            }
        }
    }

    fun resetEstado() { _estado.value = DocenteEstado.Idle }

    class Factory(private val db: AppDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DocenteViewModel(db) as T
    }
}
