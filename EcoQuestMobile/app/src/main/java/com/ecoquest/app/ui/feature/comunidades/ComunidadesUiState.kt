package com.ecoquest.app.ui.feature.comunidades

import com.ecoquest.app.domain.model.Comunidad

data class ComunidadesUiState(
    val comunidades: List<Comunidad> = emptyList(),
    val textoBusqueda: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val showCrearDialog: Boolean = false,
    val showEditarDialog: Boolean = false,
    val comunidadAEditar: Comunidad? = null
)
