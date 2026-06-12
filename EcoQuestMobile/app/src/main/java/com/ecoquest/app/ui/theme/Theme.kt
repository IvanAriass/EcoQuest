package com.ecoquest.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Green30,
    onPrimary = Color.White,
    primaryContainer = Green95,
    onPrimaryContainer = Green10,
    secondary = Green50,
    onSecondary = Color.White,
    secondaryContainer = Green95,
    onSecondaryContainer = Green10,
    tertiary = Amber30,
    onTertiary = Color.White,
    tertiaryContainer = Amber90,
    onTertiaryContainer = Color(0xFF261900),
    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,
    background = SurfaceLight,
    onBackground = Neutral10,
    surface = Color.White,
    onSurface = Neutral10,
    surfaceVariant = Green95,
    onSurfaceVariant = Color(0xFF3D5E4A),
    outline = Color(0xFF7B9A7B),
    outlineVariant = Green90,
    inverseSurface = Neutral20,
    inverseOnSurface = Neutral95,
    inversePrimary = Green80,
    surfaceTint = Green30
)

private val DarkColorScheme = darkColorScheme(
    primary = Green80,
    onPrimary = DarkGreen20,
    primaryContainer = DarkGreen30,
    onPrimaryContainer = DarkGreen90,
    secondary = Green70,
    onSecondary = DarkGreen10,
    secondaryContainer = DarkGreen20,
    onSecondaryContainer = DarkGreen90,
    tertiary = Amber80,
    onTertiary = Color(0xFF261900),
    tertiaryContainer = Color(0xFF3E2A00),
    onTertiaryContainer = Amber90,
    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,
    background = SurfaceDark,
    onBackground = Green90,
    surface = Color(0xFF1A2E1A),
    onSurface = Green90,
    surfaceVariant = DarkGreen20,
    onSurfaceVariant = Green80,
    outline = Color(0xFF6B8F6B),
    outlineVariant = DarkGreen30,
    inverseSurface = Green90,
    inverseOnSurface = DarkGreen10,
    inversePrimary = Green40,
    surfaceTint = Green80
)

@Composable
fun EcoQuestMobileTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
