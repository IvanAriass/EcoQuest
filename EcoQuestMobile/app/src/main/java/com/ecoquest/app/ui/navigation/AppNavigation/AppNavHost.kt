package com.ecoquest.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ecoquest.app.ui.navigation.AppNavigation.route.EventoRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.HomeRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.ajustesDestination
import com.ecoquest.app.ui.navigation.AppNavigation.route.comunidadDentroDestination
import com.ecoquest.app.ui.navigation.AppNavigation.route.comunidadesDestination
import com.ecoquest.app.ui.navigation.AppNavigation.route.eventoDestination
import com.ecoquest.app.ui.navigation.AppNavigation.route.homeDestination
import com.ecoquest.app.ui.navigation.AppNavigation.route.perfilDestination
import com.ecoquest.app.ui.navigation.AppNavigation.route.tiendaDestination

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = EventoRoute()
    ) {
        homeDestination(navController)
        comunidadesDestination(navController)
        comunidadDentroDestination(navController)
        tiendaDestination(navController)
        perfilDestination(navController)
        ajustesDestination(navController, onLogout)
        eventoDestination(navController)
    }
}

