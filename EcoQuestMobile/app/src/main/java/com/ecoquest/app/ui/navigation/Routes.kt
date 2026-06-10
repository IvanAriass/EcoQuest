package com.ecoquest.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ecoquest.app.ui.feature.ajustes.AjustesScreen
import com.ecoquest.app.ui.feature.ajustes.AjustesViewModel
import com.ecoquest.app.ui.feature.auth.AuthEvent
import com.ecoquest.app.ui.feature.auth.AuthViewModel
import com.ecoquest.app.ui.feature.auth.LoginScreen
import com.ecoquest.app.ui.feature.auth.RegisterScreen
import com.ecoquest.app.ui.feature.comunidades.ComunidadesEvent
import com.ecoquest.app.ui.feature.comunidades.ComunidadesScreen
import com.ecoquest.app.ui.feature.comunidades.ComunidadesViewModel
import com.ecoquest.app.ui.feature.comunidades_dentro.ComunidadesDentroScreen
import com.ecoquest.app.ui.feature.comunidades_dentro.ComunidadesDentroViewModel
import com.ecoquest.app.ui.feature.evento_dentro.EventoDentroScreen
import com.ecoquest.app.ui.feature.evento_dentro.EventoDentroViewModel
import com.ecoquest.app.ui.feature.eventos.EventosScreen
import com.ecoquest.app.ui.feature.eventos.EventosViewModel
import com.ecoquest.app.ui.feature.perfil.PerfilScreen
import com.ecoquest.app.ui.feature.perfil.PerfilViewModel
import com.ecoquest.app.ui.feature.tienda.TiendaScreen
import com.ecoquest.app.ui.feature.tienda.TiendaViewModel
import kotlinx.serialization.Serializable

object Routes {

    @Serializable
    data object Login

    @Serializable
    data object Register

    @Serializable
    data object Eventos

    @Serializable
    data class Evento(val eventoId: Long)

    @Serializable
    data object Comunidades

    @Serializable
    data class ComunidadDentro(val comunidadId: Int)

    @Serializable
    data object Tienda

    @Serializable
    data object Perfil

    @Serializable
    data object Ajustes
}

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    composable<Routes.Eventos> {
        val vm: EventosViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        EventosScreen(
            uiState = uiState,
            onEvent = { event -> vm.onEvent(event) },
            onNavigateToEvento = { eventoId -> navController.navigate(Routes.Evento(eventoId)) }
        )
    }

    composable<Routes.Evento> { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.Evento>()
        val vm: EventoDentroViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarEvento(route.eventoId) }
        EventoDentroScreen(eventoId = route.eventoId, uiState = uiState, onEvent = { event -> vm.onEvent(event) })
    }

    composable<Routes.Comunidades> {
        val vm: ComunidadesViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        ComunidadesScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is ComunidadesEvent.OnComunidadClick -> navController.navigate(Routes.ComunidadDentro(event.comunidadId))
                    else -> vm.onEvent(event)
                }
            }
        )
    }

    composable<Routes.ComunidadDentro> { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.ComunidadDentro>()
        val vm: ComunidadesDentroViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarComunidad(route.comunidadId.toLong()) }
        ComunidadesDentroScreen(
            comunidadId = route.comunidadId.toLong(),
            uiState = uiState,
            onEvent = { event -> vm.onEvent(event) },
            onNavigateToEvento = { eventoId -> navController.navigate(Routes.Evento(eventoId)) }
        )
    }

    composable<Routes.Tienda> {
        val vm: TiendaViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        TiendaScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) })
    }

    composable<Routes.Perfil> {
        val vm: PerfilViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        PerfilScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) })
    }

    composable<Routes.Ajustes> {
        val vm: AjustesViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        AjustesScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) }, onLogout = onLogout)
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    composable<Routes.Login> {
        val vm: AuthViewModel = hiltViewModel()
        val uiState by vm.uiState.collectAsState()

        LaunchedEffect(uiState.navigateToHome) {
            if (uiState.navigateToHome) {
                vm.onEvent(AuthEvent.OnNavigateToHomeConsumed)
                onLoginSuccess()
            }
        }

        LoginScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is AuthEvent.OnGoToRegistro -> navController.navigate(Routes.Register)
                    else -> vm.onEvent(event)
                }
            }
        )
    }

    composable<Routes.Register> {
        val vm: AuthViewModel = hiltViewModel()
        val uiState by vm.uiState.collectAsState()
        RegisterScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is AuthEvent.OnRegisterClicked -> {
                        vm.onEvent(event)
                        navController.navigate(Routes.Login) { popUpTo<Routes.Register> { inclusive = true } }
                    }
                    else -> vm.onEvent(event)
                }
            }
        )
    }
}
