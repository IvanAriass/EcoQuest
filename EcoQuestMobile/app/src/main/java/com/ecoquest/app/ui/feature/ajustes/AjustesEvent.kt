package com.ecoquest.app.ui.feature.ajustes

sealed interface AjustesEvent {
    data object OnToggleTema : AjustesEvent
    data object OnToggleNotificaciones : AjustesEvent
    data class OnSeleccionarIdioma(val idioma: String) : AjustesEvent
    data class OnSeleccionarTemaPersonalizado(val tema: String) : AjustesEvent
    data object OnCerrarSesion : AjustesEvent
}
