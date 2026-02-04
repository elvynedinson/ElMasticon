package com.evydev.elmasticon.completeProfile

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.evydev.elmasticon.R

@Composable
fun CompleteProfileScreen(navController: NavController) {

    var name by rememberSaveable{ mutableStateOf("") }
    var phone by rememberSaveable{ mutableStateOf("") }
    var correoPorAhora by rememberSaveable{ mutableStateOf("") }

    Column() {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentAlignment = Alignment.Center
        ){
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
            ){


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
                        .clickable{ },
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
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            CompleteProfileTextField(
                label = "Correo electrónico",
                placeholder = "eltragon@gmail.com",
                value = correoPorAhora,
                onValueChange = { correoPorAhora = it},
                icon = R.drawable.ic_lock
            )

            Spacer(modifier = Modifier.height(16.dp))

            CompleteProfileTextField(
                label = "Nombre completo",
                placeholder = "Ej. Juan Perez",
                value = name,
                onValueChange = {name = it},
                icon = R.drawable.ic_user,
            )

            Spacer(modifier = Modifier.height(16.dp))

            CompleteProfileTextField(
                label = "Teléfono",
                placeholder = "51 123456789",
                value = phone,
                onValueChange = {phone = it},
                icon = R.drawable.ic_phone
            )

            Spacer(modifier = Modifier.height(35.dp))

            Button(
                onClick = {},
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
                unfocusedBorderColor = Color(0xFFF3E5E5),
                focusedBorderColor = Color(0xFFEC1313),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.DarkGray,
                cursorColor = Color(0xFFEC1313)
            )

        )
    }
}