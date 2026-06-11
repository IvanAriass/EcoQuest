package com.ecoquest.app.ui.feature.eventos.detalle

sealed interface EventoDetalleEvent {
    data class OnComentarioChanged(val texto: String) : EventoDetalleEvent
    data object OnEnviarComentario : EventoDetalleEvent
    data object OnUnirse : EventoDetalleEvent
    data object OnAbandonar : EventoDetalleEvent
}
