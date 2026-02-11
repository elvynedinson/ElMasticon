package com.evydev.elmasticon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.evydev.elmasticon.ui.completeProfile.CompleteProfileScreen
import com.evydev.elmasticon.ui.forgotPassword.ForgotPasswordScreen
import com.evydev.elmasticon.ui.home.HomeScreen
import com.evydev.elmasticon.ui.login.LoginScreen
import com.evydev.elmasticon.ui.register.RegisterScreen

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

        composable(
            Routes.COMPLETEPROFILE,
            arguments = listOf(
                navArgument("email") { type = NavType.StringType }
            )
        ){ backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""

            CompleteProfileScreen(navController = navController, email = email)
        }

        composable(Routes.HOME){
            HomeScreen(navController = navController)
        }

        composable(Routes.FORGOTPASSWORD) {
            ForgotPasswordScreen(navController = navController)
        }

    }
}