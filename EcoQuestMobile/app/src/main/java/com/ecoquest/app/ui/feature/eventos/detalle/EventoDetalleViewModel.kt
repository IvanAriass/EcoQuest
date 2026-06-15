package com.ecoquest.app.ui.feature.eventos.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.ComentarioRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import com.ecoquest.app.domain.repository.UsuarioRepository
import com.ecoquest.app.domain.usecase.eventos.JoinEventoUseCase
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventoDetalleViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository,
    private val joinEventoUseCase: JoinEventoUseCase,
    private val tokenManager: TokenManager,
    private val usuarioRepository: UsuarioRepository,
    private val transaccionPuntosRepository: TransaccionPuntosRepository,
    private val comentarioRepository: ComentarioRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EventoDetalleUiState())
    val state: StateFlow<EventoDetalleUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    private var usuarioNombre: String = ""
    private var currentEventoId: Long = 0

    fun cargarEvento(eventoId: Long) {
        currentEventoId = eventoId
        viewModelScope.launch {
            eventoRepository.refreshEventos()
        }
        viewModelScope.launch {
            usuarioRepository.getById(usuarioId).collect { usuario ->
                usuarioNombre = usuario?.nombre?.takeIf { it.isNotBlank() } ?: usuario?.nombreUsuario ?: "Usuario"
            }
        }
        viewModelScope.launch {
            eventoRepository.getById(eventoId).collect { evento ->
                _state.update { it.copy(evento = evento) }
            }
        }
        viewModelScope.launch {
            usuarioEventoRepository.getUsuariosByEvento(eventoId).collect { usuarios ->
                _state.update { it.copy(esParticipante = usuarios.any { u -> u.id == usuarioId }) }
            }
        }
        viewModelScope.launch {
            comentarioRepository.refreshComentarios(eventoId)
        }
        viewModelScope.launch {
            comentarioRepository.getByEventoId(eventoId).collect { comentarios ->
                _state.update { it.copy(comentarios = comentarios) }
            }
        }
    }

    fun onEvent(event: EventoDetalleEvent) {
        when (event) {
            is EventoDetalleEvent.OnComentarioChanged -> {
                _state.update { it.copy(comentario = event.texto) }
            }
            is EventoDetalleEvent.OnEnviarComentario -> {
                val texto = _state.value.comentario.trim()
                if (texto.isNotBlank()) {
                    viewModelScope.launch {
                        comentarioRepository.crear(currentEventoId, usuarioId, texto)
                        _state.update { it.copy(comentario = "") }
                    }
                }
            }
            is EventoDetalleEvent.OnUnirse -> {
                viewModelScope.launch {
                    _state.value.evento?.let { joinEventoUseCase(usuarioId, it.id) }
                    _state.update { it.copy(esParticipante = true) }
                    transaccionPuntosRepository.refresh(usuarioId, notifyNew = true)
                }
            }
            is EventoDetalleEvent.OnAbandonar -> {
                viewModelScope.launch {
                    _state.value.evento?.let { usuarioEventoRepository.abandonar(usuarioId, it.id) }
                    _state.update { it.copy(esParticipante = false) }
                }
            }
            is EventoDetalleEvent.OnNavigateToPerfilUsuario -> { }
            is EventoDetalleEvent.OnExpandirComentario -> {
                val comentarioId = event.comentarioId
                _state.update { it.copy(comentarioExpandidos = it.comentarioExpandidos + comentarioId) }
                viewModelScope.launch {
                    val respuestas = comentarioRepository.obtenerRespuestas(currentEventoId, comentarioId)
                    _state.update { state ->
                        state.copy(respuestasMap = state.respuestasMap + (comentarioId to respuestas))
                    }
                }
            }
            is EventoDetalleEvent.OnColapsarComentario -> {
                _state.update { it.copy(comentarioExpandidos = it.comentarioExpandidos - event.comentarioId) }
            }
            is EventoDetalleEvent.OnResponderA -> {
                _state.update { it.copy(respondiendoAId = event.comentarioId, textoRespuesta = "") }
            }
            is EventoDetalleEvent.OnTextoRespuestaChanged -> {
                _state.update { it.copy(textoRespuesta = event.texto) }
            }
            is EventoDetalleEvent.OnCancelarRespuesta -> {
                _state.update { it.copy(respondiendoAId = null, textoRespuesta = "") }
            }
            is EventoDetalleEvent.OnToggleComentariosSheet -> {
                _state.update { it.copy(showComentariosSheet = !it.showComentariosSheet) }
            }
            is EventoDetalleEvent.OnEnviarRespuesta -> {
                val texto = _state.value.textoRespuesta.trim()
                val padreId = _state.value.respondiendoAId
                if (texto.isNotBlank() && padreId != null) {
                    viewModelScope.launch {
                        comentarioRepository.crear(currentEventoId, usuarioId, texto, padreId)
                        val respuestas = comentarioRepository.obtenerRespuestas(currentEventoId, padreId)
                        _state.update { state ->
                            state.copy(
                                respuestasMap = state.respuestasMap + (padreId to respuestas),
                                respondiendoAId = null,
                                textoRespuesta = ""
                            )
                        }
                    }
                }
            }
        }
    }
}
