package com.ecoquest.app.ui.feature.comunidades

sealed interface ComunidadesEvent {
    data class OnBusquedaChanged(val texto: String) : ComunidadesEvent
    data class OnComunidadClick(val comunidadId: Int) : ComunidadesEvent
    data object OnCrearComunidad : ComunidadesEvent
    data class OnGuardarComunidad(val nombre: String, val descripcion: String, val imagen: String) : ComunidadesEvent
    data class OnEliminarComunidad(val comunidadId: Long) : ComunidadesEvent
    data class OnEditarComunidad(val comunidadId: Long, val nombre: String, val descripcion: String, val imagen: String) : ComunidadesEvent
    data object OnDismissDialog : ComunidadesEvent
    data object OnRefrescar : ComunidadesEvent
}
