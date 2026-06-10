package com.ecoquest.app.ui.feature.eventos

import com.ecoquest.app.domain.model.Evento

data class EventosUiState(
    val eventos: List<Evento> = emptyList(),
    val textoBusqueda: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val eventosFiltrados: List<Evento>
        get() = if (textoBusqueda.isBlank()) eventos
        else eventos.filter {
            it.nombre.contains(textoBusqueda, ignoreCase = true) ||
            it.descripcion.contains(textoBusqueda, ignoreCase = true)
        }
}
