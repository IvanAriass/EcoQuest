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
import com.ecoquest.app.ui.feature.acceso.AccesoEvent
import com.ecoquest.app.ui.feature.acceso.AccesoViewModel
import com.ecoquest.app.ui.feature.acceso.InicioSesionScreen
import com.ecoquest.app.ui.feature.acceso.RegistroScreen
import com.ecoquest.app.ui.feature.comunidades.ComunidadesEvent
import com.ecoquest.app.ui.feature.comunidades.ComunidadesScreen
import com.ecoquest.app.ui.feature.comunidades.ComunidadesViewModel
import com.ecoquest.app.ui.feature.home.contenido.HomeContentEvent
import com.ecoquest.app.ui.feature.home.contenido.HomeContentScreen
import com.ecoquest.app.ui.feature.home.contenido.HomeContentViewModel
import com.ecoquest.app.ui.feature.comunidades.detalle.ComunidadDetalleScreen
import com.ecoquest.app.ui.feature.comunidades.detalle.ComunidadDetalleViewModel
import com.ecoquest.app.ui.feature.eventos.detalle.EventoDetalleScreen
import com.ecoquest.app.ui.feature.eventos.detalle.EventoDetalleViewModel
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
        val vm: HomeContentViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        HomeContentScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is HomeContentEvent.OnNavigateToEventos -> navController.navigate(Routes.Eventos) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToComunidades -> navController.navigate(Routes.Comunidades) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToTienda -> navController.navigate(Routes.Tienda) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToEvento -> navController.navigate(Routes.Evento(event.eventoId))
                    is HomeContentEvent.OnNavigateToComunidad -> navController.navigate(Routes.ComunidadDentro(event.comunidadId.toInt()))
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
        val vm: EventoDetalleViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarEvento(route.eventoId) }
        EventoDetalleScreen(eventoId = route.eventoId, uiState = uiState, onEvent = { event -> vm.onEvent(event) })
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
        val vm: ComunidadDetalleViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarComunidad(route.comunidadId.toLong()) }
        ComunidadDetalleScreen(
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
        val vm: AccesoViewModel = hiltViewModel()
        val uiState by vm.uiState.collectAsState()

        LaunchedEffect(uiState.navigateToHome) {
            if (uiState.navigateToHome) {
                vm.onEvent(AccesoEvent.OnNavigateToHomeConsumed)
                onLoginSuccess()
            }
        }

        InicioSesionScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is AccesoEvent.OnGoToRegistro -> navController.navigate(Routes.Register)
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
        val vm: AccesoViewModel = hiltViewModel()
        val uiState by vm.uiState.collectAsState()

        LaunchedEffect(uiState.navigateToLogin) {
            if (uiState.navigateToLogin) {
                vm.onEvent(AccesoEvent.OnNavigateToLoginConsumed)
                navController.navigate(Routes.Login) { popUpTo<Routes.Register> { inclusive = true } }
            }
        }

        LaunchedEffect(uiState.navigateToHome) {
            if (uiState.navigateToHome) {
                vm.onEvent(AccesoEvent.OnNavigateToHomeConsumed)
                onLoginSuccess()
            }
        }

        RegistroScreen(
            uiState = uiState,
            onEvent = { event -> vm.onEvent(event) }
        )
    }
}
