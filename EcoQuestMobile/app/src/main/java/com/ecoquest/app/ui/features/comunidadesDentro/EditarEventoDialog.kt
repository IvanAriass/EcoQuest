package com.ecoquest.app.ui.features.comunidadesDentro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarEventoDialog(
    nombreInicial: String,
    descripcionInicial: String,
    fechaInicialISO: String,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String) -> Unit
) {
    var nombre by remember { mutableStateOf(nombreInicial) }
    var descripcion by remember { mutableStateOf(descripcionInicial) }

    val displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val initialMillis = remember(fechaInicialISO) {
        runCatching {
            LocalDate.parse(fechaInicialISO.substringBefore('T'))
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli()
        }.getOrNull()
    }

    val fechaDisplayInicial = remember(fechaInicialISO) {
        runCatching {
            LocalDate.parse(fechaInicialISO.substringBefore('T')).format(displayFormatter)
        }.getOrDefault("")
    }

    var fechaDisplay by remember { mutableStateOf(fechaDisplayInicial) }
    var fechaISO by remember { mutableStateOf(fechaInicialISO) }
    var mostrarDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialMillis)

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                            fechaDisplay = localDate.format(displayFormatter)
                            fechaISO = localDate.atStartOfDay().toString()
                        }
                        mostrarDatePicker = false
                    }
                ) { Text("Aceptar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Editar evento",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del evento") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                OutlinedTextField(
                    value = fechaDisplay,
                    onValueChange = { },
                    label = { Text("Fecha") },
                    placeholder = { Text("dd/MM/yyyy") },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true
                )

                Button(
                    onClick = { mostrarDatePicker = true },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Seleccionar fecha")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(nombre, descripcion, fechaISO) }
            ) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
