package com.ecoquest.app.managers

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _token = MutableStateFlow(prefs.getString(KEY_TOKEN, null))
    val token: StateFlow<String?> = _token.asStateFlow()

    private val _usuarioId = MutableStateFlow(prefs.getLong(KEY_USUARIO_ID, -1L))
    val usuarioId: StateFlow<Long> = _usuarioId.asStateFlow()

    fun saveToken(token: String, usuarioId: Long) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putLong(KEY_USUARIO_ID, usuarioId)
            .apply()
        _token.value = token
        _usuarioId.value = usuarioId
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getUsuarioId(): Long = prefs.getLong(KEY_USUARIO_ID, -1L)

    fun clear() {
        prefs.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_USUARIO_ID)
            .apply()
        _token.value = null
        _usuarioId.value = -1L
    }

    companion object {
        private const val PREFS_NAME = "ecoquest_secure_prefs"
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USUARIO_ID = "usuario_id"
    }
}
