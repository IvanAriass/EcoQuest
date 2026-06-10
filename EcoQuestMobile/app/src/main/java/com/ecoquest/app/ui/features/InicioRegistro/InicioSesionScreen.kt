package com.ecoquest.app.ui.features.InicioRegistro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.R
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroEvent
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroUiState
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import com.ecoquest.app.ui.theme.LightGrayBackground
import com.ecoquest.app.ui.theme.greenPrimary

@Composable
fun InicioSesionScreen(
    uiState: InicioRegistroUiState,
    onEvent: (InicioRegistroEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.ecoquesticono),
                contentDescription = "EcoQuest Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = "Iniciar sesión",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo correo
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(InicioRegistroEvent.OnEmailChanged(it)) },
                label = { Text("Correo") },
                placeholder = { Text("ejemplo@example.com") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greenPrimary,
                    focusedLabelColor = greenPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo contraseña
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onEvent(InicioRegistroEvent.OnPasswordChanged(it)) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greenPrimary,
                    focusedLabelColor = greenPrimary
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón
            Button(
                onClick = { onEvent(InicioRegistroEvent.OnLoginClicked) },
                modifier = Modifier
                    .width(200.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = greenPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
            TextButton(onClick = { onEvent(InicioRegistroEvent.OnGoToRegistro) }) {
                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = greenPrimary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioSesionScreenPreview() {

    val fakeState = InicioRegistroUiState(
        email = "usuario@email.com",
        password = "123456"
    )

    EcoQuestMobileTheme {
        InicioSesionScreen(
            uiState = fakeState,
            onEvent = {}
        )
    }
}