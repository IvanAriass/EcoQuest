package com.ecoquest.app.ui.feature.eventos.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EventoDetalleViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository,
    private val joinEventoUseCase: JoinEventoUseCase,
    private val tokenManager: TokenManager,
    private val usuarioRepository: UsuarioRepository,
    private val transaccionPuntosRepository: TransaccionPuntosRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EventoDetalleUiState())
    val state: StateFlow<EventoDetalleUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    private var usuarioNombre: String = ""

    fun cargarEvento(eventoId: Long) {
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
    }

    fun onEvent(event: EventoDetalleEvent) {
        when (event) {
            is EventoDetalleEvent.OnComentarioChanged -> {
                _state.update { it.copy(comentario = event.texto) }
            }
            is EventoDetalleEvent.OnEnviarComentario -> {
                val texto = _state.value.comentario.trim()
                if (texto.isNotBlank()) {
                    val nuevo = Comentario(
                        id = System.currentTimeMillis(),
                        usuarioNombre = usuarioNombre,
                        texto = texto,
                        fechaHora = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    )
                    _state.update {
                        it.copy(
                            comentarios = it.comentarios + nuevo,
                            comentario = ""
                        )
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
        }
    }
}
