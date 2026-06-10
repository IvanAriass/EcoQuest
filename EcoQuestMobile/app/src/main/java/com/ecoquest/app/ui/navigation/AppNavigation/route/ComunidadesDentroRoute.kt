package com.ecoquest.app.ui.navigation.AppNavigation.route

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ecoquest.app.ui.features.comunidadesDentro.ComunidadesDentroScreen
import com.ecoquest.app.ui.features.comunidadesDentro.ComunidadesDentroViewModel
import kotlinx.serialization.Serializable

@Serializable
data class ComunidadDentroRoute(val comunidadId: Int)

fun NavGraphBuilder.comunidadDentroDestination(
    navController: NavHostController
) {
    composable<ComunidadDentroRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<ComunidadDentroRoute>()
        val vm: ComunidadesDentroViewModel = hiltViewModel()

        LaunchedEffect(route.comunidadId) {
            vm.cargarComunidad(route.comunidadId.toLong())
        }

        ComunidadesDentroScreen(
            comunidadId = route.comunidadId.toLong(),
            uiState = vm.state,
            onEvent = { event -> vm.onEvent(event) },
            onNavigateToEvento = { eventoId ->
                navController.navigate(EventoRoute(eventoId))
            }
        )
    }
}