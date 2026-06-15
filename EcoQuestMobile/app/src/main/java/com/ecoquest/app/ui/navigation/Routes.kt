package com.ecoquest.app.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.unit.IntOffset
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
import com.ecoquest.app.ui.feature.juegos.AhorraEnergiaScreen
import com.ecoquest.app.ui.feature.juegos.EcoQuizScreen
import com.ecoquest.app.ui.feature.juegos.JuegosScreen
import com.ecoquest.app.ui.feature.juegos.MemoramaScreen
import com.ecoquest.app.ui.feature.juegos.WordleEcoScreen
import com.ecoquest.app.ui.feature.comunidades.detalle.ComunidadDetalleScreen
import com.ecoquest.app.ui.feature.retos.RetosScreen
import com.ecoquest.app.ui.feature.comunidades.detalle.ComunidadDetalleViewModel
import com.ecoquest.app.ui.feature.eventos.detalle.EventoDetalleEvent
import com.ecoquest.app.ui.feature.eventos.detalle.EventoDetalleScreen
import com.ecoquest.app.ui.feature.eventos.detalle.EventoDetalleViewModel
import com.ecoquest.app.ui.feature.eventos.EventosScreen
import com.ecoquest.app.ui.feature.eventos.EventosViewModel
import com.ecoquest.app.ui.feature.miembros.MiembrosScreen
import com.ecoquest.app.ui.feature.miembros.MiembrosViewModel
import com.ecoquest.app.ui.feature.perfil.PerfilEvent
import com.ecoquest.app.ui.feature.perfil.PerfilScreen
import com.ecoquest.app.ui.feature.perfil.PerfilViewModel
import com.ecoquest.app.ui.feature.perfil.PerfilUsuarioScreen
import com.ecoquest.app.ui.feature.perfil.PerfilUsuarioViewModel
import com.ecoquest.app.ui.feature.tienda.TiendaScreen
import com.ecoquest.app.ui.feature.tienda.TiendaViewModel
import kotlinx.serialization.Serializable

val NAV_DURATION = 350
private val SPRING_FLOAT = spring<Float>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMediumLow
)
private val SPRING_FLOAT_STIFF = spring<Float>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessHigh
)
private val SPRING_OFFSET = spring<IntOffset>(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMediumLow
)

private fun slideInRight() = slideInHorizontally(
    initialOffsetX = { it },
    animationSpec = SPRING_OFFSET
) + fadeIn(animationSpec = tween(220))

private fun slideOutLeft() = slideOutHorizontally(
    targetOffsetX = { -it },
    animationSpec = tween(NAV_DURATION, easing = FastOutSlowInEasing)
) + fadeOut(animationSpec = tween(200))

private fun slideInLeft() = slideInHorizontally(
    initialOffsetX = { -it },
    animationSpec = SPRING_OFFSET
) + fadeIn(animationSpec = tween(220))

private fun slideOutRight() = slideOutHorizontally(
    targetOffsetX = { it },
    animationSpec = tween(NAV_DURATION, easing = FastOutSlowInEasing)
) + fadeOut(animationSpec = tween(200))

private fun scaleFadeIn() = scaleIn(
    initialScale = 0.92f,
    animationSpec = SPRING_FLOAT
) + fadeIn(animationSpec = SPRING_FLOAT_STIFF)

private fun scaleFadeOut() = scaleOut(
    targetScale = 0.95f,
    animationSpec = tween(200)
) + fadeOut(animationSpec = tween(200))

private fun slideUpIn() = slideInVertically(
    initialOffsetY = { it / 6 },
    animationSpec = SPRING_OFFSET
) + fadeIn(animationSpec = SPRING_FLOAT_STIFF)

private fun slideDownOut() = slideOutVertically(
    targetOffsetY = { it / 4 },
    animationSpec = tween(200)
) + fadeOut(animationSpec = tween(200))

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
    data object Juegos

    @Serializable
    data object EcoQuiz

    @Serializable
    data object Memorama

    @Serializable
    data object AhorraEnergia

    @Serializable
    data object WordleEco

    @Serializable
    data object Ajustes

    @Serializable
    data class Miembros(val comunidadId: Long)

    @Serializable
    data object Retos

    @Serializable
    data class PerfilUsuario(val usuarioId: Long)
}

