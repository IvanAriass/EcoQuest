package com.ecoquest.app.ui.feature.ajustes

import androidx.lifecycle.ViewModel
import com.ecoquest.app.managers.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AjustesViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow(AjustesUiState())
    val state: StateFlow<AjustesUiState> = _state.asStateFlow()

    fun onEvent(event: AjustesEvent) {
        when (event) {
            is AjustesEvent.OnToggleTema -> {
                val nuevo = !_state.value.temaOscuro
                preferencesManager.setDarkTheme(nuevo)
                _state.update { it.copy(temaOscuro = nuevo) }
            }
            is AjustesEvent.OnCerrarSesion -> { }
        }
    }
}
