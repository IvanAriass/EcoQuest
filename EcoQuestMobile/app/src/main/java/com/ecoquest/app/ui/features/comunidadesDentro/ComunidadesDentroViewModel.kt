package com.ecoquest.app.ui.features.comunidadesDentro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.CommunityMode
import com.ecoquest.app.data.repositories.ComunidadRepository
import com.ecoquest.app.data.repositories.EventoRepository
import com.ecoquest.app.data.services.ComunidadEntity
import com.ecoquest.app.data.services.EventoEntity
import com.ecoquest.app.models.Comunidad
import com.ecoquest.app.models.Evento
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ComunidadesDentroViewModel @Inject constructor(
    private val comunidadRepository: ComunidadRepository,
    private val eventoRepository: EventoRepository
) : ViewModel() {

    var state by mutableStateOf(ComunidadesDentroUiState())
        private set

    fun cargarComunidad(comunidadId: Long) {
        viewModelScope.launch {
            comunidadRepository.getById(comunidadId).collect { comunidadEntity ->
                if (comunidadEntity != null) {
                    val comunidad = Comunidad(
                        id = comunidadEntity.id,
                        nombre = comunidadEntity.nombre,
                        descripcion = comunidadEntity.descripcion,
                        imagen = comunidadEntity.imagen,
                        creadorId = comunidadEntity.creadorId
                    )
                    state = state.copy(
                        comunidad = comunidad,
                        esCreador = comunidadEntity.creadorId == 1L,
                        isLoading = false
                    )
                }
            }
        }
        // Cargar eventos
        viewModelScope.launch {
            eventoRepository.getByComunidad(comunidadId).collect { eventosEntity ->
                val eventos = eventosEntity.map { entity ->
                    Evento(
                        id = entity.id,
                        nombre = entity.nombre,
                        descripcion = entity.descripcion,
                        fechaHora = runCatching { LocalDateTime.parse(entity.fechaHora) }.getOrDefault(LocalDateTime.now()),
                        ubicacion = entity.ubicacion,
                        imagen = entity.imagen,
                        estado = entity.estado,
                        creadorId = entity.creadorId
                    )
                }
                state = state.copy(eventos = eventos)
            }
        }
    }

    fun onEvent(event: ComunidadesDentroEvent) {
        when (event) {
            is ComunidadesDentroEvent.OnAddEventoClick ->
                state = state.copy(dialogMode = CommunityMode.CREATE)

            is ComunidadesDentroEvent.OnEditComunidadClick ->
                state = state.copy(dialogMode = CommunityMode.EDIT)

            is ComunidadesDentroEvent.OnDismissDialog ->
                state = state.copy(dialogMode = CommunityMode.NONE, eventoEnEdicion = null)

            is ComunidadesDentroEvent.OnConfirmEditComunidad -> {
                viewModelScope.launch {
                    val updatedEntity = ComunidadEntity(
                        id = state.comunidad?.id ?: 0L,
                        nombre = event.nombre,
                        descripcion = event.descripcion,
                        imagen = state.comunidad?.imagen ?: ""
                    )
                    comunidadRepository.upsert(updatedEntity)
                    state = state.copy(dialogMode = CommunityMode.NONE)
                }
            }

            is ComunidadesDentroEvent.OnCreateEvento -> {
                viewModelScope.launch {
                    val nuevoEventoEntity = EventoEntity(
                        id = 0L,
                        nombre = event.nombre,
                        descripcion = event.descripcion,
                        fechaHora = event.fechaHora,
                        imagen = "",
                        estado = "Activo",
                        comunidadId = state.comunidad?.id ?: 0L,
                        creadorId = 1L
                    )
                    eventoRepository.upsert(nuevoEventoEntity)
                    state = state.copy(dialogMode = CommunityMode.NONE)
                }
            }

            is ComunidadesDentroEvent.OnEditEventoClick -> {
                val evento = state.eventos.find { it.id == event.eventoId }
                state = state.copy(dialogMode = CommunityMode.EDIT_EVENT, eventoEnEdicion = evento)
            }

            is ComunidadesDentroEvent.OnConfirmEditEvento -> {
                state.eventoEnEdicion?.let { eventoActual ->
                    viewModelScope.launch {
                        eventoRepository.upsert(
                            EventoEntity(
                                id = eventoActual.id,
                                nombre = event.nombre,
                                descripcion = event.descripcion,
                                fechaHora = event.fechaHora,
                                imagen = eventoActual.imagen,
                                estado = eventoActual.estado,
                                comunidadId = state.comunidad?.id ?: 0L,
                                creadorId = eventoActual.creadorId
                            )
                        )
                        state = state.copy(dialogMode = CommunityMode.NONE, eventoEnEdicion = null)
                    }
                }
            }
        }
    }
}
