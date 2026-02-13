package com.evydev.elmasticon.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.evydev.elmasticon.R
import com.evydev.elmasticon.navigation.Routes
import com.evydev.elmasticon.ui.masticonLoading.MasticonLoading

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val loginFormState by viewModel.loginFormState.collectAsState()

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFCF8F8))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Box(contentAlignment = Alignment.BottomCenter) {
                    Image(
                        painter = painterResource(R.drawable.pizza_background),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.5f),
                                        Color.White
                                    ),
                                    startY = 100f
                                )
                            )
                    )
                    Surface(
                        modifier = Modifier.padding(bottom = 16.dp),
                        shape = RoundedCornerShape(50),
                        color = Color.White
                    ) {
                        Text(
                            text = "EL MASTICÓN",
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEC1313)
                            )
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Qué hambre!",
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.ExtraBold)
                )
                Text(
                    text = "Inicia sesión para pedir",
                    style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(25.dp))

                LoginTextField(
                    label = "Correo electrónico",
                    placeholder = "ejemplo@correo.com",
                    value = loginFormState.email,
                    onValueChange = { viewModel.onEmailChange(it)},
                    leadingIcon = R.drawable.ic_mail,
                    error = loginFormState.emailError,
                )

                Spacer(modifier = Modifier.height(16.dp))

                LoginTextField(
                    label = "Contraseña",
                    placeholder = "********",
                    value = loginFormState.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    isPassword = true,
                    leadingIcon = R.drawable.ic_lock,
                    error = loginFormState.passwordError
                )

                Text(
                    text = "¿Olvidaste tu contraseña?",
                    style = TextStyle(fontSize = 15.sp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            navController.navigate(Routes.FORGOTPASSWORD)
                        }
                        .padding(top = 8.dp),
                    color = Color(0xFFEC1313),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.login() },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC1313)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Entrar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                when (state) {

                    LoginUiState.Idle -> {}

                    LoginUiState.Loading -> {}

                    LoginUiState.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }

                            viewModel.signInWithGoogle(context) {email, alreadyExists ->
                                if (alreadyExists) {
                                    navController.navigate(Routes.HOME){
                                        popUpTo(Routes.LOGIN) { inclusive = true }
                                    }
                                }
                            }

                        }
                    }

                    is LoginUiState.Error -> {
                        Text(
                            text = (state as LoginUiState.Error).message,
                            color = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(8.dp)
                            .border(1.dp, Color(0xFFEC1313), CircleShape)
                    )
                    Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {
                        viewModel.signInWithGoogle(context) {email, alreadyExists ->
                            if (alreadyExists) { navController.navigate(Routes.HOME) {
                                    popUpTo(Routes.LOGIN) { inclusive = true}
                                }
                            } else {
                                navController.navigate("completeprofile/$email")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google_logo),
                            contentDescription = "Google Logo",
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            "Continuar con Google",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(29.dp))

            Row() {
                Text(
                    text = "¿No tienes cuenta?"
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" Regístrate")
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .clickable {
                            navController.navigate(Routes.REGISTER)
                        }
                )
            }
        }
        if (state is LoginUiState.Loading){
            MasticonLoading(message = "INICIANDO SESIÓN...")
        }
    }
}
@Composable
fun LoginTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    leadingIcon: Int,
    error: String? = null
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            singleLine = true,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),

            keyboardOptions = if (isPassword) {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                )
            }else{
                KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            },

            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF9D5151)
                )
            },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                            ),
                            contentDescription = null,
                            tint = Color(0xFF9D5151)
                        )
                    }

                }
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },


            colors = OutlinedTextFieldDefaults.colors(

                unfocusedBorderColor = Color(0xFFF3E5E5),
                focusedBorderColor = Color(0xFFEC1313),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                cursorColor = Color(0xFFEC1313)
            ),
        )

        if (error != null){
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}
