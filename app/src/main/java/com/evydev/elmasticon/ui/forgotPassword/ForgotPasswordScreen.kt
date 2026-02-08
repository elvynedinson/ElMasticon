package com.evydev.elmasticon.ui.forgotPassword

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.evydev.elmasticon.R
import com.evydev.elmasticon.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(navController: NavController) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showSheet by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Atrás",
            modifier = Modifier
                .size(40.dp)
                .clickable { navController.popBackStack() }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.size(80.dp)) {
            Surface(
                modifier = Modifier.size(70.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFFFDE7E7)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock_reset),
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Surface(
                modifier = Modifier
                    .size(21.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-2).dp, y = (-7).dp),
                shape = CircleShape,
                color = Color.Red,
                border = BorderStroke(2.dp, Color.White)


            ) {}
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "¿Olvidaste tu\ncontraseña?",
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 32.sp,
                lineHeight = 40.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No te preocupes, suele pasar. Te enviaremos un correo con instrucciones para restablecer tu contraseña.",
            modifier = Modifier.padding(end = 10.dp),
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 26.sp
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        ForgotPasswordTextField(
            label = "Correo Electrónico",
            placeholder = "tu@email.com",
            value = email,
            onValueChange = { email = it},
            leadingIcon = R.drawable.ic_mail
        )

        Spacer(modifier = Modifier.height(30.dp))


        Button(
            onClick = { showSheet = true},
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEC1313)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Enviar enlace", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = Color.White,
                dragHandle = { BottomSheetDefaults.DragHandle() }
            ) {
                ForgotPasswordVerificationContent(
                    navController = navController,
                    onDismiss = { showSheet = false }
                )
            }
        }
    }
}

@Composable
fun ForgotPasswordTextField(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: Int
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeholder, color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF9D5151)
                )
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
fun ForgotPasswordVerificationContent(onDismiss: () -> Unit, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            color = Color(0xFFDCFCE7)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_emailread),
                contentDescription = null,
                tint = Color(0xFF16A34A),
                modifier = Modifier.padding(18.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "¡Correo enviado!",
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Revisa tu bandeja de entrada. Si existe una cuenta con ese correo, recibirás un mensaje con los pasos a seguir.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Text(
                text = "Reenviar",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                onDismiss()
                navController.navigate(Routes.LOGIN)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Text(
                text = "Entendido, volver",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }
    }
}