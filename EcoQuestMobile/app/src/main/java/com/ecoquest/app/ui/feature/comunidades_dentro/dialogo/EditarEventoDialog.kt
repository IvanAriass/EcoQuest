package com.ecoquest.app.ui.feature.comunidades_dentro.dialogo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.theme.greenPrimary

@Composable
fun EditarEventoDialog(
    nombreInicial: String = "",
    descripcionInicial: String = "",
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, descripcion: String) -> Unit
) {
    var nombre by remember { mutableStateOf(nombreInicial) }
    var descripcion by remember { mutableStateOf(descripcionInicial) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar evento") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = greenPrimary, focusedLabelColor = greenPrimary))
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth(), maxLines = 3, colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = greenPrimary, focusedLabelColor = greenPrimary))
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(nombre, descripcion) }, colors = ButtonDefaults.buttonColors(containerColor = greenPrimary)) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
