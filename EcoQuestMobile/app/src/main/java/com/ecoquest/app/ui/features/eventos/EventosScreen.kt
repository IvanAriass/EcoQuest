package com.ecoquest.app.ui.features.eventos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.models.Evento
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import java.time.LocalDateTime

private fun colorBorde(estado: String): Color = when (estado.lowercase()) {
    "noticia"  -> Color(0xFFE53935)
    "urgente"  -> Color(0xFFFF6F00)
    "cerrado"  -> Color(0xFF757575)
    else       -> GreenBar
}

@Composable
fun EventosScreen(
    uiState: EventosUiState,
    onEvent: (EventosEvent) -> Unit,
    onNavigateToEvento: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Eventos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C),
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = uiState.textoBusqueda,
            onValueChange = { onEvent(EventosEvent.OnBusquedaChanged(it)) },
            placeholder = { Text("Buscar evento", color = Color.Gray) },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenBar,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(uiState.eventosFiltrados) { evento ->
                EventoCard(
                    evento = evento,
                    onClick = { onNavigateToEvento(evento.id) }
                )
            }
        }
    }
}

@Composable
private fun EventoCard(evento: Evento, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(colorBorde(evento.estado))
            )

            Spacer(modifier = Modifier.width(10.dp))

            AsyncImage(
                model = if (evento.imagen.isNotEmpty()) evento.imagen else R.drawable.iconoeco,
                contentDescription = null,
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = evento.estado.ifBlank { "Evento Comunitario" },
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = evento.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = evento.descripcion,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            AsyncImage(
                model = if (evento.imagen.isNotEmpty()) evento.imagen else R.drawable.playa,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

// ─── Previews ───
private val fakeEventos = listOf(
    Evento(
        id = 1L, nombre = "Recogida de Basura",
        descripcion = "En la playa del Postiguer",
        fechaHora = LocalDateTime.now(), ubicacion = "Valencia",
        imagen = "", estado = "Evento Comunitario"
    ),
    Evento(
        id = 2L, nombre = "Nuevos fondos ODS",
        descripcion = "Mayor remuneración para proyectos sostenibles",
        fechaHora = LocalDateTime.now(), ubicacion = "Madrid",
        imagen = "", estado = "Noticia"
    ),
    Evento(
        id = 3L, nombre = "Junta de Alimentos",
        descripcion = "En plaza mar",
        fechaHora = LocalDateTime.now(), ubicacion = "Barcelona",
        imagen = "", estado = "Urgente"
    )
)

@Preview(showBackground = true, name = "EventosScreen - lista")
@Composable
fun EventosScreenPreview() {
    EcoQuestMobileTheme {
        EventosScreen(
            uiState = EventosUiState(eventos = fakeEventos, eventosFiltrados = fakeEventos),
            onEvent = {},
            onNavigateToEvento = {}
        )
    }
}

@Preview(showBackground = true, name = "EventosScreen - buscando")
@Composable
fun EventosScreenBuscandoPreview() {
    EcoQuestMobileTheme {
        EventosScreen(
            uiState = EventosUiState(
                eventos = fakeEventos,
                eventosFiltrados = fakeEventos.take(1),
                textoBusqueda = "Recogida"
            ),
            onEvent = {},
            onNavigateToEvento = {}
        )
    }
}

@Preview(showBackground = true, name = "EventosScreen - vacía")
@Composable
fun EventosScreenVaciaPreview() {
    EcoQuestMobileTheme {
        EventosScreen(
            uiState = EventosUiState(eventos = emptyList(), eventosFiltrados = emptyList()),
            onEvent = {},
            onNavigateToEvento = {}
        )
    }
}
