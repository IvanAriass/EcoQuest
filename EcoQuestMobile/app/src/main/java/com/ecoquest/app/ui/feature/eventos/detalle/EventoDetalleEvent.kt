package com.ecoquest.app.ui.feature.eventos.detalle

sealed interface EventoDetalleEvent {
    data class OnComentarioChanged(val texto: String) : EventoDetalleEvent
    data object OnEnviarComentario : EventoDetalleEvent
    data class OnNavigateToPerfilUsuario(val usuarioId: Long) : EventoDetalleEvent
    data object OnUnirse : EventoDetalleEvent
    data object OnAbandonar : EventoDetalleEvent
    data class OnExpandirComentario(val comentarioId: Long) : EventoDetalleEvent
    data class OnColapsarComentario(val comentarioId: Long) : EventoDetalleEvent
    data class OnResponderA(val comentarioId: Long) : EventoDetalleEvent
    data object OnToggleComentariosSheet : EventoDetalleEvent
    data class OnTextoRespuestaChanged(val texto: String) : EventoDetalleEvent
    data object OnCancelarRespuesta : EventoDetalleEvent
    data object OnEnviarRespuesta : EventoDetalleEvent
}
