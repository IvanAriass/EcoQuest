package com.ecoquest.app.ui.navigation.AppNavigation.route

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ecoquest.app.ui.features.ajustes.AjustesScreen
import com.ecoquest.app.ui.features.ajustes.AjustesViewModel
import kotlinx.serialization.Serializable

@Serializable
object AjustesRoute

fun NavGraphBuilder.ajustesDestination(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    composable<AjustesRoute> {
        val activity = LocalContext.current as ComponentActivity
        val vm: AjustesViewModel = hiltViewModel(activity)
        AjustesScreen(
            uiState = vm.state,
            onEvent = { event -> vm.onEvent(event) },
            onLogout = onLogout
        )
    }
}