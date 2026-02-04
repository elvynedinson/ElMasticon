package com.evydev.elmasticon.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.evydev.elmasticon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFCF8F8))
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Atras",
            modifier = Modifier
                .size(40.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Únete al Masticón",
            color = Color(0xFFEC1313),
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "¡Prepárate para el mejor sabor!",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )


        Spacer(modifier = Modifier.height(20.dp))

        RegisterTextField(
            label = "Nombre Completo",
            placeholder = "Alexander Chipana",
            value = name,
            onValueChange = { name = it },
            icon = R.drawable.ic_user
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegisterTextField(
            label = "Teléfono",
            placeholder = "+51 123456789",
            value = phone,
            onValueChange = { phone = it },
            icon = R.drawable.ic_phone
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegisterTextField(
            label = "Correo Electrónico",
            placeholder = "ejemplo@correo.com",
            value = email,
            onValueChange = { email = it },
            icon = R.drawable.ic_mail
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegisterTextField(
            label = "Contraseña",
            placeholder = "*********",
            value = password,
            onValueChange = { password = it },
            isPassword = true,
            icon = R.drawable.ic_eye_closed
        )

        Spacer(modifier = Modifier.height(16.dp))

        RegisterTextField(
            label = "Confirmar Contraseña",
            placeholder = "*********",
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            isPassword = true,
            icon = R.drawable.ic_eye_closed
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC1313)),
            shape = RoundedCornerShape(32.dp)
        ) {
            Text(
                "Crear Cuenta",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Divider(modifier = Modifier.weight(1f), color = Color.LightGray)

            Text(
                text = " o continúa con ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Normal
                )
            )

            Divider(modifier = Modifier.weight(1f), color = Color.LightGray)
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
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

                Text("Registrarte con Google", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Text(
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                        append("Al registrarte aceptas nuestros ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Términos y Condiciones")
                    }

                    withStyle(style = SpanStyle(color = Color.Gray, fontSize = 12.sp)) {
                        append(" y nuestra ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Política de privacidad")
                    }

                }
            )
        }

    }
}


@Composable
fun RegisterTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    icon: Int
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = null,

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
                        tint = Color(0xFFEC1313)
                    )
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
            )

        )
    }
}

@Composable
fun EmailVerificationContent(onDismiss: () -> Unit){

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            color = Color(0xFFE8F5E9)
        ) {

        }
    }

}