package com.ecoquest.app.ui.feature.evento_dentro

sealed interface EventoDentroEvent {
    data class OnComentarioChanged(val texto: String) : EventoDentroEvent
    data object OnEnviarComentario : EventoDentroEvent
    data object OnUnirse : EventoDentroEvent
    data object OnAbandonar : EventoDentroEvent
}
