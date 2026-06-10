package com.ecoquest.app.ui.features.ajustes

data class AjustesUiState(
    val notificacionesActivadas: Boolean = true,
    val temaOscuro: Boolean = false,
    val idiomaSeleccionado: String = "Español",
    val showEliminarCuentaDialog: Boolean = false,
    val showCambiarPasswordDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)