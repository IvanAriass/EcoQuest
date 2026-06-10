package com.ecoquest.app.ui.feature.evento_dentro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.ui.theme.GreenBar

@Composable
fun EventoDentroScreen(
    eventoId: Long,
    uiState: EventoDentroUiState,
    onEvent: (EventoDentroEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        uiState.evento?.let { evento ->
            AsyncImage(model = if (evento.imagen.isNotEmpty()) evento.imagen else R.drawable.playa, contentDescription = evento.nombre, modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = evento.nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C1C1C))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = evento.descripcion, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Fecha: ${evento.fechaHora}", fontSize = 13.sp, color = Color.Gray)
                Text(text = "Ubicación: ${evento.ubicacion}", fontSize = 13.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onEvent(EventoDentroEvent.OnUnirse) }, colors = ButtonDefaults.buttonColors(containerColor = GreenBar), shape = RoundedCornerShape(8.dp)) { Text("Unirse") }
                Button(onClick = { onEvent(EventoDentroEvent.OnAbandonar) }, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), shape = RoundedCornerShape(8.dp)) { Text("Abandonar") }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("Comentarios", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = uiState.comentario, onValueChange = { onEvent(EventoDentroEvent.OnComentarioChanged(it)) }, placeholder = { Text("Escribe un comentario...") }, modifier = Modifier.weight(1f), colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GreenBar))
                Button(onClick = { onEvent(EventoDentroEvent.OnEnviarComentario) }, colors = ButtonDefaults.buttonColors(containerColor = GreenBar), shape = RoundedCornerShape(8.dp)) { Text("Enviar") }
            }
        }
    }
}
