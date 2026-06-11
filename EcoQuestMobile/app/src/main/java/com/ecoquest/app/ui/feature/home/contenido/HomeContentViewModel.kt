package com.ecoquest.app.ui.feature.home.contenido

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
class HomeContentViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository,
    private val comunidadRepository: ComunidadRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContentUiState())
    val state: StateFlow<HomeContentUiState> = _state.asStateFlow()

    init {
        cargarDatos()
    }

    fun onEvent(event: HomeContentEvent) { }

    private fun cargarDatos() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            usuarioRepository.getById(1L).collect { usuario ->
                _state.update { it.copy(usuario = usuario ?: Usuario(), isLoading = false) }
            }
        }

        viewModelScope.launch {
            usuarioEventoRepository.getEventosByUsuario(1L).collect { eventos ->
                _state.update { it.copy(proximosEventos = eventos.take(5)) }
            }
        }

        viewModelScope.launch {
            usuarioComunidadRepository.getComunidadesByUsuario(1L).collect { comunidades ->
                _state.update { it.copy(comunidades = comunidades) }
            }
        }
    }
}
