package com.ecoquest.app.ui.navigation.AppNavigation.route

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pmdm.proyectobase.ui.features.comunidades.ComunidadesScreen
import com.ecoquest.app.ui.features.comunidades.ComunidadesEvent
import com.ecoquest.app.ui.features.comunidades.ComunidadesViewModel
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavGraphBuilder.homeDestination(
    navController: NavHostController
) {
    composable<HomeRoute> {
        val vm: ComunidadesViewModel = hiltViewModel()
        ComunidadesScreen(
            uiState = vm.state,
            onEvent = { event ->
                when (event) {
                    is ComunidadesEvent.OnComunidadClick ->
                        navController.navigate(ComunidadDentroRoute(event.comunidadId))
                    else -> vm.onEvent(event)
                }
            }
        )
    }
}