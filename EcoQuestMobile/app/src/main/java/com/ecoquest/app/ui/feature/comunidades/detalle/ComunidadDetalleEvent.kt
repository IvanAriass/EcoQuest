package com.ecoquest.app.ui.feature.comunidades.detalle

sealed interface ComunidadDetalleEvent {
    data class OnInfoChanged(val info: String) : ComunidadDetalleEvent
    data object OnUnirse : ComunidadDetalleEvent
    data object OnAbandonar : ComunidadDetalleEvent
    data object OnMostrarCrearEvento : ComunidadDetalleEvent
    data class OnGuardarEvento(val nombre: String, val descripcion: String, val fechaHora: String, val ubicacion: String, val imagen: String) : ComunidadDetalleEvent
    data object OnDismissDialog : ComunidadDetalleEvent
}
