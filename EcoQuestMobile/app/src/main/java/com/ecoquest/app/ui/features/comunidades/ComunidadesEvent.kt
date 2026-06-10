package com.ecoquest.app.ui.features.comunidades

sealed interface ComunidadesEvent {
    object OnAddComunidadClick : ComunidadesEvent
    object OnDismissDialog : ComunidadesEvent
    data class OnCreateComunidad(val nombre: String, val descripcion: String, val imagenUri: String = "") : ComunidadesEvent
    data class OnComunidadClick(val comunidadId: Int) : ComunidadesEvent
}