package com.ecoquest.app.ui.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.usecase.auth.LoginUseCase
import com.ecoquest.app.domain.usecase.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email, error = null) }
            }
            is AuthEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password, error = null) }
            }
            is AuthEvent.OnUsernameChanged -> {
                _uiState.update { it.copy(username = event.username, error = null) }
            }
            is AuthEvent.OnBirthDateChanged -> {
                _uiState.update { it.copy(birthDate = event.birthDate, error = null) }
            }
            is AuthEvent.OnConfirmPasswordChanged -> {
                _uiState.update { it.copy(confirmPassword = event.confirmPassword, error = null) }
            }
            is AuthEvent.OnLoginClicked -> login()
            is AuthEvent.OnRegisterClicked -> register()
            is AuthEvent.OnGoToRegistro -> { }
            is AuthEvent.OnRegistroExitoso -> {
                _uiState.value = AuthUiState()
            }
            is AuthEvent.OnNavigateToHomeConsumed -> {
                _uiState.update { it.copy(navigateToHome = false) }
            }
            is AuthEvent.OnNavigateToLoginConsumed -> {
                _uiState.update { it.copy(navigateToLogin = false) }
            }
        }
    }

    private fun login() {
        val current = _uiState.value

        if (current.email.isBlank()) {
            _uiState.update { it.copy(error = "Ingresa tu correo electrónico") }
            return
        }
        if (current.password.isBlank()) {
            _uiState.update { it.copy(error = "Ingresa tu contraseña") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            loginUseCase(current.email, current.password)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, navigateToHome = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al iniciar sesión") }
                }
        }
    }

    private fun register() {
        val current = _uiState.value

        if (current.email.isBlank() || current.username.isBlank() || current.password.isBlank()) {
            _uiState.update { it.copy(error = "Completa todos los campos") }
            return
        }
        if (!current.email.contains("@")) {
            _uiState.update { it.copy(error = "Correo electrónico no válido") }
            return
        }
        if (current.password.length < 6) {
            _uiState.update { it.copy(error = "La contraseña debe tener al menos 6 caracteres") }
            return
        }
        if (current.password != current.confirmPassword) {
            _uiState.update { it.copy(error = "Las contraseñas no coinciden") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            registerUseCase(
                Usuario(
                    nombreUsuario = current.username,
                    nombre = current.username,
                    email = current.email,
                    contrasena = current.password
                ),
                current.password
            )
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, navigateToLogin = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al registrarse") }
                }
        }
    }
}
