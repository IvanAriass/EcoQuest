package com.ecoquest.app.ui.feature.retos

import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.model.TransaccionPuntos

data class RetosUiState(
    val retos: List<Reto> = emptyList(),
    val historial: List<TransaccionPuntos> = emptyList(),
    val saldoPuntos: Int = 0,
    val isLoading: Boolean = false
)
