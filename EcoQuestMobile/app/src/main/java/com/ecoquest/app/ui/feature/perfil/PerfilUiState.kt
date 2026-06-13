package com.ecoquest.app.ui.feature.perfil

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.model.Usuario

data class PerfilUiState(
    val usuario: Usuario = Usuario(),
    val comunidades: List<Comunidad> = emptyList(),
    val eventos: List<Evento> = emptyList(),
    val retos: List<Reto> = emptyList(),
    val transacciones: List<TransaccionPuntos> = emptyList(),
    val saldoPuntos: Int = 0,
    val showComunidades: Boolean = false,
    val showEventos: Boolean = false,
    val showRetos: Boolean = false,
    val showPuntos: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
