package com.ecoquest.app.ui.features.comunidades

import com.ecoquest.app.CommunityMode
import com.ecoquest.app.models.Comunidad

data class ComunidadesUiState(
    val comunidades: List<Comunidad> = emptyList(),
    val dialogMode: CommunityMode = CommunityMode.NONE,
    val isLoading: Boolean = false,
    val error: String? = null
)