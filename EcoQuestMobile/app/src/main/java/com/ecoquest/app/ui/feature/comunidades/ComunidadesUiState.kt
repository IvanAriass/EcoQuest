package com.ecoquest.app.ui.feature.comunidades

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.ui.components.comunidad.ComunidadDialogConfig

data class ComunidadesUiState(
    val comunidades: List<Comunidad> = emptyList(),
    val textoBusqueda: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val dialogo: ComunidadDialogConfig? = null
) {
    val comunidadesFiltradas: List<Comunidad>
        get() = if (textoBusqueda.isBlank()) comunidades
        else comunidades.filter {
            it.nombre.contains(textoBusqueda, ignoreCase = true) ||
            it.descripcion.contains(textoBusqueda, ignoreCase = true)
        }
}
