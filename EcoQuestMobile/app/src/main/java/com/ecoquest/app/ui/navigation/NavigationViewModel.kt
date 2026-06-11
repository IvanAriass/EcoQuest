package com.ecoquest.app.ui.navigation

import androidx.lifecycle.ViewModel
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(tokenManager.getToken() != null)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    fun onLoginSuccess() {
        _isAuthenticated.value = true
    }

    fun onLogout() {
        tokenManager.clear()
        _isAuthenticated.value = false
    }
}
