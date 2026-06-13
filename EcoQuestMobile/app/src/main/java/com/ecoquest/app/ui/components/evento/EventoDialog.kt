package com.ecoquest.app.ui.components.evento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecoquest.app.domain.model.Evento

sealed interface EventoDialogConfig {
    data object Crear : EventoDialogConfig
    data class Editar(val evento: Evento) : EventoDialogConfig
}

@Composable
fun EventoDialog(
    config: EventoDialogConfig,
    onDismiss: () -> Unit,
    onConfirm: (nombre: String, descripcion: String, fechaHora: String, ubicacion: String, imagen: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val titulo: String
    val nombreInicial: String
    val descripcionInicial: String
    val fechaHoraInicial: String
    val ubicacionInicial: String
    val imagenInicial: String
    when (config) {
        is EventoDialogConfig.Crear -> {
            titulo = "Nuevo evento"
            nombreInicial = ""
            descripcionInicial = ""
            fechaHoraInicial = ""
            ubicacionInicial = ""
            imagenInicial = ""
        }
        is EventoDialogConfig.Editar -> {
            titulo = "Editar evento"
            nombreInicial = config.evento.nombre
            descripcionInicial = config.evento.descripcion
            fechaHoraInicial = config.evento.fechaHora
            ubicacionInicial = config.evento.ubicacion
            imagenInicial = config.evento.imagen
        }
    }

    var nombre by remember { mutableStateOf(nombreInicial) }
    var descripcion by remember { mutableStateOf(descripcionInicial) }
    var fechaHora by remember { mutableStateOf(fechaHoraInicial) }
    var ubicacion by remember { mutableStateOf(ubicacionInicial) }
    var imagen by remember { mutableStateOf(imagenInicial) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = titulo,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors()
                )
                OutlinedTextField(
                    value = fechaHora,
                    onValueChange = { fechaHora = it },
                    label = { Text("Fecha y hora") },
                    placeholder = { Text("Ej: 2026-06-15T20:00:00") },
                    supportingText = { Text("Formato: AAAA-MM-DDTHH:mm:ss") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors()
                )
                OutlinedTextField(
                    value = ubicacion,
                    onValueChange = { ubicacion = it },
                    label = { Text("Ubicación") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors()
                )
                OutlinedTextField(
                    value = imagen,
                    onValueChange = { imagen = it },
                    label = { Text("URL de imagen") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = fieldColors()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(nombre, descripcion, fechaHora, ubicacion, imagen) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    "Cancelar",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}

@Composable
private fun fieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
    focusedLabelColor = MaterialTheme.colorScheme.primary
)
