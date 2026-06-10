package com.ecoquest.app.ui.features.ajustes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AjustesViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(AjustesUiState())
        private set

    fun onEvent(event: AjustesEvent) {
        when (event) {

            is AjustesEvent.OnToggleNotificaciones ->
                state = state.copy(notificacionesActivadas = !state.notificacionesActivadas)

            is AjustesEvent.OnToggleTema ->
                state = state.copy(temaOscuro = !state.temaOscuro)

            is AjustesEvent.OnIdiomaChanged ->
                state = state.copy(idiomaSeleccionado = event.idioma)

            is AjustesEvent.OnCerrarSesionClicked -> {
                // La navegación la gestiona el Route con onLogout
            }

            is AjustesEvent.OnEliminarCuentaClicked ->
                state = state.copy(showEliminarCuentaDialog = true)

            is AjustesEvent.OnDismissEliminarCuenta ->
                state = state.copy(showEliminarCuentaDialog = false)

            is AjustesEvent.OnConfirmarEliminarCuenta -> {
                // TODO: llamada al repositorio para eliminar cuenta
                state = state.copy(showEliminarCuentaDialog = false)
            }

            is AjustesEvent.OnCambiarPasswordClicked ->
                state = state.copy(showCambiarPasswordDialog = true)

            is AjustesEvent.OnDismissCambiarPassword ->
                state = state.copy(showCambiarPasswordDialog = false)

            is AjustesEvent.OnConfirmarCambiarPassword -> {
                // TODO: llamada al repositorio para cambiar password
                state = state.copy(showCambiarPasswordDialog = false)
            }
        }
    }
}