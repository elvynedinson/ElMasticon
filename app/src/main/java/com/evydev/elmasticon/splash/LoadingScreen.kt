package com.evydev.elmasticon.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                        painter = painterResource(id = R.drawable.loading),
                        contentDescription = "Logo el Masticon",
                        modifier = Modifier.size(200.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)){
                                append("El ")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFFF6F00), fontWeight = FontWeight.Bold)){
                                append("Masticón")
                            }
                        },
                        fontSize = 32.sp
                    )

                    Text(
                        text = "Sabor como en casa",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier.width(150.dp),
                        color = Color(0xFFFF6F00),
                        trackColor = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "CARGANDO MENÚ...",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

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
            navController.navigate("login"){
                popUpTo("splash"){ inclusive = true}
            }
        }
    }
}
