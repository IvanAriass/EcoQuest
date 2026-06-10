package com.ecoquest.app.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

private const val ANIM_DURATION = 300

@Composable
fun AppNavHost(
    navController: NavHostController,
    onLogout: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home,
        enterTransition = { fadeIn(animationSpec = tween(ANIM_DURATION)) },
        exitTransition = { fadeOut(animationSpec = tween(ANIM_DURATION)) }
    ) {
        appNavGraph(navController = navController, onLogout = onLogout)
    }
}
