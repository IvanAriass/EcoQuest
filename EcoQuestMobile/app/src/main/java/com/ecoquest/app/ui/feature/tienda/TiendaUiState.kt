package com.ecoquest.app.ui.feature.tienda

import com.ecoquest.app.domain.model.Producto

data class TiendaUiState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
