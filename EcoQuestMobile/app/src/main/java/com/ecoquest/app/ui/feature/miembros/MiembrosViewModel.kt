package com.ecoquest.app.ui.feature.miembros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MiembrosUiState(
    val miembros: List<com.ecoquest.app.domain.model.Usuario> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class MiembrosViewModel @Inject constructor(
    private val usuarioComunidadRepository: UsuarioComunidadRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MiembrosUiState())
    val state: StateFlow<MiembrosUiState> = _state.asStateFlow()

    fun cargarMiembros(comunidadId: Long) {
        viewModelScope.launch {
            usuarioComunidadRepository.getMiembrosByComunidad(comunidadId).collect { miembros ->
                _state.update { it.copy(miembros = miembros, isLoading = false) }
            }
        }
    }
}
