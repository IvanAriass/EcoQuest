package com.ecoquest.app.ui.feature.comunidades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.usecase.comunidades.GetComunidadesUseCase
import com.ecoquest.app.managers.TokenManager
import com.ecoquest.app.ui.components.comunidad.ComunidadDialogConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComunidadesViewModel @Inject constructor(
    private val getComunidadesUseCase: GetComunidadesUseCase,
    private val comunidadRepository: ComunidadRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(ComunidadesUiState())
    val state: StateFlow<ComunidadesUiState> = _state.asStateFlow()

    init {
        cargarComunidades()
    }

    fun onEvent(event: ComunidadesEvent) {
        when (event) {
            is ComunidadesEvent.OnBusquedaChanged -> {
                _state.update { it.copy(textoBusqueda = event.texto) }
            }
            is ComunidadesEvent.OnComunidadClick -> { }
            is ComunidadesEvent.OnCrearComunidad -> {
                _state.update { it.copy(dialogo = ComunidadDialogConfig.Crear) }
            }
            is ComunidadesEvent.OnGuardarComunidad -> {
                viewModelScope.launch {
                    val comunidad = Comunidad(nombre = event.nombre, descripcion = event.descripcion, imagen = event.imagen)
                    val creadorId = tokenManager.getUsuarioId()
                    comunidadRepository.upsert(comunidad)
                    if (creadorId > 0) {
                        comunidadRepository.crearEnServidor(comunidad, creadorId)
                    }
                    _state.update { it.copy(dialogo = null) }
                    cargarComunidades()
                }
            }
            is ComunidadesEvent.OnDismissDialog -> {
                _state.update { it.copy(dialogo = null) }
            }
            is ComunidadesEvent.OnRefrescar -> cargarComunidades()
        }
    }

    private fun cargarComunidades() {
        viewModelScope.launch {
            comunidadRepository.refreshComunidades()
        }
        viewModelScope.launch {
            getComunidadesUseCase().collect { comunidades ->
                _state.update { it.copy(comunidades = comunidades) }
            }
        }
    }
}
