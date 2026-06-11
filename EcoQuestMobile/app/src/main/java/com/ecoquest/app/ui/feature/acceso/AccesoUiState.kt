package com.ecoquest.app.ui.feature.acceso

data class AccesoUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val birthDate: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigateToHome: Boolean = false,
    val navigateToLogin: Boolean = false
)
