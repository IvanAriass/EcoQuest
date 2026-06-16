package com.ecoquest.app.ui.feature.tienda

import com.ecoquest.app.domain.model.Producto

sealed interface TiendaEvent {
    data class SelectCategory(val category: String?) : TiendaEvent
    data class SelectTipo(val tipo: String?) : TiendaEvent
    data class OnProductoClick(val producto: Producto) : TiendaEvent
    data object OnCerrarDetalle : TiendaEvent
    data object OnConfirmarCanje : TiendaEvent
    data object OnCancelarCanje : TiendaEvent
    data object OnCanjeConsumido : TiendaEvent
    data object OnErrorConsumido : TiendaEvent
}
