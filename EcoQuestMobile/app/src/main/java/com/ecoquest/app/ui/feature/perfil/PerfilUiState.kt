package com.ecoquest.app.ui.feature.perfil

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario

data class PerfilUiState(
    val usuario: Usuario = Usuario(),
    val comunidades: List<Comunidad> = emptyList(),
    val eventos: List<Evento> = emptyList(),
    val showComunidades: Boolean = false,
    val showEventos: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
