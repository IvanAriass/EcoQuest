package com.ecoquest.app.ui.feature.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import com.ecoquest.app.domain.repository.UsuarioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val comunidadRepository: ComunidadRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PerfilUiState())
    val state: StateFlow<PerfilUiState> = _state.asStateFlow()

    init {
        cargarPerfil()
    }

    fun onEvent(event: PerfilEvent) {
        when (event) {
            is PerfilEvent.OnToggleComunidades -> {
                _state.update { it.copy(showComunidades = !it.showComunidades, showEventos = false) }
            }
            is PerfilEvent.OnToggleEventos -> {
                _state.update { it.copy(showEventos = !it.showEventos, showComunidades = false) }
            }
            is PerfilEvent.OnFotoSeleccionada -> {
                _state.update { it.copy(usuario = it.usuario.copy(imagen = event.uri)) }
                viewModelScope.launch {
                    usuarioRepository.upsert(_state.value.usuario.copy(imagen = event.uri))
                }
            }
        }
    }

    private fun cargarPerfil() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            usuarioRepository.getById(1L).collect { usuario ->
                _state.update { it.copy(usuario = usuario ?: Usuario(), isLoading = false) }
            }
        }
        viewModelScope.launch {
            usuarioComunidadRepository.getComunidadesByUsuario(1L).collect { comunidades ->
                _state.update { it.copy(comunidades = comunidades) }
            }
        }
        viewModelScope.launch {
            usuarioEventoRepository.getEventosByUsuario(1L).collect { eventos ->
                _state.update { it.copy(eventos = eventos) }
            }
        }
    }
}
