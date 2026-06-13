package com.ecoquest.app.ui.feature.tienda

sealed interface TiendaEvent {
    data class SelectCategory(val category: String?) : TiendaEvent
}
