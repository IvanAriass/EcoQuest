package com.ecoquest.app.ui.feature.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.RetoRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
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

data class PerfilUsuarioUiState(
    val usuario: Usuario = Usuario(),
    val comunidades: List<Comunidad> = emptyList(),
    val eventos: List<Evento> = emptyList(),
    val retos: List<Reto> = emptyList(),
    val transacciones: List<TransaccionPuntos> = emptyList(),
    val saldoPuntos: Int = 0,
    val showComunidades: Boolean = false,
    val showEventos: Boolean = false,
    val showRetos: Boolean = false,
    val showPuntos: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class PerfilUsuarioViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository,
    private val transaccionPuntosRepository: TransaccionPuntosRepository,
    private val retoRepository: RetoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PerfilUsuarioUiState())
    val state: StateFlow<PerfilUsuarioUiState> = _state.asStateFlow()

    fun onToggleComunidades() {
        _state.update { it.copy(showComunidades = !it.showComunidades, showEventos = false, showRetos = false, showPuntos = false) }
    }

    fun onToggleEventos() {
        _state.update { it.copy(showEventos = !it.showEventos, showComunidades = false, showRetos = false, showPuntos = false) }
    }

    fun onToggleRetos() {
        _state.update { it.copy(showRetos = !it.showRetos, showComunidades = false, showEventos = false, showPuntos = false) }
    }

    fun onTogglePuntos() {
        _state.update { it.copy(showPuntos = !it.showPuntos, showComunidades = false, showEventos = false, showRetos = false) }
    }

    fun cargarPerfil(usuarioId: Long) {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            usuarioRepository.getById(usuarioId).collect { usuario ->
                _state.update { it.copy(usuario = usuario ?: Usuario(), isLoading = false) }
            }
        }
        viewModelScope.launch {
            usuarioComunidadRepository.getComunidadesByUsuario(usuarioId).collect { comunidades ->
                _state.update { it.copy(comunidades = comunidades) }
            }
        }
        viewModelScope.launch {
            usuarioEventoRepository.getEventosByUsuario(usuarioId).collect { eventos ->
                _state.update { it.copy(eventos = eventos) }
            }
        }
        viewModelScope.launch {
            retoRepository.getAll().collect { retos ->
                _state.update { it.copy(retos = retos) }
            }
        }
        viewModelScope.launch {
            val saldo = transaccionPuntosRepository.getSaldo(usuarioId)
            _state.update { it.copy(saldoPuntos = saldo) }
        }
        viewModelScope.launch {
            transaccionPuntosRepository.getByUsuario(usuarioId).collect { transacciones ->
                _state.update { it.copy(transacciones = transacciones) }
            }
        }
    }
}
