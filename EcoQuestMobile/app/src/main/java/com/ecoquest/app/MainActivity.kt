package com.ecoquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ecoquest.app.managers.GlobalToastManager
import com.ecoquest.app.managers.PreferencesManager
import com.ecoquest.app.ui.components.general.EcoToastHost
import com.ecoquest.app.ui.components.general.ToastData
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

    @Inject
    lateinit var globalToastManager: GlobalToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationVm: NavigationViewModel = hiltViewModel()
            val isAuthenticated by navigationVm.isAuthenticated.collectAsState()
            val isDarkTheme by preferencesManager.isDarkTheme.collectAsState()
            val themeName by preferencesManager.themeName.collectAsState()
            val authNavController = rememberNavController()

            var currentToast by remember { mutableStateOf<ToastData?>(null) }

            LaunchedEffect(Unit) {
                globalToastManager.toastEvent.collect { toast ->
                    currentToast = toast
                }
            }

            EcoQuestMobileTheme(darkTheme = isDarkTheme, themeName = themeName) {
                Box(modifier = Modifier.fillMaxSize()) {
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

                    EcoToastHost(
                        toast = currentToast,
                        onDismiss = { currentToast = null },
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .statusBarsPadding()
                    )
                }
            }
        }
    }
}
