package com.ecoquest.app.ui.feature.ajustes

sealed interface AjustesEvent {
    data object OnToggleTema : AjustesEvent
    data object OnCerrarSesion : AjustesEvent
}
