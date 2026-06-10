package com.ecoquest.app.ui.features.comunidadesDentro

sealed interface ComunidadesDentroEvent {
    object OnAddEventoClick : ComunidadesDentroEvent
    object OnEditComunidadClick : ComunidadesDentroEvent
    object OnDismissDialog : ComunidadesDentroEvent
    data class OnConfirmEditComunidad(val nombre: String, val descripcion: String) : ComunidadesDentroEvent
    data class OnCreateEvento(val nombre: String, val descripcion: String, val fechaHora: String) : ComunidadesDentroEvent
    data class OnEditEventoClick(val eventoId: Long) : ComunidadesDentroEvent
    data class OnConfirmEditEvento(val nombre: String, val descripcion: String, val fechaHora: String) : ComunidadesDentroEvent
}
