package com.ecoquest.app.ui.feature.comunidades_dentro

sealed interface ComunidadesDentroEvent {
    data class OnInfoChanged(val info: String) : ComunidadesDentroEvent
    data object OnUnirse : ComunidadesDentroEvent
    data object OnAbandonar : ComunidadesDentroEvent
    data object OnMostrarCrearEvento : ComunidadesDentroEvent
    data class OnGuardarEvento(val nombre: String, val descripcion: String, val fechaHora: String, val ubicacion: String, val imagen: String) : ComunidadesDentroEvent
    data object OnDismissDialog : ComunidadesDentroEvent
    data class OnEliminarEvento(val eventoId: Long) : ComunidadesDentroEvent
}
