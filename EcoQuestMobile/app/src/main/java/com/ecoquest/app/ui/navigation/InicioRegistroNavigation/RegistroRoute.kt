package com.ecoquest.app.ui.navigation.InicioRegistroNavigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.ecoquest.app.ui.features.InicioRegistro.RegistroScreen
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroEvent
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroViewModel
import kotlinx.serialization.Serializable

@Serializable
object RegistroRoute

fun NavGraphBuilder.registroDestination(
    navController: NavHostController
) {
    composable<RegistroRoute> {
        val vm: InicioRegistroViewModel = hiltViewModel()
        val uiState by vm.uiState

        RegistroScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is InicioRegistroEvent.OnRegisterClicked -> {
                        vm.onEvent(event)
                        // Tras registrarse volvemos a InicioSesion limpiando el backstack
                        navController.navigate(InicioSesionRoute) {
                            popUpTo<RegistroRoute> { inclusive = true }
                        }
                    }
                    else -> vm.onEvent(event)
                }
            }
        )
    }
}