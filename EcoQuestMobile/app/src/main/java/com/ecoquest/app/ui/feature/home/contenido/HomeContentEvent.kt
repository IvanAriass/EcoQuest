package com.ecoquest.app.ui.feature.home.contenido

sealed interface HomeContentEvent {
    data object OnNavigateToEventos : HomeContentEvent
    data object OnNavigateToComunidades : HomeContentEvent
    data object OnNavigateToTienda : HomeContentEvent
    data object OnNavigateToRetos : HomeContentEvent
    data class OnNavigateToEvento(val eventoId: Long) : HomeContentEvent
    data class OnNavigateToComunidad(val comunidadId: Long) : HomeContentEvent
}
