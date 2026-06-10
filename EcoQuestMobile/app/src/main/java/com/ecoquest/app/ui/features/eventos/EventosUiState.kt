package com.ecoquest.app.ui.features.eventos

import com.ecoquest.app.models.Evento

data class EventosUiState(
    val eventos: List<Evento> = emptyList(),
    val eventosFiltrados: List<Evento> = emptyList(),
    val textoBusqueda: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
