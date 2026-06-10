package com.ecoquest.app.ui.feature.comunidades_dentro

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.ui.feature.comunidades_dentro.dialogo.CrearEventoDialog
import com.ecoquest.app.ui.theme.GreenBar

@Composable
fun ComunidadesDentroScreen(
    comunidadId: Long,
    uiState: ComunidadesDentroUiState,
    onEvent: (ComunidadesDentroEvent) -> Unit,
    onNavigateToEvento: (Long) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        uiState.comunidad?.let { comunidad ->
            AsyncImage(
                model = if (comunidad.imagen.isNotEmpty()) comunidad.imagen else R.drawable.iconoeco,
                contentDescription = comunidad.nombre,
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = comunidad.nombre, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C1C1C))
            Text(text = comunidad.descripcion, fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { onEvent(ComunidadesDentroEvent.OnUnirse) }, colors = ButtonDefaults.buttonColors(containerColor = GreenBar), shape = RoundedCornerShape(8.dp)) { Text("Unirse") }
                Button(onClick = { onEvent(ComunidadesDentroEvent.OnAbandonar) }, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), shape = RoundedCornerShape(8.dp)) { Text("Abandonar") }
                Button(onClick = { onEvent(ComunidadesDentroEvent.OnMostrarCrearEvento) }, colors = ButtonDefaults.buttonColors(containerColor = GreenBar), shape = RoundedCornerShape(8.dp)) { Text("+ Evento") }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Eventos", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(uiState.eventos) { evento ->
                    EventoItem(evento = evento, onClick = { onNavigateToEvento(evento.id) })
                }
            }
        }
    }
    if (uiState.showCrearEventoDialog) {
        CrearEventoDialog(
            onDismiss = { onEvent(ComunidadesDentroEvent.OnDismissDialog) },
            onConfirm = { nombre, descripcion, fechaHora, ubicacion, imagen ->
                onEvent(ComunidadesDentroEvent.OnGuardarEvento(nombre, descripcion, fechaHora, ubicacion, imagen))
            }
        )
    }
}

@Composable
private fun EventoItem(evento: Evento, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = evento.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = evento.descripcion, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
            }
            Text(text = evento.fechaHora, color = Color.Gray, fontSize = 11.sp)
        }
    }
}
