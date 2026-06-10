package com.ecoquest.app.ui.feature.evento_dentro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.R

@Composable
fun EventoDentroScreen(
    eventoId: Long,
    uiState: EventoDentroUiState,
    onEvent: (EventoDentroEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        uiState.evento?.let { evento ->
            AsyncImage(
                model = if (evento.imagen.isNotEmpty()) evento.imagen else R.drawable.playa,
                contentDescription = evento.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = evento.estado.ifBlank { "Evento" },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primaryContainer,
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = evento.nombre,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = evento.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = evento.fechaHora,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    if (evento.ubicacion.isNotBlank()) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = evento.ubicacion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = { onEvent(EventoDentroEvent.OnUnirse) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text("Unirse", style = MaterialTheme.typography.labelLarge)
                    }
                    OutlinedButton(
                        onClick = { onEvent(EventoDentroEvent.OnAbandonar) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text("Abandonar", style = MaterialTheme.typography.labelLarge)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Comentarios",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = uiState.comentario,
                        onValueChange = { onEvent(EventoDentroEvent.OnComentarioChanged(it)) },
                        placeholder = { Text("Escribe un comentario...") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                        )
                    )
                    Button(
                        onClick = { onEvent(EventoDentroEvent.OnEnviarComentario) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Enviar")
                    }
                }
            }
        }
    }
}
