package com.ecoquest.app.ui.feature.perfil

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.model.UsuarioCosmetico
import com.ecoquest.app.ui.components.comunidad.ComunidadCard
import com.ecoquest.app.ui.components.evento.EventoCard
import com.ecoquest.app.ui.theme.GradientEnd
import com.ecoquest.app.ui.theme.GradientStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(
    uiState: PerfilUsuarioUiState,
    onBack: () -> Unit,
    onToggleComunidades: () -> Unit,
    onToggleEventos: () -> Unit,
    onToggleRetos: () -> Unit,
    onTogglePuntos: () -> Unit,
    onNavigateToEventos: () -> Unit = {},
    onNavigateToComunidades: () -> Unit = {},
    onNavigateToRetos: () -> Unit = {},
    onNavigateToComunidad: (Int) -> Unit = {},
    onNavigateToEvento: (Long) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            val cosmeticosAplicados = uiState.cosmeticos.filter { it.aplicado }

            PerfilUsuarioHeader(
                usuario = uiState.usuario,
                cosmeticosAplicados = cosmeticosAplicados
            )

            Spacer(modifier = Modifier.height(24.dp))

            StatsGrid(
                saldoPuntos = uiState.saldoPuntos,
                comunidadesCount = uiState.comunidades.size,
                eventosCount = uiState.eventos.size,
                retosCount = uiState.retos.size,
                onPuntosClick = onNavigateToRetos,
                onComunidadesClick = onNavigateToComunidades,
                onEventosClick = onNavigateToEventos,
                onRetosClick = onNavigateToRetos
            )

            Spacer(modifier = Modifier.height(24.dp))

            ExpandableSection(
                label = "Comunidades",
                icon = Icons.Filled.Groups,
                count = uiState.comunidades.size,
                isExpanded = uiState.showComunidades,
                onClick = onToggleComunidades
            ) {
                if (uiState.comunidades.isEmpty()) {
                    Text(
                        text = "No pertenece a ninguna comunidad",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    uiState.comunidades.forEach { comunidad ->
                        ComunidadCard(
                            comunidad = comunidad,
                            onClick = { onNavigateToComunidad(comunidad.id.toInt()) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExpandableSection(
                label = "Eventos",
                icon = Icons.Filled.Event,
                count = uiState.eventos.size,
                isExpanded = uiState.showEventos,
                onClick = onToggleEventos
            ) {
                if (uiState.eventos.isEmpty()) {
                    Text(
                        text = "No participa en ning\u00fan evento",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    uiState.eventos.forEach { evento ->
                        EventoCard(
                            evento = evento,
                            onClick = { onNavigateToEvento(evento.id) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExpandableSection(
                label = "Retos Completados",
                icon = Icons.Filled.Star,
                count = uiState.retos.size,
                isExpanded = uiState.showRetos,
                onClick = onToggleRetos
            ) {
                if (uiState.retos.isEmpty()) {
                    Text(
                        text = "No hay retos disponibles",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    uiState.retos.forEach { reto ->
                        RetoCompactRow(reto = reto)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            ExpandableSection(
                label = "Historial de Puntos",
                icon = Icons.Filled.History,
                count = uiState.transacciones.size,
                isExpanded = uiState.showPuntos,
                onClick = onTogglePuntos
            ) {
                if (uiState.transacciones.isEmpty()) {
                    Text(
                        text = "No hay transacciones registradas",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    uiState.transacciones.take(10).forEach { transaccion ->
                        TransaccionRow(transaccion = transaccion)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PerfilUsuarioHeader(
    usuario: Usuario,
    cosmeticosAplicados: List<UsuarioCosmetico> = emptyList()
) {
    val marcoCosmetico = cosmeticosAplicados.firstOrNull { it.productoTipo == "MARCO" }
    val temaCosmetico = cosmeticosAplicados.firstOrNull { it.productoTipo == "TEMA" }
    val insigniaCosmetico = cosmeticosAplicados.firstOrNull { it.productoTipo == "INSIGNIA" }
    val estiloCosmetico = cosmeticosAplicados.firstOrNull { it.productoTipo == "ESTILO_NOMBRE" }

    val marcoVisual = remember(marcoCosmetico) { marcoCosmetico?.let { getCosmeticoVisual(it.productoId).marco } }
    val temaVisual = remember(temaCosmetico) { temaCosmetico?.let { getCosmeticoVisual(it.productoId).tema } }
    val insigniaVisual = remember(insigniaCosmetico) { insigniaCosmetico?.let { getCosmeticoVisual(it.productoId).insignia } }
    val estiloVisual = remember(estiloCosmetico) { estiloCosmetico?.let { getCosmeticoVisual(it.productoId).estiloNombre } }

    val cardBrush = remember(temaVisual) {
        temaVisual?.let { Brush.verticalGradient(it.gradient) }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = if (cardBrush == null) CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ) else CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (cardBrush != null) Modifier.background(cardBrush, RoundedCornerShape(24.dp))
                    else Modifier
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(if (marcoVisual != null) 120.dp + marcoVisual.borderWidth * 2 else 120.dp)
                        .clip(CircleShape)
                        .then(
                            if (marcoVisual != null) Modifier.background(
                                Brush.sweepGradient(colors = marcoVisual.colors)
                            )
                            else Modifier.background(
                                Brush.sweepGradient(colors = listOf(GradientStart, GradientEnd))
                            )
                        )
                        .padding(if (marcoVisual != null) marcoVisual.borderWidth else 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(
                                Brush.sweepGradient(colors = listOf(GradientStart, GradientEnd))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (usuario.imagen.isNotEmpty()) {
                            AsyncImage(
                                model = usuario.imagen,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text(
                                text = usuario.nombreUsuario.take(1).uppercase(),
                                style = MaterialTheme.typography.displayLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${usuario.nombre} ${usuario.apellido}",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = if (cardBrush == null) MaterialTheme.colorScheme.onPrimaryContainer
                        else Color.White
                    )
                    if (insigniaVisual != null) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(insigniaVisual.backgroundColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = insigniaVisual.icono,
                                contentDescription = "Insignia",
                                modifier = Modifier.size(16.dp),
                                tint = insigniaVisual.iconTint
                            )
                        }
                    }
                }

                if (estiloVisual?.brush != null) {
                    Text(
                        text = "@${usuario.nombreUsuario}",
                        style = MaterialTheme.typography.bodyMedium.copy(brush = estiloVisual.brush)
                    )
                } else {
                    Text(
                        text = "@${usuario.nombreUsuario}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (cardBrush == null) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                        else Color.White.copy(alpha = 0.85f)
                    )
                }

                if (usuario.descripcion.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.Info,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (cardBrush == null) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            else Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = usuario.descripcion,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (cardBrush == null) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            else Color.White.copy(alpha = 0.9f),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (usuario.email.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = if (cardBrush == null) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                            else Color.White.copy(alpha = 0.7f)
                        )
                        Text(
                            text = usuario.email,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (cardBrush == null) MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                            else Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsGrid(
    saldoPuntos: Int,
    comunidadesCount: Int,
    eventosCount: Int,
    retosCount: Int,
    onPuntosClick: () -> Unit = {},
    onComunidadesClick: () -> Unit = {},
    onEventosClick: () -> Unit = {},
    onRetosClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                icon = Icons.Filled.Eco,
                value = "$saldoPuntos",
                label = "Puntos Eco",
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                iconTint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                onClick = onPuntosClick
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
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                icon = Icons.Filled.Event,
                value = "$eventosCount",
                label = "Eventos",
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                iconTint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f),
                onClick = onEventosClick
            )
            StatCard(
                icon = Icons.Filled.Star,
                value = "$retosCount",
                label = "Retos",
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                iconTint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
                onClick = onRetosClick
            )
        }
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
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(containerColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
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

@Composable
private fun ExpandableSection(
    label: String,
    icon: ImageVector,
    count: Int,
    isExpanded: Boolean,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val chevronRotation by animateFloatAsState(
        targetValue = if (isExpanded) 90f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "chevronRotation"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onClick
    ) {
        Column {
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
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "$count",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = if (isExpanded) "Contraer" else "Expandir",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(chevronRotation)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                ) + fadeIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ),
                exit = shrinkVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                ) + fadeOut(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
private fun RetoCompactRow(reto: Reto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.TaskAlt,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = reto.nombre,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = reto.descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${reto.puntos} pts",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun TransaccionRow(transaccion: TransaccionPuntos) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaccion.concepto,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = transaccion.fecha,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = "${if (transaccion.tipo == "GANADO") "+" else "-"}${transaccion.puntos}",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = if (transaccion.tipo == "GANADO")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.error
        )
    }
}
