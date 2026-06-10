package com.ecoquest.app.ui.features.eventoDentro

sealed interface EventoDentroEvent {
    object OnInscribirse : EventoDentroEvent
    object OnDesinscribirse : EventoDentroEvent
}
