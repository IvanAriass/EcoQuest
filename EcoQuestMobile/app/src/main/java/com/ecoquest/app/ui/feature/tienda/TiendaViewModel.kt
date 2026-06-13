package com.ecoquest.app.ui.feature.tienda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.ProductoRepository
import com.ecoquest.app.domain.usecase.productos.GetProductosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TiendaViewModel @Inject constructor(
    private val getProductosUseCase: GetProductosUseCase,
    private val productoRepository: ProductoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TiendaUiState())
    val state: StateFlow<TiendaUiState> = _state.asStateFlow()

    init {
        cargarProductos()
    }

    fun onEvent(event: TiendaEvent) {
        when (event) {
            is TiendaEvent.SelectCategory -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
        }
    }

    private fun cargarProductos() {
        viewModelScope.launch {
            productoRepository.refreshProductos()
        }
        viewModelScope.launch {
            getProductosUseCase().collect { productos ->
                _state.update {
                    it.copy(
                        productos = productos,
                        categorias = productos.map { p -> p.categoria }
                            .filter { c -> c.isNotBlank() }
                            .distinct()
                            .sorted(),
                        isLoading = false
                    )
                }
            }
        }
    }
}
