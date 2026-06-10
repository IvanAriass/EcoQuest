package com.ecoquest.app.ui.features.comunidades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.CommunityMode
import com.ecoquest.app.data.repositories.ComunidadRepository
import com.ecoquest.app.data.services.ComunidadEntity
import com.ecoquest.app.models.Comunidad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComunidadesViewModel @Inject constructor(
    private val comunidadRepository: ComunidadRepository
) : ViewModel() {

    var state by mutableStateOf(ComunidadesUiState())
        private set

    init {
        viewModelScope.launch {
            comunidadRepository.insertAllIfAbsent(comunidadesSeed)
        }
        viewModelScope.launch {
            comunidadRepository.getAll().collect { comunidadesEntity ->
                val comunidades = comunidadesEntity.map { entity ->
                    Comunidad(
                        id = entity.id,
                        nombre = entity.nombre,
                        descripcion = entity.descripcion,
                        imagen = entity.imagen,
                        creadorId = entity.creadorId
                    )
                }
                state = state.copy(comunidades = comunidades, isLoading = false)
            }
        }
    }

    companion object {
        private val comunidadesSeed = listOf(
            ComunidadEntity(id = 1L, nombre = "Todos Unidos",
                descripcion = "Somos la Comunidad Eco-Conexión, y ¡acabamos de aterrizar en esta aplicación!",
                imagen = "android.resource://com.ecoquest.app/drawable/todosunidos"),
            ComunidadEntity(id = 2L, nombre = "EcoWarriors",
                descripcion = "Comprometidos con el medio ambiente y la sostenibilidad local.",
                imagen = "android.resource://com.ecoquest.app/drawable/comunidad3"),
            ComunidadEntity(id = 3L, nombre = "Verde Urbano",
                descripcion = "Transformando espacios urbanos en zonas más verdes y habitables.",
                imagen = "android.resource://com.ecoquest.app/drawable/comunidad4"),
        )
    }

    fun onEvent(event: ComunidadesEvent) {
        when (event) {
            is ComunidadesEvent.OnAddComunidadClick -> onAddComunidadClick()
            is ComunidadesEvent.OnDismissDialog -> onDismissDialog()
            is ComunidadesEvent.OnCreateComunidad -> onCreateComunidad(event.nombre, event.descripcion, event.imagenUri)
            is ComunidadesEvent.OnComunidadClick -> { /* Interceptado en el destination */ }
        }
    }

    private fun cargarComunidades() {
        state = state.copy(isLoading = true)
        // TODO: llamada al repositorio
        state = state.copy(isLoading = false, comunidades = emptyList())
    }

    private fun onAddComunidadClick() {
        state = state.copy(dialogMode = CommunityMode.CREATE)
    }

    private fun onDismissDialog() {
        state = state.copy(dialogMode = CommunityMode.NONE)
    }

    private fun onCreateComunidad(nombre: String, descripcion: String, imagenUri: String) {
        if (nombre.isBlank() || descripcion.isBlank()) {
            state = state.copy(error = "El nombre y la descripción no pueden estar vacíos")
            return
        }

        viewModelScope.launch {
            val nuevaComunidadEntity = com.ecoquest.app.data.services.ComunidadEntity(
                id = (state.comunidades.maxOfOrNull { it.id } ?: 0L) + 1L,
                nombre = nombre,
                descripcion = descripcion,
                imagen = imagenUri,
                creadorId = 1L
            )
            comunidadRepository.upsert(nuevaComunidadEntity)
            state = state.copy(dialogMode = CommunityMode.NONE, error = null)
        }
    }
}