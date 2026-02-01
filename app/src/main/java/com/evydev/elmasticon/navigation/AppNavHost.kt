package com.evydev.elmasticon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.evydev.elmasticon.splash.SplashScreen
import com.evydev.elmasticon.welcome.WelcomeScreen

@Composable
fun AppNavHost(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ){

        composable(Routes.SPLASH){
            SplashScreen(navController = navController)
        }

        composable(Routes.WELCOME) {
            WelcomeScreen()
        }
    }
}