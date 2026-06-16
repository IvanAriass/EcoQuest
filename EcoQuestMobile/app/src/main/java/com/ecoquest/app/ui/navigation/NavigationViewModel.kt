package com.ecoquest.app.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.domain.repository.UsuarioCosmeticoRepository
import com.ecoquest.app.managers.PreferencesManager
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val preferencesManager: PreferencesManager,
    private val usuarioCosmeticoRepository: UsuarioCosmeticoRepository
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(tokenManager.getToken() != null)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    fun onLoginSuccess() {
        _isAuthenticated.value = true
        sincronizarTema()
    }

    fun onLogout() {
        tokenManager.clear()
        preferencesManager.setThemeName("default")
        _isAuthenticated.value = false
    }

    private fun sincronizarTema() {
        viewModelScope.launch {
            val usuarioId = tokenManager.getUsuarioId().takeIf { it > 0 } ?: return@launch
            usuarioCosmeticoRepository.refresh(usuarioId)
            val cosmeticos = usuarioCosmeticoRepository.getByUsuario(usuarioId).first()
            val activeTema = cosmeticos.firstOrNull { it.productoTipo == "TEMA" && it.aplicado }
            val nombre = activeTema?.let { productoIdToThemeName(it.productoId) } ?: "default"
            preferencesManager.setThemeName(nombre)
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
}
