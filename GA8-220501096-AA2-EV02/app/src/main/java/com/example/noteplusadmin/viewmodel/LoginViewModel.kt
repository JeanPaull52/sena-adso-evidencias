package com.example.noteplusadmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.noteplusadmin.data.database.AppDatabase
import com.example.noteplusadmin.data.entities.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest

sealed class LoginState {
    data object Idle    : LoginState()
    data object Loading : LoginState()
    data class  Success(val usuario: Usuario) : LoginState()
    data object Error   : LoginState()
}

class LoginViewModel(private val db: AppDatabase) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(nombreUsuario: String, contrasena: String) {
        if (nombreUsuario.isBlank() || contrasena.isBlank()) {
            _state.value = LoginState.Error
            return
        }
        _state.value = LoginState.Loading
        viewModelScope.launch {
            val hash    = sha256(contrasena)
            val usuario = db.usuarioDao().buscarPorCredenciales(nombreUsuario.trim(), hash)
            _state.value = if (usuario != null) LoginState.Success(usuario) else LoginState.Error
        }
    }

    fun resetState() { _state.value = LoginState.Idle }

    private fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    class Factory(private val db: AppDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = LoginViewModel(db) as T
    }
}
