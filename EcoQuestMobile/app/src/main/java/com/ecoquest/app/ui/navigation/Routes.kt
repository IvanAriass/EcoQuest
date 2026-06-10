package com.ecoquest.app.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import com.ecoquest.app.ui.feature.pantalla_home.PantallaHomeEvent
import com.ecoquest.app.ui.feature.pantalla_home.PantallaHomeScreen
import com.ecoquest.app.ui.feature.pantalla_home.PantallaHomeViewModel
import com.ecoquest.app.ui.feature.comunidades_dentro.ComunidadesDentroScreen
import com.ecoquest.app.ui.feature.comunidades_dentro.ComunidadesDentroViewModel
import com.ecoquest.app.ui.feature.evento_dentro.EventoDentroScreen
import com.ecoquest.app.ui.feature.evento_dentro.EventoDentroViewModel
import com.ecoquest.app.ui.feature.eventos.EventosScreen
import com.ecoquest.app.ui.feature.eventos.EventosViewModel
import com.ecoquest.app.ui.feature.perfil.PerfilEvent
import com.ecoquest.app.ui.feature.perfil.PerfilScreen
import com.ecoquest.app.ui.feature.perfil.PerfilViewModel
import com.ecoquest.app.ui.feature.tienda.TiendaScreen
import com.ecoquest.app.ui.feature.tienda.TiendaViewModel
import kotlinx.serialization.Serializable

private const val ANIM_DURATION = 350

object Routes {

    @Serializable
    data object Login

    @Serializable
    data object Register

    @Serializable
    data object Home

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

private fun slideInFromRight() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = tween(ANIM_DURATION)
) + fadeIn(animationSpec = tween(ANIM_DURATION))

private fun slideOutToLeft() = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(ANIM_DURATION)
) + fadeOut(animationSpec = tween(ANIM_DURATION))

private fun slideInFromLeft() = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = tween(ANIM_DURATION)
) + fadeIn(animationSpec = tween(ANIM_DURATION))

private fun slideOutToRight() = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(ANIM_DURATION)
) + fadeOut(animationSpec = tween(ANIM_DURATION))

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    composable<Routes.Home>(
        enterTransition = { fadeIn(animationSpec = tween(ANIM_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(ANIM_DURATION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(ANIM_DURATION)) },
        popExitTransition = { fadeOut(animationSpec = tween(ANIM_DURATION)) }
    ) {
        val vm: PantallaHomeViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        PantallaHomeScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is PantallaHomeEvent.OnNavigateToEventos -> navController.navigate(Routes.Eventos) { launchSingleTop = true }
                    is PantallaHomeEvent.OnNavigateToComunidades -> navController.navigate(Routes.Comunidades) { launchSingleTop = true }
                    is PantallaHomeEvent.OnNavigateToTienda -> navController.navigate(Routes.Tienda) { launchSingleTop = true }
                    is PantallaHomeEvent.OnNavigateToEvento -> navController.navigate(Routes.Evento(event.eventoId))
                    is PantallaHomeEvent.OnNavigateToComunidad -> navController.navigate(Routes.ComunidadDentro(event.comunidadId.toInt()))
                }
            }
        )
    }

    composable<Routes.Eventos>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
        val vm: EventosViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        EventosScreen(
            uiState = uiState,
            onEvent = { event -> vm.onEvent(event) },
            onNavigateToEvento = { eventoId -> navController.navigate(Routes.Evento(eventoId)) }
        )
    }

    composable<Routes.Evento>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.Evento>()
        val vm: EventoDentroViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarEvento(route.eventoId) }
        EventoDentroScreen(eventoId = route.eventoId, uiState = uiState, onEvent = { event -> vm.onEvent(event) })
    }

    composable<Routes.Comunidades>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
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

    composable<Routes.ComunidadDentro>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) { backStackEntry ->
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

    composable<Routes.Tienda>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
        val vm: TiendaViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        TiendaScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) })
    }

    composable<Routes.Perfil>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
        val vm: PerfilViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        PerfilScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is PerfilEvent.OnGoToAjustes -> navController.navigate(Routes.Ajustes)
                    else -> vm.onEvent(event)
                }
            }
        )
    }

    composable<Routes.Ajustes>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
        val vm: AjustesViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        AjustesScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) }, onLogout = onLogout)
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    composable<Routes.Login>(
        enterTransition = { fadeIn(animationSpec = tween(ANIM_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(ANIM_DURATION)) },
        popEnterTransition = { fadeIn(animationSpec = tween(ANIM_DURATION)) },
        popExitTransition = { fadeOut(animationSpec = tween(ANIM_DURATION)) }
    ) {
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

    composable<Routes.Register>(
        enterTransition = { slideInFromRight() },
        exitTransition = { slideOutToLeft() },
        popEnterTransition = { slideInFromLeft() },
        popExitTransition = { slideOutToRight() }
    ) {
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
