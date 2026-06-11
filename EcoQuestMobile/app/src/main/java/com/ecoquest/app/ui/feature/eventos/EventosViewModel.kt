package com.ecoquest.app.ui.feature.eventos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.usecase.eventos.GetEventosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventosViewModel @Inject constructor(
    private val getEventosUseCase: GetEventosUseCase,
    private val eventoRepository: EventoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EventosUiState())
    val state: StateFlow<EventosUiState> = _state.asStateFlow()

    init {
        cargarEventos()
    }

    fun onEvent(event: EventosEvent) {
        when (event) {
            is EventosEvent.OnBusquedaChanged -> {
                _state.update { it.copy(textoBusqueda = event.texto) }
            }
        }
    }

    private fun cargarEventos() {
        viewModelScope.launch {
            eventoRepository.refreshEventos()
        }
        viewModelScope.launch {
            getEventosUseCase().collect { eventos ->
                _state.update { it.copy(eventos = eventos) }
            }
        }
    }
}
