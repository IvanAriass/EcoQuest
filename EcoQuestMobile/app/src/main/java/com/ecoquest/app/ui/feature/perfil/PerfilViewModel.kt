package com.ecoquest.app.ui.feature.perfil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.EventoRepository
import com.ecoquest.app.domain.repository.RetoRepository
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import com.ecoquest.app.domain.repository.UsuarioCosmeticoRepository
import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import com.ecoquest.app.domain.repository.UsuarioRepository
import com.ecoquest.app.managers.PreferencesManager
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfilViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
    private val comunidadRepository: ComunidadRepository,
    private val eventoRepository: EventoRepository,
    private val usuarioComunidadRepository: UsuarioComunidadRepository,
    private val usuarioEventoRepository: UsuarioEventoRepository,
    private val transaccionPuntosRepository: TransaccionPuntosRepository,
    private val retoRepository: RetoRepository,
    private val usuarioCosmeticoRepository: UsuarioCosmeticoRepository,
    private val preferencesManager: PreferencesManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _state = MutableStateFlow(PerfilUiState())
    val state: StateFlow<PerfilUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    init {
        cargarPerfil()
    }

    fun onEvent(event: PerfilEvent) {
        when (event) {
            is PerfilEvent.OnToggleComunidades -> {
                _state.update { it.copy(showComunidades = !it.showComunidades, showEventos = false, showRetos = false, showPuntos = false) }
            }
            is PerfilEvent.OnToggleEventos -> {
                _state.update { it.copy(showEventos = !it.showEventos, showComunidades = false, showRetos = false, showPuntos = false) }
            }
            is PerfilEvent.OnToggleRetos -> {
                _state.update { it.copy(showRetos = !it.showRetos, showComunidades = false, showEventos = false, showPuntos = false) }
            }
            is PerfilEvent.OnTogglePuntos -> {
                _state.update { it.copy(showPuntos = !it.showPuntos, showComunidades = false, showEventos = false, showRetos = false) }
            }
            is PerfilEvent.OnFotoSeleccionada -> {
                _state.update { it.copy(usuario = it.usuario.copy(imagen = event.uri)) }
                viewModelScope.launch {
                    usuarioRepository.upsert(_state.value.usuario.copy(imagen = event.uri))
                }
            }
            is PerfilEvent.OnAplicarCosmetico -> {
                viewModelScope.launch {
                    usuarioCosmeticoRepository.aplicarCosmetico(usuarioId, event.productoId)
                    productoIdToThemeName(event.productoId)?.let { preferencesManager.setThemeName(it) }
                }
            }
            is PerfilEvent.OnDesaplicarCosmetico -> {
                viewModelScope.launch {
                    usuarioCosmeticoRepository.desaplicarCosmetico(usuarioId, event.productoId)
                    val current = _state.value.cosmeticos
                    val stillApplied = current.firstOrNull {
                        it.productoTipo == "TEMA" && it.aplicado && it.productoId != event.productoId
                    }
                    preferencesManager.setThemeName(
                        stillApplied?.let { productoIdToThemeName(it.productoId) } ?: "default"
                    )
                }
            }
            is PerfilEvent.OnToggleCosmeticos -> {
                _state.update { it.copy(showCosmeticos = !it.showCosmeticos, showComunidades = false, showEventos = false, showRetos = false, showPuntos = false) }
            }
            is PerfilEvent.OnGoToAjustes -> { }
            is PerfilEvent.OnGoToRetos -> { }
            is PerfilEvent.OnGoToTienda -> { }
            is PerfilEvent.OnGoToEventos -> { }
            is PerfilEvent.OnGoToComunidades -> { }
            is PerfilEvent.OnLogout -> { }
        }
    }

    private fun productoIdToThemeName(productoId: Long): String? = when (productoId) {
        3L -> "bosque"
        4L -> "atardecer"
        7L -> "oceano"
        8L -> "noche"
        9L -> "flora"
        else -> null
    }

    private fun cargarPerfil() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            eventoRepository.refreshEventos()
        }
        viewModelScope.launch {
            comunidadRepository.refreshComunidades()
        }

        viewModelScope.launch {
            usuarioRepository.getById(usuarioId).collect { usuario ->
                _state.update { it.copy(usuario = usuario ?: Usuario(), isLoading = false) }
            }
        }
        viewModelScope.launch {
            usuarioComunidadRepository.getComunidadesByUsuario(usuarioId).collect { comunidades ->
                _state.update { it.copy(comunidades = comunidades) }
            }
        }
        viewModelScope.launch {
            usuarioEventoRepository.getEventosByUsuario(usuarioId).collect { eventos ->
                _state.update { it.copy(eventos = eventos) }
            }
        }
        viewModelScope.launch {
            retoRepository.getAll().collect { retos ->
                _state.update { it.copy(retos = retos) }
            }
        }
        viewModelScope.launch {
            val saldo = transaccionPuntosRepository.getSaldo(usuarioId)
            _state.update { it.copy(saldoPuntos = saldo) }
        }
        viewModelScope.launch {
            transaccionPuntosRepository.getByUsuario(usuarioId).collect { transacciones ->
                _state.update { it.copy(transacciones = transacciones) }
            }
        }
        viewModelScope.launch {
            usuarioCosmeticoRepository.refresh(usuarioId)
        }
        viewModelScope.launch {
            usuarioCosmeticoRepository.getByUsuario(usuarioId).collect { cosmeticos ->
                _state.update { it.copy(cosmeticos = cosmeticos) }
            }
        }
    }
}
