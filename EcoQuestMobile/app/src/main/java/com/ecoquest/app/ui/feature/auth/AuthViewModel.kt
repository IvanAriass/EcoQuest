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
                _uiState.update { it.copy(email = event.email) }
            }
            is AuthEvent.OnPasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }
            is AuthEvent.OnUsernameChanged -> {
                _uiState.update { it.copy(username = event.username) }
            }
            is AuthEvent.OnBirthDateChanged -> {
                _uiState.update { it.copy(birthDate = event.birthDate) }
            }
            is AuthEvent.OnConfirmPasswordChanged -> {
                _uiState.update { it.copy(confirmPassword = event.confirmPassword) }
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
        }
    }

    private fun login() {
        val current = _uiState.value
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            loginUseCase(current.email, current.password)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false, isLoggedIn = true, navigateToHome = true) }
                }
                .onFailure { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message ?: "Error al iniciar sesión") }
                }
        }
    }

    private fun register() {
        val current = _uiState.value
        if (current.password != current.confirmPassword) {
            _uiState.update { it.copy(error = "Las contraseñas no coinciden") }
            return
        }
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            registerUseCase(
                Usuario(nombreUsuario = current.username, nombre = current.username, email = current.email),
                current.password
            )
            _uiState.update { it.copy(isLoading = false, isLoggedIn = false) }
            onEvent(AuthEvent.OnRegistroExitoso)
        }
    }
}
