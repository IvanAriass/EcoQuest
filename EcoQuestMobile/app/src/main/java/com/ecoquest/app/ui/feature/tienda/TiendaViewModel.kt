package com.ecoquest.app.ui.feature.tienda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.ProductoRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.domain.repository.UsuarioCosmeticoRepository
import com.ecoquest.app.domain.usecase.productos.GetProductosUseCase
import com.ecoquest.app.managers.TokenManager
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
    private val productoRepository: ProductoRepository,
    private val transaccionPuntosRepository: TransaccionPuntosRepository,
    private val usuarioCosmeticoRepository: UsuarioCosmeticoRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(TiendaUiState())
    val state: StateFlow<TiendaUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    init {
        cargarProductos()
        cargarSaldo()
    }

    fun onEvent(event: TiendaEvent) {
        when (event) {
            is TiendaEvent.SelectCategory -> {
                _state.update { it.copy(selectedCategory = event.category, selectedTipo = null) }
            }
            is TiendaEvent.SelectTipo -> {
                _state.update { it.copy(selectedTipo = event.tipo, selectedCategory = null) }
            }
            is TiendaEvent.OnProductoClick -> {
                _state.update { it.copy(productoSeleccionado = event.producto) }
            }
            is TiendaEvent.OnCerrarDetalle -> {
                _state.update { it.copy(productoSeleccionado = null, confirmarCanje = false) }
            }
            is TiendaEvent.OnConfirmarCanje -> {
                val producto = _state.value.productoSeleccionado ?: return
                realizarCanje(producto.id)
            }
            is TiendaEvent.OnCancelarCanje -> {
                _state.update { it.copy(confirmarCanje = false) }
            }
            is TiendaEvent.OnCanjeConsumido -> {
                _state.update { it.copy(canjeExitoso = false, productoSeleccionado = null, confirmarCanje = false) }
            }
            is TiendaEvent.OnErrorConsumido -> {
                _state.update { it.copy(canjeError = null) }
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
                        tipos = productos.map { p -> p.tipo }
                            .filter { c -> c.isNotBlank() }
                            .distinct()
                            .sorted(),
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

    private fun cargarSaldo() {
        viewModelScope.launch {
            val saldo = transaccionPuntosRepository.getSaldo(usuarioId)
            _state.update { it.copy(saldoPuntos = saldo) }
        }
    }

    private fun realizarCanje(productoId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(confirmarCanje = false, isCanjeando = true) }
            val error = productoRepository.canjearProducto(usuarioId, productoId)
            if (error == null) {
                usuarioCosmeticoRepository.refresh(usuarioId)
                cargarSaldo()
                _state.update { it.copy(canjeExitoso = true, isCanjeando = false) }
            } else {
                _state.update { it.copy(canjeError = error, isCanjeando = false) }
            }
        }
    }
}
