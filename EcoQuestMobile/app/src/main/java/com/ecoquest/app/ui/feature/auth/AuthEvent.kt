package com.ecoquest.app.ui.feature.auth

sealed interface AuthEvent {
    data class OnEmailChanged(val email: String) : AuthEvent
    data class OnPasswordChanged(val password: String) : AuthEvent
    data class OnUsernameChanged(val username: String) : AuthEvent
    data class OnBirthDateChanged(val birthDate: String) : AuthEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String) : AuthEvent
    data object OnRegisterClicked : AuthEvent
    data object OnLoginClicked : AuthEvent
    data object OnGoToRegistro : AuthEvent
    data object OnRegistroExitoso : AuthEvent
    data object OnNavigateToHomeConsumed : AuthEvent
    data object OnNavigateToLoginConsumed : AuthEvent
}
