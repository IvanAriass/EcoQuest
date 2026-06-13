package com.ecoquest.app.ui.feature.ajustes

import com.ecoquest.app.domain.model.Usuario

data class AjustesUiState(
    val temaOscuro: Boolean = false,
    val notificaciones: Boolean = true,
    val idioma: String = "es",
    val usuario: Usuario? = null,
    val appVersion: String = "1.0",
    val isLoading: Boolean = false
)
