package com.ecoquest.app.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecoquest.app.ui.navigation.AppNavHost
import com.ecoquest.app.R
import com.ecoquest.app.ui.navigation.AppNavigation.route.AjustesRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.ComunidadesRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.EventoRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.HomeRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.PerfilRoute
import com.ecoquest.app.ui.navigation.AppNavigation.route.TiendaRoute
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme

// ─── Composable real con ViewModel (usado en producción) ───
@Composable
fun HomeScreen(
    vm: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    HomeScreenContent(
        navController = navController,
        content = { AppNavHost(navController = navController, onLogout = onLogout) }
    )
}

// ─── Composable de contenido sin ViewModel ni NavHost (usado en preview) ───
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    navController: NavHostController,
    content: @Composable () -> Unit = {}
) {
    // Obtén la ruta actual para saber qué ítem está activo
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Scaffold(
            topBar = { /* ... igual que antes ... */ },
            bottomBar = {
                NavigationBar(
                    containerColor = GreenBar,
                    contentColor = Color.White,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = currentRoute?.contains("EventoRoute") == true,
                        onClick = {
                            navController.navigate(EventoRoute()) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Filled.Home, contentDescription = "Home")
                        },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GreenBar,
                            selectedTextColor = Color.White,
                            indicatorColor = Color.White,
                            unselectedIconColor = Color.White.copy(alpha = 0.7f),
                            unselectedTextColor = Color.White.copy(alpha = 0.7f)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute == ComunidadesRoute::class.qualifiedName,
                        onClick = {
                            navController.navigate(ComunidadesRoute) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Filled.List, contentDescription = "Comunidad")
                        },
                        label = { Text("Comunidad") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GreenBar,
                            selectedTextColor = Color.White,
                            indicatorColor = Color.White,
                            unselectedIconColor = Color.White.copy(alpha = 0.7f),
                            unselectedTextColor = Color.White.copy(alpha = 0.7f)
                        )
                    )

                    NavigationBarItem(
                        selected = currentRoute == TiendaRoute::class.qualifiedName,
                        onClick = {
                            navController.navigate(TiendaRoute) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Tienda")
                        },
                        label = { Text("Tienda") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GreenBar,
                            selectedTextColor = Color.White,
                            indicatorColor = Color.White,
                            unselectedIconColor = Color.White.copy(alpha = 0.7f),
                            unselectedTextColor = Color.White.copy(alpha = 0.7f)
                        )
                    )
                }
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        }
    }
}

// ─── Pantalla principal de eventos (startDestination del AppNavHost) ───
@Composable
fun HomeMainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("Home — eventos")
    }
}

// ─── Preview: sin AppNavHost ni hiltViewModel, solo el Scaffold visual ───
@Preview(showBackground = true, name = "HomeScreen")
@Composable
fun HomeScreenPreview() {
    EcoQuestMobileTheme {
        HomeScreenContent(
            navController = rememberNavController(),
            content = {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("Home — preview")
                }
            }
        )
    }
}