fun NavGraphBuilder.appNavGraph(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    composable<Routes.Home>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
    ) {
        val vm: HomeContentViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        HomeContentScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is HomeContentEvent.OnNavigateToPerfil -> navController.navigate(Routes.Perfil) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToJuegos -> navController.navigate(Routes.Juegos)
                    is HomeContentEvent.OnNavigateToJuego -> {
                        val ruta = when (event.juegoId) {
                            1L -> Routes.EcoQuiz
                            2L -> Routes.Memorama
                            3L -> Routes.AhorraEnergia
                            4L -> Routes.WordleEco
                            else -> Routes.Juegos
                        }
                        navController.navigate(ruta)
                    }
                    is HomeContentEvent.OnNavigateToEventos -> navController.navigate(Routes.Eventos) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToComunidades -> navController.navigate(Routes.Comunidades) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToTienda -> navController.navigate(Routes.Tienda) { launchSingleTop = true }
                    is HomeContentEvent.OnNavigateToEvento -> navController.navigate(Routes.Evento(event.eventoId))
                    is HomeContentEvent.OnNavigateToComunidad -> navController.navigate(Routes.ComunidadDentro(event.comunidadId.toInt()))
                    is HomeContentEvent.OnNavigateToRetos -> navController.navigate(Routes.Retos) { launchSingleTop = true }
                }
            }
        )
    }

    composable<Routes.Eventos>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
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
        enterTransition = { slideInRight() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInLeft() },
        popExitTransition = { slideOutRight() }
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.Evento>()
        val vm: EventoDetalleViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarEvento(route.eventoId) }
        EventoDetalleScreen(eventoId = route.eventoId, uiState = uiState, onEvent = { event ->
            when (event) {
                is EventoDetalleEvent.OnNavigateToPerfilUsuario ->
                    navController.navigate(Routes.PerfilUsuario(event.usuarioId))
                else -> vm.onEvent(event)
            }
        })
    }

    composable<Routes.Comunidades>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
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
        enterTransition = { slideInRight() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInLeft() },
        popExitTransition = { slideOutRight() }
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.ComunidadDentro>()
        val vm: ComunidadDetalleViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarComunidad(route.comunidadId.toLong()) }
        ComunidadDetalleScreen(
            comunidadId = route.comunidadId.toLong(),
            uiState = uiState,
            onEvent = { event -> vm.onEvent(event) },
            onNavigateToEvento = { eventoId -> navController.navigate(Routes.Evento(eventoId)) },
            onNavigateToMiembros = { navController.navigate(Routes.Miembros(route.comunidadId.toLong())) }
        )
    }

    composable<Routes.Tienda>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
    ) {
        val vm: TiendaViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        TiendaScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) })
    }

    composable<Routes.Perfil>(
        enterTransition = { slideUpIn() },
        exitTransition = { slideDownOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
    ) {
        val vm: PerfilViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        PerfilScreen(
            uiState = uiState,
            onEvent = { event ->
                when (event) {
                    is PerfilEvent.OnGoToAjustes -> navController.navigate(Routes.Ajustes)
                    is PerfilEvent.OnGoToRetos -> navController.navigate(Routes.Retos) { launchSingleTop = true }
                    is PerfilEvent.OnGoToTienda -> navController.navigate(Routes.Tienda) { launchSingleTop = true }
                    is PerfilEvent.OnGoToEventos -> navController.navigate(Routes.Eventos) { launchSingleTop = true }
                    is PerfilEvent.OnGoToComunidades -> navController.navigate(Routes.Comunidades) { launchSingleTop = true }
                    is PerfilEvent.OnLogout -> onLogout()
                    else -> vm.onEvent(event)
                }
            }
        )
    }

    composable<Routes.Juegos>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
    ) {
        JuegosScreen(
            onBack = { navController.popBackStack() },
            onNavigateToJuego = { juegoId ->
                when (juegoId) {
                    1L -> navController.navigate(Routes.EcoQuiz)
                    2L -> navController.navigate(Routes.Memorama)
                    3L -> navController.navigate(Routes.AhorraEnergia)
                    4L -> navController.navigate(Routes.WordleEco)
                }
            }
        )
    }

    composable<Routes.EcoQuiz>(
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        EcoQuizScreen(onBack = { navController.popBackStack() })
    }

    composable<Routes.Memorama>(
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        MemoramaScreen(onBack = { navController.popBackStack() })
    }

    composable<Routes.AhorraEnergia>(
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        AhorraEnergiaScreen(onBack = { navController.popBackStack() })
    }

    composable<Routes.WordleEco>(
        enterTransition = { slideInHorizontally { it } + fadeIn() },
        exitTransition = { slideOutHorizontally { -it } + fadeOut() },
        popEnterTransition = { slideInHorizontally { -it } + fadeIn() },
        popExitTransition = { slideOutHorizontally { it } + fadeOut() }
    ) {
        WordleEcoScreen(
            onBack = {
                navController.navigate(Routes.Juegos) {
                    popUpTo(Routes.Home) { inclusive = false }
                }
            }
        )
    }

    composable<Routes.Ajustes>(
        enterTransition = { slideInRight() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInLeft() },
        popExitTransition = { slideOutRight() }
    ) {
        val vm: AjustesViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        AjustesScreen(uiState = uiState, onEvent = { event -> vm.onEvent(event) }, onLogout = onLogout)
    }

    composable<Routes.Retos>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
    ) {
        RetosScreen()
    }

    composable<Routes.Miembros>(
        enterTransition = { slideInRight() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInLeft() },
        popExitTransition = { slideOutRight() }
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.Miembros>()
        val vm: MiembrosViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarMiembros(route.comunidadId) }
        MiembrosScreen(
            title = "Miembros",
            onBack = { navController.popBackStack() },
            miembros = uiState.miembros,
            onNavigateToPerfilUsuario = { usuarioId -> navController.navigate(Routes.PerfilUsuario(usuarioId)) }
        )
    }

    composable<Routes.PerfilUsuario>(
        enterTransition = { slideInRight() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInLeft() },
        popExitTransition = { slideOutRight() }
    ) { backStackEntry ->
        val route = backStackEntry.toRoute<Routes.PerfilUsuario>()
        val vm: PerfilUsuarioViewModel = hiltViewModel()
        val uiState by vm.state.collectAsState()
        LaunchedEffect(route) { vm.cargarPerfil(route.usuarioId) }
        PerfilUsuarioScreen(
            uiState = uiState,
            onBack = { navController.popBackStack() },
            onToggleComunidades = { vm.onToggleComunidades() },
            onToggleEventos = { vm.onToggleEventos() },
            onToggleRetos = { vm.onToggleRetos() },
            onTogglePuntos = { vm.onTogglePuntos() }
        )
    }
}

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    onLoginSuccess: () -> Unit
) {
    composable<Routes.Login>(
        enterTransition = { scaleFadeIn() },
        exitTransition = { scaleFadeOut() },
        popEnterTransition = { scaleFadeIn() },
        popExitTransition = { scaleFadeOut() }
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
        enterTransition = { slideInRight() },
        exitTransition = { slideOutLeft() },
        popEnterTransition = { slideInLeft() },
        popExitTransition = { slideOutRight() }
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
