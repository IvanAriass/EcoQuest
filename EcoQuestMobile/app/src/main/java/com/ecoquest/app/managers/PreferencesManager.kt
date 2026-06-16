package com.ecoquest.app.managers

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _isDarkTheme = MutableStateFlow(prefs.getBoolean(KEY_DARK_THEME, false))
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _language = MutableStateFlow(prefs.getString(KEY_LANGUAGE, "es") ?: "es")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _notificaciones = MutableStateFlow(prefs.getBoolean(KEY_NOTIFICACIONES, true))
    val notificaciones: StateFlow<Boolean> = _notificaciones.asStateFlow()

    private val _themeName = MutableStateFlow(prefs.getString(KEY_THEME, "default") ?: "default")
    val themeName: StateFlow<String> = _themeName.asStateFlow()

    fun setDarkTheme(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_THEME, enabled).apply()
        _isDarkTheme.value = enabled
    }

    fun setThemeName(name: String) {
        prefs.edit().putString(KEY_THEME, name).apply()
        _themeName.value = name
    }

    fun setLanguage(lang: String) {
        prefs.edit().putString(KEY_LANGUAGE, lang).apply()
        _language.value = lang
    }

    fun setNotificaciones(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_NOTIFICACIONES, enabled).apply()
        _notificaciones.value = enabled
    }

    companion object {
        private const val PREFS_NAME = "ecoquest_prefs"
        private const val KEY_DARK_THEME = "dark_theme"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_NOTIFICACIONES = "notificaciones"
        private const val KEY_THEME = "theme_name"
    }
}
