package com.ecoquest.app.ui.features.eventos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.repositories.ComunidadRepository
import com.ecoquest.app.data.repositories.EventoRepository
import com.ecoquest.app.data.services.ComunidadEntity
import com.ecoquest.app.data.services.EventoEntity
import com.ecoquest.app.models.Evento
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventosViewModel @Inject constructor(
    private val eventoRepository: EventoRepository,
    private val comunidadRepository: ComunidadRepository
) : ViewModel() {

    var state by mutableStateOf(EventosUiState())
        private set

    init {
        viewModelScope.launch {
            comunidadRepository.insertAllIfAbsent(comunidadesSeed)
            eventoRepository.insertAllIfAbsent(eventosSeed)
        }
        viewModelScope.launch {
            eventoRepository.getAll().collect { eventosEntity ->
                val eventos = eventosEntity.map { entity ->
                    Evento(
                        id = entity.id,
                        nombre = entity.nombre,
                        descripcion = entity.descripcion,
                        fechaHora = runCatching { LocalDateTime.parse(entity.fechaHora) }.getOrDefault(LocalDateTime.now()),
                        ubicacion = entity.ubicacion,
                        imagen = entity.imagen,
                        estado = entity.estado
                    )
                }
                state = state.copy(
                    eventos = eventos,
                    eventosFiltrados = filtrar(eventos, state.textoBusqueda),
                    isLoading = false
                )
            }
        }
    }

    fun onEvent(event: EventosEvent) {
        when (event) {
            is EventosEvent.OnBusquedaChanged -> {
                state = state.copy(
                    textoBusqueda = event.texto,
                    eventosFiltrados = filtrar(state.eventos, event.texto)
                )
            }
            is EventosEvent.OnEventoClick -> { /* Interceptado en el destination */ }
        }
    }

    private fun filtrar(eventos: List<Evento>, texto: String): List<Evento> {
        if (texto.isBlank()) return eventos
        return eventos.filter { it.nombre.contains(texto, ignoreCase = true) }
    }

    companion object {
        private const val BASE = "android.resource://com.ecoquest.app/drawable"

        private val comunidadesSeed = listOf(
            ComunidadEntity(id = 1L, nombre = "Todos Unidos",
                descripcion = "Somos la Comunidad Eco-Conexión, y ¡acabamos de aterrizar en esta aplicación!",
                imagen = "$BASE/todosunidos"),
            ComunidadEntity(id = 2L, nombre = "EcoWarriors",
                descripcion = "Comprometidos con el medio ambiente y la sostenibilidad local.",
                imagen = "$BASE/comunidad3"),
            ComunidadEntity(id = 3L, nombre = "Verde Urbano",
                descripcion = "Transformando espacios urbanos en zonas más verdes y habitables.",
                imagen = "$BASE/comunidad4"),
        )

        private val eventosSeed = listOf(
            EventoEntity(
                id = 1L,
                nombre = "Recogida de Basura",
                descripcion = "En la playa del Postiguer",
                fechaHora = "2026-06-01T10:00:00",
                ubicacion = "Valencia",
                imagen = "$BASE/playa",
                estado = "Evento Comunitario",
                comunidadId = 1L
            ),
            EventoEntity(
                id = 2L,
                nombre = "Nuevos fondos ODS",
                descripcion = "Mayor remuneración para proyectos sostenibles",
                fechaHora = "2026-06-05T12:00:00",
                ubicacion = "Madrid",
                imagen = "$BASE/periodicos",
                estado = "Noticia",
                comunidadId = 1L
            ),
            EventoEntity(
                id = 3L,
                nombre = "Junta de Alimentos",
                descripcion = "En plaza mar",
                fechaHora = "2026-06-10T09:00:00",
                ubicacion = "Barcelona",
                imagen = "$BASE/todosunidos",
                estado = "Urgente",
                comunidadId = 1L
            )
        )
    }
}
