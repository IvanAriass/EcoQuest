package com.ecoquest.app.ui.feature.comunidades.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import com.ecoquest.app.domain.usecase.comunidades.JoinComunidadUseCase
import com.ecoquest.app.managers.TokenManager
import com.ecoquest.app.ui.components.evento.EventoDialogConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComunidadDetalleViewModel @Inject constructor(
    private val comunidadRepository: ComunidadRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val joinComunidadUseCase: JoinComunidadUseCase,
    private val tokenManager: TokenManager,
    private val transaccionPuntosRepository: TransaccionPuntosRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ComunidadDetalleUiState())
    val state: StateFlow<ComunidadDetalleUiState> = _state.asStateFlow()

    private var comunidadId: Long = 0

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    fun cargarComunidad(id: Long) {
        comunidadId = id
        viewModelScope.launch {
            comunidadRepository.refreshComunidades()
        }
        viewModelScope.launch {
            eventoRepository.refreshEventos()
        }
        viewModelScope.launch {
            comunidadRepository.getById(id).collect { comunidad ->
                _state.update { it.copy(comunidad = comunidad) }
            }
        }
        viewModelScope.launch {
            eventoRepository.getAll().collect { todosEventos ->
                val comunidad = _state.value.comunidad
                val nombre = comunidad?.nombre ?: ""
                val filtrados = todosEventos.filter { evento ->
                    evento.comunidadId == id ||
                    (nombre.isNotBlank() && evento.nombreComunidad == nombre)
                }
                _state.update { it.copy(eventos = filtrados) }
            }
        }
        viewModelScope.launch {
            usuarioComunidadRepository.getMiembrosByComunidad(id).collect { miembros ->
                val esMiembro = miembros.any { it.id == usuarioId }
                _state.update { it.copy(miembros = miembros, esMiembro = esMiembro) }
            }
        }
    }

    fun onEvent(event: ComunidadDetalleEvent) {
        when (event) {
            is ComunidadDetalleEvent.OnMostrarCrearEvento -> {
                _state.update { it.copy(dialogo = EventoDialogConfig.Crear) }
            }
            is ComunidadDetalleEvent.OnGuardarEvento -> {
                viewModelScope.launch {
                    eventoRepository.upsert(
                        Evento(
                            nombre = event.nombre,
                            descripcion = event.descripcion,
                            fechaHora = event.fechaHora,
                            ubicacion = event.ubicacion,
                            imagen = event.imagen,
                            comunidadId = comunidadId,
                            creadorId = usuarioId
                        )
                    )
                    _state.update { it.copy(dialogo = null) }
                }
            }
            is ComunidadDetalleEvent.OnDismissDialog -> {
                _state.update { it.copy(dialogo = null) }
            }
            is ComunidadDetalleEvent.OnInfoChanged -> {
                _state.update { it.copy(info = event.info) }
            }
            is ComunidadDetalleEvent.OnUnirse -> {
                viewModelScope.launch {
                    joinComunidadUseCase(usuarioId, comunidadId, "MIEMBRO")
                    _state.update { it.copy(esMiembro = true) }
                    transaccionPuntosRepository.refresh(usuarioId, notifyNew = true)
                }
            }
            is ComunidadDetalleEvent.OnAbandonar -> {
                viewModelScope.launch {
                    usuarioComunidadRepository.abandonar(usuarioId, comunidadId)
                    _state.update { it.copy(esMiembro = false) }
                }
            }
        }
    }
}
