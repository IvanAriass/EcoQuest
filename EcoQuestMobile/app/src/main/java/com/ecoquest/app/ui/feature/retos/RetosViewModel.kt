package com.ecoquest.app.ui.feature.retos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.RetoRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RetosViewModel @Inject constructor(
    private val retoRepository: RetoRepository,
    private val transaccionPuntosRepository: TransaccionPuntosRepository,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(RetosUiState())
    val state: StateFlow<RetosUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            retoRepository.refreshRetos()
        }
        viewModelScope.launch {
            transaccionPuntosRepository.refresh(usuarioId)
        }

        viewModelScope.launch {
            retoRepository.getAll().collect { retos ->
                _state.update { it.copy(retos = retos) }
            }
        }

        viewModelScope.launch {
            transaccionPuntosRepository.getByUsuario(usuarioId).collect { historial ->
                _state.update { it.copy(historial = historial) }
            }
        }

        viewModelScope.launch {
            val saldo = transaccionPuntosRepository.getSaldo(usuarioId)
            _state.update { it.copy(saldoPuntos = saldo) }
        }
    }
}
