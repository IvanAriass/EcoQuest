package com.ecoquest.app.ui.features.eventoDentro

import com.ecoquest.app.models.Evento

data class EventoDentroUiState(
    val evento: Evento? = null,
    val inscrito: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
