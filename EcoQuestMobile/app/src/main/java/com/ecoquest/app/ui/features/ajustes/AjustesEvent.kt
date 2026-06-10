package com.ecoquest.app.ui.features.ajustes

sealed interface AjustesEvent {
    object OnToggleNotificaciones : AjustesEvent
    object OnToggleTema : AjustesEvent
    data class OnIdiomaChanged(val idioma: String) : AjustesEvent
    object OnCerrarSesionClicked : AjustesEvent
    object OnEliminarCuentaClicked : AjustesEvent
    object OnConfirmarEliminarCuenta : AjustesEvent
    object OnDismissEliminarCuenta : AjustesEvent
    object OnCambiarPasswordClicked : AjustesEvent
    object OnDismissCambiarPassword : AjustesEvent
    data class OnConfirmarCambiarPassword(val nuevaPassword: String) : AjustesEvent
}