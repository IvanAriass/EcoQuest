package com.ecoquest.app.ui.feature.comunidades.detalle

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.ui.components.evento.EventoDialogConfig

data class ComunidadDetalleUiState(
    val comunidad: Comunidad? = null,
    val eventos: List<Evento> = emptyList(),
    val miembros: List<Usuario> = emptyList(),
    val esMiembro: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val info: String? = null,
    val dialogo: EventoDialogConfig? = null
)
