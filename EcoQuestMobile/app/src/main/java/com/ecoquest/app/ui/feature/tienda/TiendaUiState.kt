package com.ecoquest.app.ui.feature.tienda

import com.ecoquest.app.domain.model.Producto

data class TiendaUiState(
    val productos: List<Producto> = emptyList(),
    val categorias: List<String> = emptyList(),
    val tipos: List<String> = emptyList(),
    val selectedCategory: String? = null,
    val selectedTipo: String? = null,
    val isLoading: Boolean = false,
    val isCanjeando: Boolean = false,
    val saldoPuntos: Int = 0,
    val productoSeleccionado: Producto? = null,
    val canjeExitoso: Boolean = false,
    val canjeError: String? = null,
    val confirmarCanje: Boolean = false,
    val error: String? = null
) {
    val filteredProductos: List<Producto>
        get() {
            var result = productos
            if (selectedTipo != null) {
                result = result.filter { it.tipo == selectedTipo }
            }
            if (selectedCategory != null) {
                result = result.filter { it.categoria == selectedCategory }
            }
            return result
        }
}
