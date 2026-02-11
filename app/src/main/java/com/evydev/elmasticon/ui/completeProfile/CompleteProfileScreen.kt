package com.evydev.elmasticon.ui.completeProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.evydev.elmasticon.R
import com.evydev.elmasticon.navigation.Routes
import com.evydev.elmasticon.ui.masticonLoading.MasticonLoading

@Composable
fun CompleteProfileScreen(
    viewModel: CompleteProfileViewModel = viewModel(),
    navController: NavController,
    email: String
) {

    val completeFormState by viewModel.completeFormState.collectAsState()
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column() {
            Box(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Atrás",
                        modifier = Modifier.size(40.dp)
                    )
                }

                Text(
                    text = "Completa tu perfil",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = Color.LightGray.copy(alpha = 0.3f)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFFCF8F8))
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(160.dp)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.BottomEnd
                ) {


                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(4.dp, Color.White, CircleShape),
                        shape = CircleShape,
                        color = Color(0xFFFFDAB9)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Foto de Perfil",
                            modifier = Modifier.padding(30.dp),
                            tint = Color.White
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .size(42.dp)
                            .border(2.dp, Color.White, CircleShape)
                            .clickable { },
                        shape = CircleShape,
                        color = Color(0xFFEC1313)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar foto",
                            modifier = Modifier.padding(9.dp),
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¡Casi listos para comer!",
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    ),

                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Solo unos datos más para tu primer pedido.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                CompleteProfileTextField(
                    label = "Correo electrónico",
                    placeholder = "eltragon@gmail.com",
                    value = email,
                    onValueChange = {},
                    icon = R.drawable.ic_lock,
                    readOnly = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                CompleteProfileTextField(
                    label = "Nombre completo",
                    placeholder = "Ej. Juan Perez",
                    value = completeFormState.name,
                    onValueChange = { viewModel.onNameChange(it) },
                    icon = R.drawable.ic_user,
                    error = completeFormState.nameError
                )

                Spacer(modifier = Modifier.height(16.dp))

                CompleteProfileTextField(
                    label = "Teléfono",
                    placeholder = "987654321",
                    value = completeFormState.phone,
                    onValueChange = { viewModel.onPhoneChange(it) },
                    icon = R.drawable.ic_phone,
                    keyboardType = KeyboardType.Phone,
                    prefix = { Text("+51 ", color = Color.Black)},
                    error = completeFormState.phoneError
                )

                Spacer(modifier = Modifier.height(35.dp))

                Button(
                    onClick = { viewModel.complete(email) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC1313))
                ) {
                    Text(
                        text = "Guardar y continuar",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                when (state) {

                    CompleteUiState.Idle -> {}

                    CompleteUiState.Loading -> {}

                    CompleteUiState.Success -> {
                        LaunchedEffect(Unit) {
                            navController.navigate(Routes.HOME){
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                    }

                    is CompleteUiState.Error -> {
                        Text(
                            text = (state as CompleteUiState.Error).message,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        if (state is CompleteUiState.Loading){
            MasticonLoading(message = "GUARDANDO PERFIL...")
        }
    }
}

@Composable
fun CompleteProfileTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    icon: Int,
    error: String? = null,
    prefix: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            readOnly = readOnly,
            singleLine = true,
            prefix = prefix,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = null,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = if (keyboardType == KeyboardType.Text)
                    KeyboardCapitalization.Words
                else
                    KeyboardCapitalization.None,

                autoCorrectEnabled = false
            ),

            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                            ),
                            contentDescription = null,
                            tint = Color(0xFF666666)
                        )
                    }
                } else {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF94A3B8)
                    )
                }
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },


            colors = OutlinedTextFieldDefaults.colors(


                focusedContainerColor = if (readOnly) Color(0xFFF2F5F8) else Color.White,
                unfocusedContainerColor = if (readOnly) Color(0xFFF2F5F8) else Color.White,

                unfocusedBorderColor = if (readOnly) Color.Transparent else Color(0xFFF3E5E5),
                focusedTextColor = if (readOnly) Color.Gray else Color.Black,
                unfocusedTextColor = if (readOnly) Color.Gray else Color.DarkGray,

                focusedBorderColor = if (readOnly) Color.Transparent else Color(0xFFEC1313),
                cursorColor = Color(0xFFEC1313)
            )

        )

        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}
