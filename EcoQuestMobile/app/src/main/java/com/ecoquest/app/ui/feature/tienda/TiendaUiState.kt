package com.ecoquest.app.ui.feature.tienda

import com.ecoquest.app.domain.model.Producto

data class TiendaUiState(
    val productos: List<Producto> = emptyList(),
    val categorias: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val filteredProductos: List<Producto>
        get() = if (selectedCategory == null) productos
        else productos.filter { it.categoria == selectedCategory }
}
