package com.ecoquest.app.ui.feature.eventos.detalle

import com.ecoquest.app.domain.model.Comentario
import com.ecoquest.app.domain.model.Evento

data class EventoDetalleUiState(
    val evento: Evento? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val comentario: String = "",
    val comentarios: List<Comentario> = emptyList(),
    val esParticipante: Boolean = false,
    val comentarioExpandidos: Set<Long> = emptySet(),
    val respuestasMap: Map<Long, List<Comentario>> = emptyMap(),
    val respondiendoAId: Long? = null,
    val textoRespuesta: String = "",
    val showComentariosSheet: Boolean = false
)
