package com.ecoquest.app.ui.features.perfil

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.repositories.ComunidadRepository
import com.ecoquest.app.data.repositories.EventoRepository
import com.ecoquest.app.data.repositories.UsuarioComunidadRepository
import com.ecoquest.app.data.repositories.UsuarioEventoRepository
import com.ecoquest.app.data.repositories.UsuarioRepository
import com.ecoquest.app.data.services.UsuarioEntity
import com.ecoquest.app.models.Comunidad
import com.ecoquest.app.models.Evento
import com.ecoquest.app.models.Usuario
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val comunidadRepository: ComunidadRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository
) : ViewModel() {

    var state by mutableStateOf(PerfilUiState())
        private set

    init {
        cargarPerfil()
    }

    fun onEvent(event: PerfilEvent) {
        when (event) {
            is PerfilEvent.OnToggleComunidades -> {
                state = state.copy(
                    showComunidades = !state.showComunidades,
                    showEventos = false
                )
            }
            is PerfilEvent.OnToggleEventos -> {
                state = state.copy(
                    showEventos = !state.showEventos,
                    showComunidades = false
                )
            }
            is PerfilEvent.OnFotoSeleccionada -> {
                val usuarioActualizado = state.usuario.copy(imagen = event.uri)
                state = state.copy(usuario = usuarioActualizado)
                viewModelScope.launch {
                    usuarioRepository.upsert(
                        UsuarioEntity(
                            id = state.usuario.id,
                            nombreUsuario = state.usuario.nombreUsuario,
                            nombre = state.usuario.nombre,
                            apellido = state.usuario.apellido,
                            descripcion = state.usuario.descripcion,
                            edad = state.usuario.edad,
                            email = state.usuario.email,
                            imagen = event.uri
                        )
                    )
                }
            }
        }
    }

    private fun cargarPerfil() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                // Cargar usuario actual (asumiendo ID = 1)
                usuarioRepository.getById(1L).collect { usuarioEntity ->
                    if (usuarioEntity != null) {
                        val usuario = Usuario(
                            id = usuarioEntity.id,
                            nombreUsuario = usuarioEntity.nombreUsuario,
                            nombre = usuarioEntity.nombre,
                            apellido = usuarioEntity.apellido,
                            descripcion = usuarioEntity.descripcion,
                            edad = usuarioEntity.edad,
                            email = usuarioEntity.email,
                            imagen = usuarioEntity.imagen
                        )
                        state = state.copy(usuario = usuario, isLoading = false)
                    }
                }
            } catch (e: Exception) {
                state = state.copy(error = "Error al cargar usuario: ${e.message}", isLoading = false)
            }
        }

        // Cargar comunidades del usuario
        viewModelScope.launch {
            try {
                usuarioComunidadRepository.getByUsuario(1L).collect { usuarioComunidadList ->
                    val comunidades = mutableListOf<Comunidad>()
                    usuarioComunidadList.forEach { relacion ->
                        comunidadRepository.getById(relacion.comunidadId).collect { comunidadEntity ->
                            if (comunidadEntity != null) {
                                val comunidad = Comunidad(
                                    id = comunidadEntity.id,
                                    nombre = comunidadEntity.nombre,
                                    descripcion = comunidadEntity.descripcion,
                                    imagen = comunidadEntity.imagen
                                )
                                comunidades.add(comunidad)
                            }
                        }
                    }
                    state = state.copy(comunidades = comunidades)
                }
            } catch (e: Exception) {
                state = state.copy(error = "Error al cargar comunidades: ${e.message}")
            }
        }

        // Cargar eventos del usuario
        viewModelScope.launch {
            try {
                usuarioEventoRepository.getByUsuario(1L).collect { usuarioEventoList ->
                    val eventos = mutableListOf<Evento>()
                    usuarioEventoList.forEach { relacion ->
                        eventoRepository.getById(relacion.eventoId).collect { eventoEntity ->
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
                                eventos.add(evento)
                            }
                        }
                    }
                    state = state.copy(eventos = eventos)
                }
            } catch (e: Exception) {
                state = state.copy(error = "Error al cargar eventos: ${e.message}")
            }
        }
    }
}
