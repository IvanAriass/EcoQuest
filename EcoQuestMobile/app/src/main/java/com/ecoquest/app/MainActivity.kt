package com.ecoquest.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ecoquest.app.ui.features.ajustes.AjustesViewModel
import com.ecoquest.app.ui.features.home.HomeScreen
import com.ecoquest.app.ui.navigation.InicioRegistroNavigation.AuthNavHost
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val ajustesVm: AjustesViewModel = hiltViewModel()
            EcoQuestMobileTheme(darkTheme = ajustesVm.state.temaOscuro) {
                var isAuthenticated by remember { mutableStateOf(false) }

                if (isAuthenticated) {
                    HomeScreen(
                        onLogout = { isAuthenticated = false }
                    )
                } else {
                    val authNavController = rememberNavController()
                    AuthNavHost(
                        navController = authNavController,
                        onLoginSuccess = { isAuthenticated = true }
                    )
                }
            }
        }
    }
}



