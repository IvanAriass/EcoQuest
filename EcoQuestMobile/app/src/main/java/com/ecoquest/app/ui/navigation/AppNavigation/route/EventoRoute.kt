package com.ecoquest.app.ui.navigation.AppNavigation.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ecoquest.app.ui.features.eventoDentro.EventoDentroScreen
import com.ecoquest.app.ui.features.eventoDentro.EventoDentroViewModel
import com.ecoquest.app.ui.features.eventos.EventosScreen
import com.ecoquest.app.ui.features.eventos.EventosViewModel
import kotlinx.serialization.Serializable

@Serializable
data class EventoRoute(val eventoId: Long = 0)

fun NavGraphBuilder.eventoDestination(
    navController: NavHostController
) {
    composable<EventoRoute> { backStackEntry ->
        val route = backStackEntry.toRoute<EventoRoute>()

        if (route.eventoId == 0L) {
            EventosDestination(navController)
        } else {
            EventoDentroDestination(navController, route.eventoId)
        }
    }
}

@Composable
private fun EventosDestination(navController: NavHostController) {
    val vm: EventosViewModel = hiltViewModel()
    EventosScreen(
        uiState = vm.state,
        onEvent = { event -> vm.onEvent(event) },
        onNavigateToEvento = { eventoId ->
            navController.navigate(EventoRoute(eventoId))
        }
    )
}

@Composable
private fun EventoDentroDestination(navController: NavHostController, eventoId: Long) {
    val vm: EventoDentroViewModel = hiltViewModel()
    LaunchedEffect(eventoId) {
        vm.cargarEvento(eventoId)
    }
    EventoDentroScreen(
        eventoId = eventoId,
        uiState = vm.state,
        onEvent = { event -> vm.onEvent(event) }
    )
}
