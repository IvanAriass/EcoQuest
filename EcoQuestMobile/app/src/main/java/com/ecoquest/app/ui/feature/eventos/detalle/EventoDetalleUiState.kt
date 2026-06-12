package com.ecoquest.app.ui.feature.eventos.detalle

import com.ecoquest.app.domain.model.Evento

data class Comentario(
    val id: Long = 0,
    val usuarioNombre: String = "",
    val texto: String = "",
    val fechaHora: String = ""
)

data class EventoDetalleUiState(
    val evento: Evento? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val comentario: String = "",
    val comentarios: List<Comentario> = emptyList(),
    val esParticipante: Boolean = false
)
