package com.ecoquest.app.ui.feature.comunidades.detalle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.model.UsuarioComunidad
import com.ecoquest.app.ui.components.common.RolBadge
import com.ecoquest.app.ui.components.evento.EventoCompactCard
import com.ecoquest.app.ui.components.evento.EventoDialog

@Composable
fun ComunidadDetalleScreen(
    comunidadId: Long,
    uiState: ComunidadDetalleUiState,
    onEvent: (ComunidadDetalleEvent) -> Unit,
    onNavigateToEvento: (Long) -> Unit,
    onNavigateToMiembros: () -> Unit = {},
    onNavigateToChat: () -> Unit = {},
    onNavigateToGestion: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        uiState.comunidad?.let { comunidad ->
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = if (comunidad.imagen.isNotEmpty()) comunidad.imagen else R.drawable.iconoeco,
                    contentDescription = comunidad.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.75f)
                                )
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp)
                ) {
                    Text(
                        text = comunidad.nombre,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.People,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color.White
                            )
                        }
                        Text(
                            text = "${comunidad.usuarios.size} miembros",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset( y = (-8).dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Acerca de",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = comunidad.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (uiState.esMiembro) {
                            uiState.miRolInfo?.let { rol ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Tu rol:",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    RolBadge(rolInfo = rol)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Button(
                                    onClick = { onEvent(ComunidadDetalleEvent.OnAbandonar) },
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier
                                        .height(44.dp)
                                        .weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        "Abandonar",
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Button(
                                    onClick = onNavigateToChat,
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary
                                    ),
                                    modifier = Modifier
                                        .height(44.dp)
                                        .width(44.dp),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Filled.Chat,
                                        contentDescription = "Chat",
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.onTertiary
                                    )
                                }
                                if (uiState.puedeCrearEventos) {
                                    Button(
                                        onClick = { onEvent(ComunidadDetalleEvent.OnMostrarCrearEvento) },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        ),
                                        modifier = Modifier
                                            .height(44.dp)
                                            .width(44.dp),
                                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "Crear evento",
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }
                            }
                        } else {
                            Button(
                                onClick = { onEvent(ComunidadDetalleEvent.OnUnirse) },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(44.dp),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Group,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    "Unirse",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (uiState.miembros.isNotEmpty()) {
                    AnimatedSection(index = 0) {
                        Card(
                            onClick = onNavigateToMiembros,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Filled.People,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Miembros",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${uiState.miembros.size} personas",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Icon(
                                    Icons.Filled.ChevronRight,
                                    contentDescription = "Ver miembros",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }

                    if (uiState.puedeGestionar) {
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = onNavigateToGestion,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.Settings, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Gestionar comunidad")
                        }
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 24.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                }

                AnimatedSection(index = 1) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Eventos",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (uiState.eventos.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${uiState.eventos.size} eventos",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (uiState.eventos.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Filled.CalendarMonth,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                                )
                                Text(
                                    text = "No hay eventos todav\u00eda",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        uiState.eventos.forEach { evento ->
                            EventoCompactCard(
                                evento = evento,
                                onClick = { onNavigateToEvento(evento.id) }
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }

    uiState.dialogo?.let { config ->
        EventoDialog(
            config = config,
            onDismiss = { onEvent(ComunidadDetalleEvent.OnDismissDialog) },
            onConfirm = { nombre, descripcion, fechaHora, ubicacion, imagen ->
                onEvent(ComunidadDetalleEvent.OnGuardarEvento(nombre, descripcion, fechaHora, ubicacion, imagen))
            }
        )
    }
}

@Composable
private fun AnimatedSection(index: Int, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(300 + index * 80)) +
                slideInVertically(
                    animationSpec = tween(300 + index * 80),
                    initialOffsetY = { it / 6 }
                )
    ) {
        Column {
            content()
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComunidadDetallePreview() {
    val sampleComunidad = Comunidad(
        id = 1,
        nombre = "EcoAmigos Valencia",
        descripcion = "Grupo de personas comprometidas con el medio ambiente en la ciudad de Valencia. Organizamos limpiezas de playa, reforestaciones y talleres de reciclaje.",
        imagen = "",
        usuarios = List(15) { UsuarioComunidad(id = it.toLong(), rol = "MIEMBRO") }
    )
    val sampleEventos = listOf(
        Evento(id = 1, nombre = "Limpieza playa", descripcion = "", fechaHora = "20/06 09:00", ubicacion = "Playa Malvarrosa"),
        Evento(id = 2, nombre = "Taller reciclaje", descripcion = "", fechaHora = "25/06 17:00", ubicacion = "Centro C\u00edvico")
    )
    val sampleState = ComunidadDetalleUiState(
        comunidad = sampleComunidad,
        eventos = sampleEventos,
        miembros = List(5) { Usuario(id = it.toLong(), nombre = "Usuario $it", apellido = "Apellido $it") },
        esMiembro = true
    )
    ComunidadDetalleScreen(
        comunidadId = 1,
        uiState = sampleState,
        onEvent = {},
        onNavigateToEvento = {},
        onNavigateToMiembros = {},
        onNavigateToChat = {}
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComunidadDetalleNoMemberPreview() {
    val sampleComunidad = Comunidad(
        id = 2,
        nombre = "Guardianes del Planeta",
        descripcion = "Comunidad global dedicada a la protecci\u00f3n del medio ambiente. \u00danete a nuestras iniciativas!",
        imagen = "",
        usuarios = emptyList()
    )
    val sampleState = ComunidadDetalleUiState(
        comunidad = sampleComunidad,
        eventos = emptyList(),
        miembros = emptyList(),
        esMiembro = false
    )
    ComunidadDetalleScreen(
        comunidadId = 2,
        uiState = sampleState,
        onEvent = {},
        onNavigateToEvento = {},
        onNavigateToMiembros = {},
        onNavigateToChat = {}
    )
}
