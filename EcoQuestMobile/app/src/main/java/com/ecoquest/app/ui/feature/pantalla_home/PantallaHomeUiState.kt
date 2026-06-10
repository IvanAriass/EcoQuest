package com.ecoquest.app.ui.feature.pantalla_home

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario

data class PantallaHomeUiState(
    val usuario: Usuario = Usuario(),
    val proximosEventos: List<Evento> = emptyList(),
    val comunidades: List<Comunidad> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
