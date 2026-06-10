package com.ecoquest.app.ui.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecoquest.app.R
import com.ecoquest.app.ui.components.bar.BottomNavBar
import com.ecoquest.app.ui.components.bar.BottomNavTab
import com.ecoquest.app.ui.navigation.AppNavHost
import com.ecoquest.app.ui.navigation.Routes
import com.ecoquest.app.ui.theme.Green99

@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Green99.copy(alpha = 0.10f))
        )

        Scaffold(
            bottomBar = {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onTabSelected = { tab -> navigateToTab(navController, tab) }
                )
            },
            containerColor = Color.Transparent
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
