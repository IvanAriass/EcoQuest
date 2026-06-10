package com.ecoquest.app.ui.feature.tienda

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TiendaViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(TiendaUiState())
    val state: StateFlow<TiendaUiState> = _state.asStateFlow()

    fun onEvent(event: TiendaEvent) {
        when (event) { else -> {} }
    }
}
