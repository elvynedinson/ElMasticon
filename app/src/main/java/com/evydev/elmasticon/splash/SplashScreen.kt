package com.evydev.elmasticon.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.evydev.elmasticon.R

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state) {

        is SplashUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.splash),
                        contentDescription = "Logo el Masticon",
                        modifier = Modifier.size(200.dp)
                    )

                    Spacer(modifier = Modifier.height(35.dp))

                    CircularProgressIndicator()

                }
            }
        }

        is SplashUiState.Success -> {
            // Navigation will be handled here
        }

        is SplashUiState.Error -> {
            val message = (state as SplashUiState.Error).message

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(message)
            }
        }
    }

    if (state is SplashUiState.Success){
        LaunchedEffect(Unit) {
            navController.navigate("welcome"){
                popUpTo("splash"){ inclusive = true}
            }
        }
    }
}
