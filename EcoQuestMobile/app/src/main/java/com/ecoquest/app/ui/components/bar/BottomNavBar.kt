package com.ecoquest.app.ui.components.bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.theme.GreenBar

enum class BottomNavTab(val label: String) {
    EVENTOS("Home"),
    COMUNIDADES("Comunidad"),
    TIENDA("Tienda"),
    PERFIL("Perfil")
}

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = GreenBar,
        contentColor = Color.White,
        tonalElevation = 0.dp,
        modifier = modifier
    ) {
        NavigationBarItem(
            selected = currentRoute?.contains("Eventos") == true || currentRoute?.contains("Evento") == true,
            onClick = { onTabSelected(BottomNavTab.EVENTOS) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = navItemColors()
        )

        NavigationBarItem(
            selected = currentRoute == "com.ecoquest.app.ui.navigation.Routes.Comunidades",
            onClick = { onTabSelected(BottomNavTab.COMUNIDADES) },
            icon = { Icon(Icons.Filled.List, contentDescription = "Comunidad") },
            label = { Text("Comunidad") },
            colors = navItemColors()
        )

        NavigationBarItem(
            selected = currentRoute == "com.ecoquest.app.ui.navigation.Routes.Tienda",
            onClick = { onTabSelected(BottomNavTab.TIENDA) },
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Tienda") },
            label = { Text("Tienda") },
            colors = navItemColors()
        )

        NavigationBarItem(
            selected = currentRoute == "com.ecoquest.app.ui.navigation.Routes.Perfil",
            onClick = { onTabSelected(BottomNavTab.PERFIL) },
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            colors = navItemColors()
        )
    }
}

@Composable
private fun navItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = GreenBar,
    selectedTextColor = Color.White,
    indicatorColor = Color.White,
    unselectedIconColor = Color.White.copy(alpha = 0.7f),
    unselectedTextColor = Color.White.copy(alpha = 0.7f)
)
