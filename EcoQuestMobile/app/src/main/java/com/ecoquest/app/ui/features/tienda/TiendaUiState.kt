package com.ecoquest.app.ui.features.tienda

import com.ecoquest.app.models.Producto

data class TiendaUiState(
    val puntos: Int = 0,
    val productosDestacados: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
