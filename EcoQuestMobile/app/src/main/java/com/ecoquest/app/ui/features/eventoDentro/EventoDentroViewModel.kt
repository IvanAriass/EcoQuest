package com.ecoquest.app.ui.features.eventoDentro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.repositories.EventoRepository
import com.ecoquest.app.data.repositories.UsuarioEventoRepository
import com.ecoquest.app.data.services.UsuarioEventoEntity
import com.ecoquest.app.models.Evento
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventoDentroViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository
) : ViewModel() {

    var state by mutableStateOf(EventoDentroUiState())
        private set

    private var eventoId: Long = 0L

    fun cargarEvento(eventoId: Long) {
        this.eventoId = eventoId
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            eventoRepository.getById(eventoId).collect { eventoEntity ->
                if (eventoEntity != null) {
                    val evento = Evento(
                        id = eventoEntity.id,
                        nombre = eventoEntity.nombre,
                        descripcion = eventoEntity.descripcion,
                        fechaHora = runCatching { LocalDateTime.parse(eventoEntity.fechaHora) }.getOrDefault(LocalDateTime.now()),
                        ubicacion = eventoEntity.ubicacion,
                        imagen = eventoEntity.imagen,
                        estado = eventoEntity.estado
                    )
                    state = state.copy(evento = evento, isLoading = false)
                }
            }
        }

        viewModelScope.launch {
            usuarioEventoRepository.getByUsuario(1L).collect { relaciones ->
                state = state.copy(inscrito = relaciones.any { it.eventoId == eventoId })
            }
        }
    }

    fun onEvent(event: EventoDentroEvent) {
        when (event) {
            is EventoDentroEvent.OnInscribirse -> {
                viewModelScope.launch {
                    usuarioEventoRepository.upsert(
                        UsuarioEventoEntity(usuarioId = 1L, eventoId = eventoId)
                    )
                }
            }
            is EventoDentroEvent.OnDesinscribirse -> {
                viewModelScope.launch {
                    usuarioEventoRepository.delete(usuarioId = 1L, eventoId = eventoId)
                }
            }
        }
    }
}
