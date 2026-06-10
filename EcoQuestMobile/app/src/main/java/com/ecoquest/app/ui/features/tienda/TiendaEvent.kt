package com.ecoquest.app.ui.features.tienda

sealed interface TiendaEvent {
    object OnCanjearClick : TiendaEvent
    data class OnProductoClick(val productoId: Long) : TiendaEvent
    object OnComoCunseguirPuntosClick : TiendaEvent
}
