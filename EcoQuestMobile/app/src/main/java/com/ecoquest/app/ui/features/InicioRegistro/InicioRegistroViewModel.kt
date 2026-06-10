package com.ecoquest.app.ui.features.InicioRegistro

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.repositories.UsuarioRepository
import com.ecoquest.app.data.services.UsuarioEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InicioRegistroViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) : ViewModel() {

    var uiState =  mutableStateOf(InicioRegistroUiState())
        private set

    init {
        // Estado inicial si quieres hacer alguna carga inicial
    }

    fun onEvent(event: InicioRegistroEvent) {
        when (event) {

            is InicioRegistroEvent.OnEmailChanged -> {
                uiState.value = uiState.value.copy(
                    email = event.email
                )
            }

            is InicioRegistroEvent.OnPasswordChanged -> {
                uiState.value = uiState.value.copy(
                    password = event.password
                )
            }

            is InicioRegistroEvent.OnUsernameChanged -> {
                uiState.value = uiState.value.copy(
                    username = event.username
                )
            }

            is InicioRegistroEvent.OnBirthDateChanged -> {
                uiState.value = uiState.value.copy(
                    birthDate = event.birthDate
                )
            }

            is InicioRegistroEvent.OnConfirmPasswordChanged -> {
                uiState.value = uiState.value.copy(
                    confirmPassword = event.confirmPassword
                )
            }

            is InicioRegistroEvent.OnLoginClicked -> {
                login()
            }

            is InicioRegistroEvent.OnRegisterClicked -> {
                register()
            }

            is InicioRegistroEvent.OnGoToRegistro -> { /* NavHost lo intercepta */ }

            is InicioRegistroEvent.OnRegistroExitoso -> {
                uiState.value = InicioRegistroUiState()
            }

            is InicioRegistroEvent.OnNavigateToHomeConsumed -> {
                uiState.value = uiState.value.copy(navigateToHome = false)
            }
        }
    }

    private fun login() {

        uiState.value = uiState.value.copy(
            isLoading = true,
            error = null
        )

        // Aquí iría la llamada a Firebase / API

        uiState.value = uiState.value.copy(
            isLoading = false,
            isLoggedIn = true,
            navigateToHome = true
        )
    }

    private fun register() {

        if (uiState.value.password != uiState.value.confirmPassword) {
            uiState.value = uiState.value.copy(
                error = "Las contraseñas no coinciden"
            )
            return
        }

        uiState.value = uiState.value.copy(
            isLoading = true,
            error = null
        )

        val usuarioEntity = UsuarioEntity(
            id = 1L,
            nombreUsuario = uiState.value.username,
            nombre = uiState.value.username,
            email = uiState.value.email
        )
        viewModelScope.launch {
            usuarioRepository.upsert(usuarioEntity)
        }

        uiState.value = uiState.value.copy(
            isLoading = false,
            isLoggedIn = false
        )
        onEvent(InicioRegistroEvent.OnRegistroExitoso)
    }
}