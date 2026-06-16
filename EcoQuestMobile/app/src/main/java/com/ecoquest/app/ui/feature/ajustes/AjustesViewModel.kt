package com.ecoquest.app.ui.feature.ajustes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.UsuarioRepository
import com.ecoquest.app.managers.PreferencesManager
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AjustesViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val usuarioRepository: UsuarioRepository,
    private val tokenManager: TokenManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(AjustesUiState())
    val state: StateFlow<AjustesUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    init {
        cargarAjustes()
    }

    fun onEvent(event: AjustesEvent) {
        when (event) {
            is AjustesEvent.OnToggleTema -> {
                val nuevo = !_state.value.temaOscuro
                preferencesManager.setDarkTheme(nuevo)
            }
            is AjustesEvent.OnToggleNotificaciones -> {
                val nuevo = !_state.value.notificaciones
                preferencesManager.setNotificaciones(nuevo)
            }
            is AjustesEvent.OnSeleccionarIdioma -> {
                preferencesManager.setLanguage(event.idioma)
            }
            is AjustesEvent.OnSeleccionarTemaPersonalizado -> {
                preferencesManager.setThemeName(event.tema)
            }
            is AjustesEvent.OnCerrarSesion -> { }
        }
    }

    private fun cargarAjustes() {
        _state.update { it.copy(appVersion = obtenerVersion()) }

        viewModelScope.launch {
            combine(
                preferencesManager.isDarkTheme,
                preferencesManager.themeName,
                preferencesManager.notificaciones,
                preferencesManager.language,
                usuarioRepository.getById(usuarioId)
            ) { tema, temaPers, notif, lang, usuario ->
                AjustesUiState(
                    temaOscuro = tema,
                    temaPersonalizado = temaPers,
                    notificaciones = notif,
                    idioma = lang,
                    usuario = usuario,
                    appVersion = _state.value.appVersion
                )
            }.collect { state ->
                _state.value = state
            }
        }
    }

    private fun obtenerVersion(): String {
        return try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "1.0"
        } catch (_: Exception) { "1.0" }
    }
}
