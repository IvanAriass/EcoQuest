package com.ecoquest.app.ui.feature.home.contenido

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Juego
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.ui.components.comunidad.ComunidadCard
import com.ecoquest.app.ui.components.evento.EventoCardVertical
import com.ecoquest.app.ui.components.general.SectionHeaderWithAction
import com.ecoquest.app.ui.theme.GradientEnd
import com.ecoquest.app.ui.theme.GradientStart

@Composable
fun HomeContentScreen(
    uiState: HomeContentUiState,
    onEvent: (HomeContentEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        HeaderSection(
            usuario = uiState.usuario,
            onClick = { onEvent(HomeContentEvent.OnNavigateToPerfil) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        StatsRow(
            eventosCount = uiState.proximosEventos.size,
            comunidadesCount = uiState.comunidades.size,
            saldoPuntos = uiState.saldoPuntos,
            onEventosClick = { onEvent(HomeContentEvent.OnNavigateToEventos) },
            onComunidadesClick = { onEvent(HomeContentEvent.OnNavigateToComunidades) },
            onPuntosClick = { onEvent(HomeContentEvent.OnNavigateToRetos) }
        )

        Spacer(modifier = Modifier.height(28.dp))

        if (uiState.proximosEventos.isNotEmpty()) {
            SectionHeaderWithAction(
                title = "Tus pr\u00f3ximos eventos",
                onAction = { onEvent(HomeContentEvent.OnNavigateToEventos) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                contentPadding = PaddingValues(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.proximosEventos, key = { it.id }) { evento ->
                    EventoCardVertical(
                        evento = evento,
                        onClick = { onEvent(HomeContentEvent.OnNavigateToEvento(evento.id)) }
                    )
                }
            }
        }

        if (uiState.comunidades.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeaderWithAction(
                title = "Tus comunidades",
                onAction = { onEvent(HomeContentEvent.OnNavigateToComunidades) }
            )
            Spacer(modifier = Modifier.height(12.dp))
            LazyRow(
                modifier = Modifier.height(280.dp),
                contentPadding = PaddingValues(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.comunidades, key = { it.id }) { comunidad ->
                    ComunidadCard(
                        comunidad = comunidad,
                        onClick = { onEvent(HomeContentEvent.OnNavigateToComunidad(comunidad.id)) },
                        modifier = Modifier.width(220.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        SectionHeaderWithAction(
            title = "Mini juegos",
            onAction = { onEvent(HomeContentEvent.OnNavigateToJuegos) }
        )
        Spacer(modifier = Modifier.height(12.dp))
        JuegosHorizontalRow(
            onJuegoClick = { juegoId -> onEvent(HomeContentEvent.OnNavigateToJuego(juegoId)) }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun JuegosHorizontalRow(onJuegoClick: (Long) -> Unit) {
    val juegos = listOf(
        Juego(
            id = 1, nombre = "Eco-Quiz",
            descripcion = "Preguntas sobre el medio ambiente",
            icono = "Quiz", colorHex = "#4CAF50"
        ),
        Juego(
            id = 2, nombre = "Memorama Verde",
            descripcion = "Encuentra los pares ecol\u00f3gicos",
            icono = "Extension", colorHex = "#FF9800"
        ),
        Juego(
            id = 3, nombre = "Ahorra Energ\u00eda",
            descripcion = "H\u00e1bitos sostenibles en tu hogar",
            icono = "SmartToy", colorHex = "#2196F3"
        ),
        Juego(
            id = 4, nombre = "Eco-Wordle",
            descripcion = "Adivina la palabra ecol\u00f3gica",
            icono = "Edit", colorHex = "#9C27B0"
        ),
    )

    LazyRow(
        contentPadding = PaddingValues(end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(juegos, key = { it.id }) { juego ->
            JuegoHorizontalCard(juego = juego, onClick = { onJuegoClick(juego.id) })
        }
    }
}

@Composable
private fun JuegoHorizontalCard(juego: Juego, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.width(200.dp).height(160.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Color(android.graphics.Color.parseColor(juego.colorHex)).copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = iconoJuego(juego.icono),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(android.graphics.Color.parseColor(juego.colorHex))
                )
            }
            Column {
                Text(
                    text = juego.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = juego.descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

private fun iconoJuego(icono: String): ImageVector = when (icono) {
    "Quiz" -> Icons.Filled.Quiz
    "Extension" -> Icons.Filled.Extension
    "SmartToy" -> Icons.Filled.SmartToy
    "Edit" -> Icons.Filled.Edit
    else -> Icons.Filled.Star
}

@Composable
private fun HeaderSection(usuario: Usuario, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.sweepGradient(
                            colors = listOf(GradientStart, GradientEnd)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (usuario.imagen.isNotEmpty()) {
                    AsyncImage(
                        model = usuario.imagen,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = usuario.nombreUsuario.take(1).uppercase(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "\u00a1Hola, ${usuario.nombre.takeIf { it.isNotBlank() } ?: usuario.nombreUsuario}!",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Eco,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Eco-Guerrero",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun StatsRow(
    eventosCount: Int,
    comunidadesCount: Int,
    saldoPuntos: Int,
    onEventosClick: () -> Unit,
    onComunidadesClick: () -> Unit,
    onPuntosClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            icon = Icons.Filled.CalendarMonth,
            value = "$eventosCount",
            label = "Eventos",
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            iconTint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.weight(1f),
            onClick = onEventosClick
        )
        StatCard(
            icon = Icons.Filled.Groups,
            value = "$comunidadesCount",
            label = "Comunidades",
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            iconTint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.weight(1f),
            onClick = onComunidadesClick
        )
        StatCard(
            icon = Icons.Filled.Eco,
            value = "$saldoPuntos",
            label = "Puntos Eco",
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            iconTint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f),
            onClick = onPuntosClick
        )
    }
}

@Composable
private fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    containerColor: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = if (onClick != null) modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        ) else modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}




