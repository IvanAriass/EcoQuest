package com.ecoquest.app.ui.feature.comunidades_dentro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import com.ecoquest.app.domain.usecase.comunidades.JoinComunidadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComunidadesDentroViewModel @Inject constructor(
    private val comunidadRepository: ComunidadRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val joinComunidadUseCase: JoinComunidadUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ComunidadesDentroUiState())
    val state: StateFlow<ComunidadesDentroUiState> = _state.asStateFlow()

    private var comunidadId: Long = 0

    fun cargarComunidad(id: Long) {
        comunidadId = id
        viewModelScope.launch {
            comunidadRepository.getById(id).collect { comunidad ->
                _state.update { it.copy(comunidad = comunidad) }
            }
        }
        viewModelScope.launch {
            eventoRepository.getByComunidad(id).collect { eventos ->
                _state.update { it.copy(eventos = eventos) }
            }
        }
        viewModelScope.launch {
            usuarioComunidadRepository.getMiembrosByComunidad(id).collect { miembros ->
                _state.update { it.copy(miembros = miembros) }
            }
        }
    }

    fun onEvent(event: ComunidadesDentroEvent) {
        when (event) {
            is ComunidadesDentroEvent.OnMostrarCrearEvento -> {
                _state.update { it.copy(showCrearEventoDialog = true) }
            }
            is ComunidadesDentroEvent.OnGuardarEvento -> {
                viewModelScope.launch {
                    eventoRepository.upsert(Evento(nombre = event.nombre, descripcion = event.descripcion, fechaHora = event.fechaHora, ubicacion = event.ubicacion, imagen = event.imagen, comunidadId = comunidadId, creadorId = 1L))
                    _state.update { it.copy(showCrearEventoDialog = false) }
                }
            }
            is ComunidadesDentroEvent.OnDismissDialog -> {
                _state.update { it.copy(showCrearEventoDialog = false) }
            }
            is ComunidadesDentroEvent.OnEliminarEvento -> {
                viewModelScope.launch { eventoRepository.delete(Evento(id = event.eventoId)) }
            }
            is ComunidadesDentroEvent.OnInfoChanged -> {
                _state.update { it.copy(info = event.info) }
            }
            is ComunidadesDentroEvent.OnUnirse -> {
                viewModelScope.launch { joinComunidadUseCase(1L, comunidadId, "miembro") }
            }
            is ComunidadesDentroEvent.OnAbandonar -> {
                viewModelScope.launch { usuarioComunidadRepository.abandonar(1L, comunidadId) }
            }
        }
    }
}
