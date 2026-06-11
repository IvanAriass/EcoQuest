package com.ecoquest.app.ui.feature.eventos.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import com.ecoquest.app.domain.usecase.eventos.JoinEventoUseCase
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
    private val joinEventoUseCase: JoinEventoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EventoDetalleUiState())
    val state: StateFlow<EventoDetalleUiState> = _state.asStateFlow()

    fun cargarEvento(eventoId: Long) {
        viewModelScope.launch {
            eventoRepository.getById(eventoId).collect { evento ->
                _state.update { it.copy(evento = evento) }
            }
        }
    }

    fun onEvent(event: EventoDetalleEvent) {
        when (event) {
            is EventoDetalleEvent.OnComentarioChanged -> {
                _state.update { it.copy(comentario = event.texto) }
            }
            is EventoDetalleEvent.OnEnviarComentario -> {
                _state.update { it.copy(comentario = "") }
            }
            is EventoDetalleEvent.OnUnirse -> {
                viewModelScope.launch {
                    _state.value.evento?.let { joinEventoUseCase(1L, it.id) }
                }
            }
            is EventoDetalleEvent.OnAbandonar -> {
                viewModelScope.launch {
                    _state.value.evento?.let { usuarioEventoRepository.abandonar(1L, it.id) }
                }
            }
        }
    }
}
