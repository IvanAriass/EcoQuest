package com.ecoquest.app.ui.feature.acceso

sealed interface AccesoEvent {
    data class OnEmailChanged(val email: String) : AccesoEvent
    data class OnPasswordChanged(val password: String) : AccesoEvent
    data class OnUsernameChanged(val username: String) : AccesoEvent
    data class OnBirthDateChanged(val birthDate: String) : AccesoEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String) : AccesoEvent
    data object OnRegisterClicked : AccesoEvent
    data object OnLoginClicked : AccesoEvent
    data object OnGoToRegistro : AccesoEvent
    data object OnRegistroExitoso : AccesoEvent
    data object OnNavigateToHomeConsumed : AccesoEvent
    data object OnNavigateToLoginConsumed : AccesoEvent
}
