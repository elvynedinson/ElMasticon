package com.evydev.elmasticon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.evydev.elmasticon.completeProfile.CompleteProfileScreen
import com.evydev.elmasticon.home.HomeScreen
import com.evydev.elmasticon.login.LoginScreen
import com.evydev.elmasticon.register.RegisterScreen

@Composable
fun AppNavHost(navController: NavHostController){

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ){

        composable(Routes.LOGIN) {
            LoginScreen(navController = navController)
        }

        composable(Routes.REGISTER){
            RegisterScreen(navController = navController)
        }

        composable(Routes.COMPLETEPROFILE){
            CompleteProfileScreen(navController = navController)
        }

        composable(Routes.HOME){
            HomeScreen(navController = navController)
        }
    }
}