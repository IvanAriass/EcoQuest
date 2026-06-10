package com.ecoquest.app.ui.feature.perfil

sealed interface PerfilEvent {
    data object OnToggleComunidades : PerfilEvent
    data object OnToggleEventos : PerfilEvent
    data class OnFotoSeleccionada(val uri: String) : PerfilEvent
}
