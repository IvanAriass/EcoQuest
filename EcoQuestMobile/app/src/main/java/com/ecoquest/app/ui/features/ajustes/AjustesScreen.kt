package com.ecoquest.app.ui.features.ajustes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import com.ecoquest.app.ui.theme.greenPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesScreen(
    uiState: AjustesUiState,
    onEvent: (AjustesEvent) -> Unit,
    onLogout: () -> Unit
) {
    val idiomas = listOf("Español", "English", "Français", "Deutsch")
    var idiomaExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        Text(
            text = "Ajustes",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E2E2E),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // ─── Sección Preferencias ───
        AjustesSectionTitle("Preferencias")

        // Notificaciones
        AjustesToggleItem(
            icon = Icons.Filled.Notifications,
            label = "Notificaciones",
            checked = uiState.notificacionesActivadas,
            onCheckedChange = { onEvent(AjustesEvent.OnToggleNotificaciones) }
        )

        // Tema oscuro
        AjustesToggleItem(
            icon = Icons.Filled.Star,
            label = "Tema oscuro",
            checked = uiState.temaOscuro,
            onCheckedChange = { onEvent(AjustesEvent.OnToggleTema) }
        )

        // Idioma
        AjustesSectionTitle("Idioma")

        ExposedDropdownMenuBox(
            expanded = idiomaExpanded,
            onExpandedChange = { idiomaExpanded = !idiomaExpanded }
        ) {
            OutlinedTextField(
                value = uiState.idiomaSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Idioma") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = idiomaExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = greenPrimary,
                    focusedLabelColor = greenPrimary
                )
            )
            ExposedDropdownMenu(
                expanded = idiomaExpanded,
                onDismissRequest = { idiomaExpanded = false }
            ) {
                idiomas.forEach { idioma ->
                    DropdownMenuItem(
                        text = { Text(idioma) },
                        onClick = {
                            onEvent(AjustesEvent.OnIdiomaChanged(idioma))
                            idiomaExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ─── Sección Cuenta ───
        AjustesSectionTitle("Cuenta")

        // Cambiar contraseña
        AjustesButtonItem(
            icon = Icons.Filled.Lock,
            label = "Cambiar contraseña",
            onClick = { onEvent(AjustesEvent.OnCambiarPasswordClicked) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Cerrar sesión
        AjustesButtonItem(
            icon = Icons.Filled.ExitToApp,
            label = "Cerrar sesión",
            onClick = {
                onEvent(AjustesEvent.OnCerrarSesionClicked)
                onLogout()
            },
            tint = Color(0xFFE65100)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Eliminar cuenta
        AjustesButtonItem(
            icon = Icons.Filled.Delete,
            label = "Eliminar cuenta",
            onClick = { onEvent(AjustesEvent.OnEliminarCuentaClicked) },
            tint = Color.Red
        )

        Spacer(modifier = Modifier.height(24.dp))
    }

    // ─── Diálogo eliminar cuenta ───
    if (uiState.showEliminarCuentaDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(AjustesEvent.OnDismissEliminarCuenta) },
            title = { Text("Eliminar cuenta", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(
                    onClick = {
                        onEvent(AjustesEvent.OnConfirmarEliminarCuenta)
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { onEvent(AjustesEvent.OnDismissEliminarCuenta) }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // ─── Diálogo cambiar contraseña ───
    if (uiState.showCambiarPasswordDialog) {
        CambiarPasswordDialog(
            onDismiss = { onEvent(AjustesEvent.OnDismissCambiarPassword) },
            onConfirm = { nuevaPassword ->
                onEvent(AjustesEvent.OnConfirmarCambiarPassword(nuevaPassword))
            }
        )
    }
}

@Composable
private fun AjustesSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = GreenBar,
        modifier = Modifier.padding(bottom = 8.dp, top = 4.dp)
    )
}

@Composable
private fun AjustesToggleItem(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = GreenBar)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label, modifier = Modifier.weight(1f), fontSize = 15.sp)
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange() },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = GreenBar)
            )
        }
    }
}

@Composable
private fun AjustesButtonItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    tint: Color = Color(0xFF2E2E2E)
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label, fontSize = 15.sp, color = tint, modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}

@Composable
private fun CambiarPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var nuevaPassword by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Cambiar contraseña", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nuevaPassword,
                    onValueChange = { nuevaPassword = it; error = null },
                    label = { Text("Nueva contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenPrimary,
                        focusedLabelColor = greenPrimary
                    )
                )
                OutlinedTextField(
                    value = confirmarPassword,
                    onValueChange = { confirmarPassword = it; error = null },
                    label = { Text("Confirmar contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenPrimary,
                        focusedLabelColor = greenPrimary
                    )
                )
                if (error != null) {
                    Text(text = error!!, color = Color.Red, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nuevaPassword != confirmarPassword) {
                        error = "Las contraseñas no coinciden"
                    } else if (nuevaPassword.isBlank()) {
                        error = "La contraseña no puede estar vacía"
                    } else {
                        onConfirm(nuevaPassword)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = greenPrimary)
            ) {
                Text("Guardar", color = Color.Black)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

// ─── Previews ───
@Preview(showBackground = true, name = "AjustesScreen")
@Composable
fun AjustesScreenPreview() {
    EcoQuestMobileTheme {
        AjustesScreen(
            uiState = AjustesUiState(),
            onEvent = {},
            onLogout = {}
        )
    }
}

@Preview(showBackground = true, name = "AjustesScreen - diálogo eliminar cuenta")
@Composable
fun AjustesScreenEliminarPreview() {
    EcoQuestMobileTheme {
        AjustesScreen(
            uiState = AjustesUiState(showEliminarCuentaDialog = true),
            onEvent = {},
            onLogout = {}
        )
    }
}

@Preview(showBackground = true, name = "AjustesScreen - diálogo cambiar password")
@Composable
fun AjustesScreenPasswordPreview() {
    EcoQuestMobileTheme {
        AjustesScreen(
            uiState = AjustesUiState(showCambiarPasswordDialog = true),
            onEvent = {},
            onLogout = {}
        )
    }
}