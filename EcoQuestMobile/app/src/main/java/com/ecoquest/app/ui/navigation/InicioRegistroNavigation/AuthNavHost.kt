package com.ecoquest.app.ui.navigation.InicioRegistroNavigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


@Composable
fun AuthNavHost(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = InicioSesionRoute
    ) {
        inicioSesionDestination(
            navController = navController,
            onLoginSuccess = onLoginSuccess
        )
        registroDestination(
            navController = navController
        )
    }
}