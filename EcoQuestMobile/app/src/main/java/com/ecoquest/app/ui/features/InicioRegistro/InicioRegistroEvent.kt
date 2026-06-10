package com.ecoquest.app.ui.features.InicioRegistro

sealed interface InicioRegistroEvent {
    data class OnEmailChanged(val email: String) : InicioRegistroEvent
    data class OnPasswordChanged(val password: String) : InicioRegistroEvent
    data class OnUsernameChanged(val username: String) : InicioRegistroEvent
    data class OnBirthDateChanged(val birthDate: String) : InicioRegistroEvent
    data class OnConfirmPasswordChanged(val confirmPassword: String) : InicioRegistroEvent
    object OnRegisterClicked : InicioRegistroEvent
    object OnLoginClicked : InicioRegistroEvent

    object OnGoToRegistro : InicioRegistroEvent
    object OnRegistroExitoso : InicioRegistroEvent
    object OnNavigateToHomeConsumed : InicioRegistroEvent
}