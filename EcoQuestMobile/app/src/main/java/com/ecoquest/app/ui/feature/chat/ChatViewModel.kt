package com.ecoquest.app.ui.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.domain.model.Mensaje
import com.ecoquest.app.domain.model.RolInfo
import com.ecoquest.app.domain.repository.MensajeRepository
import com.ecoquest.app.domain.repository.RolRepository
import com.ecoquest.app.domain.usecase.comunidades.GetMensajesUseCase
import com.ecoquest.app.domain.usecase.comunidades.SendMensajeUseCase
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val usuarioId: Long = 0,
    val mensajes: List<Mensaje> = emptyList(),
    val textoInput: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val miRol: RolInfo = RolInfo(),
    val puedeEscribir: Boolean = true,
    val puedeModerar: Boolean = false
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMensajesUseCase: GetMensajesUseCase,
    private val sendMensajeUseCase: SendMensajeUseCase,
    private val mensajeRepository: MensajeRepository,
    private val usuarioComunidadDao: UsuarioComunidadDao,
    private val tokenManager: TokenManager,
    private val rolRepository: RolRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    private var comunidadId: Long = 0

    fun cargarChat(id: Long) {
        comunidadId = id
        _state.update { it.copy(usuarioId = usuarioId) }
        viewModelScope.launch {
            mensajeRepository.refreshMensajes(id)
        }
        viewModelScope.launch {
            getMensajesUseCase(id).collect { mensajes ->
                _state.update { it.copy(mensajes = mensajes) }
            }
        }
        viewModelScope.launch {
            rolRepository.refreshRoles()
            usuarioComunidadDao.getByComunidad(id).collect { relaciones ->
                val miRelacion = relaciones.find { it.usuarioId == usuarioId }
                val rolId = miRelacion?.rol?.ifBlank { "SEMILLA" } ?: "SEMILLA"
                val rolInfo = rolRepository.getRolById(rolId)
                _state.update { it.copy(
                    miRol = rolInfo,
                    puedeEscribir = rolInfo.nivel >= 1,
                    puedeModerar = rolInfo.nivel >= 3
                )}
            }
        }
    }

    fun onTextoCambiado(texto: String) {
        _state.update { it.copy(textoInput = texto) }
    }

    fun onEnviarMensaje() {
        val texto = _state.value.textoInput.trim()
        if (texto.isBlank()) return

        viewModelScope.launch {
            sendMensajeUseCase(comunidadId, usuarioId, texto)
            _state.update { it.copy(textoInput = "") }
        }
    }
}
