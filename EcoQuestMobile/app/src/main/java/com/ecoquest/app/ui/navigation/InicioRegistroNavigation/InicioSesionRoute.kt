package com.ecoquest.app.ui.navigation.InicioRegistroNavigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ecoquest.app.ui.features.InicioRegistro.InicioSesionScreen
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroEvent
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroViewModel
import kotlinx.serialization.Serializable

@Serializable
object InicioSesionRoute

fun NavGraphBuilder.inicioSesionDestination(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {

    composable<InicioSesionRoute> {
        val vm: InicioRegistroViewModel = hiltViewModel()
        val uiState by vm.uiState
        InicioSesionScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is InicioRegistroEvent.OnGoToRegistro ->
                        navController.navigate(RegistroRoute)
                    is InicioRegistroEvent.OnLoginClicked -> {
                        vm.onEvent(event)
                        if (uiState.navigateToHome) {
                            vm.onEvent(InicioRegistroEvent.OnNavigateToHomeConsumed)
                            onLoginSuccess()
                        }
                    }
                    else -> vm.onEvent(event)
                }
            }
        )
    }
}