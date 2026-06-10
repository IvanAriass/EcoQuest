package com.ecoquest.app.ui.navigation

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
        startDestination = Routes.Login
    ) {
        authNavGraph(navController = navController, onLoginSuccess = onLoginSuccess)
    }
}
