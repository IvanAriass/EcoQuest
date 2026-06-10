package com.ecoquest.app.ui.features.InicioRegistro

data class InicioRegistroUiState (
    val email: String = "",
    val password: String = "",

    val username: String = "",
    val birthDate: String = "",
    val confirmPassword: String = "",

    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,

    val navigateToHome: Boolean = false
)