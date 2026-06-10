package com.ecoquest.app.ui.features.tienda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ecoquest.app.models.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TiendaViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(TiendaUiState())
        private set

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        state = state.copy(
            puntos = 1250,
            productosDestacados = listOf(
                Producto(id = 1L, nombre = "Regalo Misterioso", descripcion = "🎁", precio = 1000)
            )
        )
    }

    fun onEvent(event: TiendaEvent) {
        when (event) {
            TiendaEvent.OnCanjearClick -> { /* TODO: navegar a catálogo */ }
            is TiendaEvent.OnProductoClick -> { /* TODO: navegar al detalle */ }
            TiendaEvent.OnComoCunseguirPuntosClick -> { /* TODO: mostrar info de puntos */ }
        }
    }
}
