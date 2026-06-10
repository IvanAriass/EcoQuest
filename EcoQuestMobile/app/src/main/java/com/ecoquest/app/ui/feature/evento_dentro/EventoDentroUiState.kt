package com.ecoquest.app.ui.feature.evento_dentro

import com.ecoquest.app.domain.model.Evento

data class EventoDentroUiState(
    val evento: Evento? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val comentario: String = "",
    val esParticipante: Boolean = false
)
