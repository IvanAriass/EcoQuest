package com.ecoquest.app.ui.feature.perfil

sealed interface PerfilEvent {
    data object OnToggleComunidades : PerfilEvent
    data object OnToggleEventos : PerfilEvent
    data object OnToggleRetos : PerfilEvent
    data object OnTogglePuntos : PerfilEvent
    data class OnFotoSeleccionada(val uri: String) : PerfilEvent
    data object OnGoToAjustes : PerfilEvent
    data object OnGoToRetos : PerfilEvent
    data object OnGoToTienda : PerfilEvent
    data object OnGoToEventos : PerfilEvent
    data object OnGoToComunidades : PerfilEvent
    data object OnLogout : PerfilEvent
}
