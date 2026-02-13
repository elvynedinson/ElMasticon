package com.evydev.elmasticon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.evydev.elmasticon.navigation.AppNavHost
import com.evydev.elmasticon.ui.theme.ElMasticonTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ElMasticonTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}