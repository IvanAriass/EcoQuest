package com.ecoquest.app.ui.feature.evento_dentro

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
class EventoDentroViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository,
    private val joinEventoUseCase: JoinEventoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EventoDentroUiState())
    val state: StateFlow<EventoDentroUiState> = _state.asStateFlow()

    fun cargarEvento(eventoId: Long) {
        viewModelScope.launch {
            eventoRepository.getById(eventoId).collect { evento ->
                _state.update { it.copy(evento = evento) }
            }
        }
    }

    fun onEvent(event: EventoDentroEvent) {
        when (event) {
            is EventoDentroEvent.OnComentarioChanged -> {
                _state.update { it.copy(comentario = event.texto) }
            }
            is EventoDentroEvent.OnEnviarComentario -> {
                _state.update { it.copy(comentario = "") }
            }
            is EventoDentroEvent.OnUnirse -> {
                viewModelScope.launch {
                    _state.value.evento?.let { joinEventoUseCase(1L, it.id) }
                }
            }
            is EventoDentroEvent.OnAbandonar -> {
                viewModelScope.launch {
                    _state.value.evento?.let { usuarioEventoRepository.abandonar(1L, it.id) }
                }
            }
        }
    }
}
