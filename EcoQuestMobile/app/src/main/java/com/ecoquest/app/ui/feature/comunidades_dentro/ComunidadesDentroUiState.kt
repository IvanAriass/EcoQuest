package com.ecoquest.app.ui.feature.comunidades_dentro

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario

data class ComunidadesDentroUiState(
    val comunidad: Comunidad? = null,
    val eventos: List<Evento> = emptyList(),
    val miembros: List<Usuario> = emptyList(),
    val esMiembro: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val info: String? = null,
    val showCrearEventoDialog: Boolean = false
)
