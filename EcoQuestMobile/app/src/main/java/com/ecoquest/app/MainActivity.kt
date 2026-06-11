package com.ecoquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ecoquest.app.managers.PreferencesManager
import com.ecoquest.app.ui.feature.home.HomeScreen
import com.ecoquest.app.ui.navigation.AuthNavHost
import com.ecoquest.app.ui.navigation.NavigationViewModel
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationVm: NavigationViewModel = hiltViewModel()
            val isAuthenticated by navigationVm.isAuthenticated.collectAsState()
            val isDarkTheme by preferencesManager.isDarkTheme.collectAsState()
            val authNavController = rememberNavController()

            EcoQuestMobileTheme(darkTheme = isDarkTheme) {
                Crossfade(
                    targetState = isAuthenticated,
                    animationSpec = tween(400)
                ) { authenticated ->
                    if (authenticated) {
                        HomeScreen(onLogout = { navigationVm.onLogout() })
                    } else {
                        AuthNavHost(
                            navController = authNavController,
                            onLoginSuccess = { navigationVm.onLoginSuccess() }
                        )
                    }
                }
            }
        }
    }
}
