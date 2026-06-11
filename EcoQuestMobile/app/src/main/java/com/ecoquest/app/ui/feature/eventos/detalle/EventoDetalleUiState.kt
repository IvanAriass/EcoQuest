package com.ecoquest.app.ui.feature.eventos.detalle

import com.ecoquest.app.domain.model.Evento

data class EventoDetalleUiState(
    val evento: Evento? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val comentario: String = "",
    val esParticipante: Boolean = false
)
