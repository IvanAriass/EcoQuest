package com.ecoquest.app.ui.features.eventoDentro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.models.Evento
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import java.time.LocalDateTime

private val FondoVerde = Color(0xFFE8F5E9)

@Composable
fun EventoDentroScreen(
    eventoId: Long,
    uiState: EventoDentroUiState,
    onEvent: (EventoDentroEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoVerde)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Eventos",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C),
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen circular + nombre + categoría
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = if (uiState.evento?.imagen?.isNotEmpty() == true)
                    uiState.evento.imagen else R.drawable.iconoeco,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = uiState.evento?.nombre ?: "",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C)
                )
                Text(
                    text = uiState.evento?.estado?.ifBlank { "Evento comunitario" }
                        ?: "Evento comunitario",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Hero image
        AsyncImage(
            model = if (uiState.evento?.imagen?.isNotEmpty() == true)
                uiState.evento.imagen else R.drawable.playa,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del evento
        Text(
            text = uiState.evento?.nombre ?: "",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C)
        )

        // Ubicación
        Text(
            text = uiState.evento?.ubicacion ?: "",
            fontSize = 13.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 2.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Descripción
        Text(
            text = uiState.evento?.descripcion ?: "",
            fontSize = 14.sp,
            color = Color(0xFF3A3A3A),
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón inscribirse / desinscribirse
        Button(
            onClick = {
                if (uiState.inscrito) onEvent(EventoDentroEvent.OnDesinscribirse)
                else onEvent(EventoDentroEvent.OnInscribirse)
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.inscrito) Color.Gray else GreenBar
            )
        ) {
            Text(
                text = if (uiState.inscrito) "Desinscribirse" else "Inscribirse",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

// ─── Previews ───
private val fakeEvento = Evento(
    id = 1L,
    nombre = "Recogida de basura",
    descripcion = "Únete a nosotros en el evento de recogida de basura en la playa del Postiguer y contribuye a mantener nuestro litoral limpio y saludable. Es una oportunidad para disfrutar del aire libre.",
    fechaHora = LocalDateTime.now(),
    ubicacion = "En la playa del Postiguer",
    imagen = "",
    estado = "Evento comunitario"
)

@Preview(showBackground = true, name = "EventoDentroScreen - no inscrito")
@Composable
fun EventoDentroScreenPreview() {
    EcoQuestMobileTheme {
        EventoDentroScreen(
            eventoId = 1L,
            uiState = EventoDentroUiState(evento = fakeEvento, inscrito = false),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, name = "EventoDentroScreen - inscrito")
@Composable
fun EventoDentroScreenInscritoPreview() {
    EcoQuestMobileTheme {
        EventoDentroScreen(
            eventoId = 1L,
            uiState = EventoDentroUiState(evento = fakeEvento, inscrito = true),
            onEvent = {}
        )
    }
}
