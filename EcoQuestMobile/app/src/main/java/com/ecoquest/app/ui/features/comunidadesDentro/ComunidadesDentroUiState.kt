package com.ecoquest.app.ui.features.comunidadesDentro

import com.ecoquest.app.CommunityMode
import com.ecoquest.app.models.Comunidad
import com.ecoquest.app.models.Evento

data class ComunidadesDentroUiState(
    val comunidad: Comunidad? = null,
    val eventos: List<Evento> = emptyList(),
    val esCreador: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val dialogMode: CommunityMode = CommunityMode.NONE,
    val eventoEnEdicion: Evento? = null
)
