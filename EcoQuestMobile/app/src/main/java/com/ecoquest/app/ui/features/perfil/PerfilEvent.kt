package com.ecoquest.app.ui.features.perfil

sealed interface PerfilEvent {
    object OnToggleComunidades : PerfilEvent
    object OnToggleEventos : PerfilEvent
    data class OnFotoSeleccionada(val uri: String) : PerfilEvent
}
