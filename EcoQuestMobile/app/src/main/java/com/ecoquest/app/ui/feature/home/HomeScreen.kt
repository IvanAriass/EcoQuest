package com.ecoquest.app.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecoquest.app.ui.components.navegacion.BottomNavBar
import com.ecoquest.app.ui.components.navegacion.BottomNavTab
import com.ecoquest.app.ui.navigation.AppNavHost
import com.ecoquest.app.ui.navigation.Routes

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onTabSelected = { tab -> navigateToTab(navController, tab) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppNavHost(navController = navController, onLogout = onLogout)
        }
    }
}

private fun navigateToTab(navController: androidx.navigation.NavHostController, tab: BottomNavTab) {
    when (tab) {
        BottomNavTab.HOME -> {
            navController.navigate(Routes.Home) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
        BottomNavTab.EVENTOS -> {
            navController.navigate(Routes.Eventos) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
        BottomNavTab.COMUNIDADES -> {
            navController.navigate(Routes.Comunidades) {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }
        }
        BottomNavTab.TIENDA -> {
            navController.navigate(Routes.Tienda) {
                launchSingleTop = true
            }
        }
        BottomNavTab.PERFIL -> {
            navController.navigate(Routes.Perfil) {
                launchSingleTop = true
            }
        }
    }
}
