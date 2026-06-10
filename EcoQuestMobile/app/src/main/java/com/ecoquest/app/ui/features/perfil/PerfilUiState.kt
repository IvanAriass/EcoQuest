package com.ecoquest.app.ui.features.perfil

import com.ecoquest.app.models.Comunidad
import com.ecoquest.app.models.Evento
import com.ecoquest.app.models.Usuario

data class PerfilUiState(
    val usuario: Usuario = Usuario(),
    val comunidades: List<Comunidad> = emptyList(),
    val eventos: List<Evento> = emptyList(),
    val showComunidades: Boolean = false,
    val showEventos: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)
