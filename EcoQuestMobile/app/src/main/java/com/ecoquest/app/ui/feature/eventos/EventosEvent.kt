package com.ecoquest.app.ui.feature.eventos

sealed interface EventosEvent {
    data class OnBusquedaChanged(val texto: String) : EventosEvent
}
