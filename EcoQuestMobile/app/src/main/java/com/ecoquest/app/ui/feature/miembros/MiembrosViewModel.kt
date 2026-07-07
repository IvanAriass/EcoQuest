package com.ecoquest.app.ui.feature.miembros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.domain.model.RolInfo
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.RolRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MiembroConRol(
    val usuario: Usuario,
    val rolInfo: RolInfo
)

data class MiembrosUiState(
    val miembros: List<MiembroConRol> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class MiembrosViewModel @Inject constructor(
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val usuarioComunidadDao: UsuarioComunidadDao,
    private val rolRepository: RolRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MiembrosUiState())
    val state: StateFlow<MiembrosUiState> = _state.asStateFlow()

    fun cargarMiembros(comunidadId: Long) {
        viewModelScope.launch {
            val miembrosFlow = usuarioComunidadRepository.getMiembrosByComunidad(comunidadId)
            val rolesFlow = usuarioComunidadDao.getByComunidad(comunidadId)

            rolRepository.refreshRoles()
            miembrosFlow.combine(rolesFlow) { miembros, relaciones ->
                miembros.map { usuario ->
                    val rol = relaciones.find { it.usuarioId == usuario.id }?.rol ?: "SEMILLA"
                    MiembroConRol(usuario = usuario, rolInfo = rolRepository.getRolById(rol))
                }
            }.collect { miembrosConRol ->
                _state.update { it.copy(miembros = miembrosConRol, isLoading = false) }
            }
        }
    }
}
