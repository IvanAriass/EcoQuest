package com.ecoquest.app.ui.features.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    // La navegación entre destinos la gestiona navController directamente en HomeScreen.
    // Este ViewModel queda listo para cargar datos del usuario al iniciar (p.ej. eventos recientes).
    // TODO: cargar datos iniciales desde repositorio
}