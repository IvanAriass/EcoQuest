package com.ecoquest.app.ui.feature.comunidades.dialogo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.theme.greenPrimary

@Composable
fun CrearComunidadDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, descripcion: String, imagen: String) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva comunidad") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = greenPrimary, focusedLabelColor = greenPrimary)
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = greenPrimary, focusedLabelColor = greenPrimary)
                )
                OutlinedTextField(
                    value = imagen,
                    onValueChange = { imagen = it },
                    label = { Text("URL de imagen") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = greenPrimary, focusedLabelColor = greenPrimary)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(nombre, descripcion, imagen) },
                colors = ButtonDefaults.buttonColors(containerColor = greenPrimary)
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
