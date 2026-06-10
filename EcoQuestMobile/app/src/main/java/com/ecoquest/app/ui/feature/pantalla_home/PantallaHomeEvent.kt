package com.ecoquest.app.ui.feature.pantalla_home

sealed interface PantallaHomeEvent {
    data object OnNavigateToEventos : PantallaHomeEvent
    data object OnNavigateToComunidades : PantallaHomeEvent
    data object OnNavigateToTienda : PantallaHomeEvent
    data class OnNavigateToEvento(val eventoId: Long) : PantallaHomeEvent
    data class OnNavigateToComunidad(val comunidadId: Long) : PantallaHomeEvent
}
