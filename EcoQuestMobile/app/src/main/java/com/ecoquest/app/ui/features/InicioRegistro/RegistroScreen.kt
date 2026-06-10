package com.ecoquest.app.ui.features.InicioRegistro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.ecoquest.app.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroEvent
import com.ecoquest.app.ui.features.InicioRegistro.InicioRegistroUiState
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import com.ecoquest.app.ui.theme.LightGrayBackground
import com.ecoquest.app.ui.theme.greenPrimary

@Composable
fun RegistroScreen(
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

            Spacer(modifier = Modifier.height(32.dp))

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
                text = "Registrarse",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Correo
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

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre de usuario
            OutlinedTextField(
                value = uiState.username,
                onValueChange = { onEvent(InicioRegistroEvent.OnUsernameChanged(it)) },
                label = { Text("Nombre usuario") },
                placeholder = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greenPrimary,
                    focusedLabelColor = greenPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Fecha de nacimiento
            OutlinedTextField(
                value = uiState.birthDate,
                onValueChange = { onEvent(InicioRegistroEvent.OnBirthDateChanged(it)) },
                label = { Text("Fecha de nacimiento") },
                placeholder = { Text("dd/mm/yyyy") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greenPrimary,
                    focusedLabelColor = greenPrimary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Contraseña
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

            Spacer(modifier = Modifier.height(12.dp))

            // Confirmar contraseña
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = { onEvent(InicioRegistroEvent.OnConfirmPasswordChanged(it)) },
                label = { Text("Confirmar contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greenPrimary,
                    focusedLabelColor = greenPrimary
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Botón crear cuenta
            Button(
                onClick = {
                    onEvent(InicioRegistroEvent.OnRegisterClicked)
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = greenPrimary
                )
            ) {
                Text(
                    text = "Crear cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {

    val fakeState = InicioRegistroUiState(
        email = "usuario@email.com",
        username = "EcoUser",
        birthDate = "01/01/2000",
        password = "123456",
        confirmPassword = "123456"
    )

    EcoQuestMobileTheme {
        RegistroScreen(
            uiState = fakeState,
            onEvent = {}
        )
    }
}