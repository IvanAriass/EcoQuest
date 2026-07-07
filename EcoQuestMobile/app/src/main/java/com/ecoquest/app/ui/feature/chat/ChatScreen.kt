package com.ecoquest.app.ui.feature.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.domain.model.Mensaje
import com.ecoquest.app.ui.components.common.RolBadge
import com.ecoquest.app.ui.util.formatearFechaChat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private sealed interface ChatListItem {
    data class DateHeader(val label: String) : ChatListItem
    data class Message(val mensaje: Mensaje) : ChatListItem
}

private fun agruparPorDia(mensajes: List<Mensaje>): List<ChatListItem> {
    val hoy = LocalDate.now()
    val parsers = listOf(
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    )

    fun extraerFecha(fechaHora: String): LocalDate? {
        for (parser in parsers) {
            try {
                return LocalDateTime.parse(fechaHora, parser).toLocalDate()
            } catch (_: DateTimeParseException) {}
        }
        return try {
            LocalDate.parse(fechaHora.take(10), DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (_: Exception) { null }
    }

    val items = mutableListOf<ChatListItem>()
    var ultimaFecha: LocalDate? = null
    for (msg in mensajes) {
        val fecha = extraerFecha(msg.fechaHora) ?: continue
        if (fecha != ultimaFecha) {
            val label = formatearFechaChat(fecha, hoy)
            items.add(ChatListItem.DateHeader(label))
            ultimaFecha = fecha
        }
        items.add(ChatListItem.Message(msg))
    }
    return items
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    uiState: ChatUiState,
    onBack: () -> Unit,
    onTextoCambiado: (String) -> Unit,
    onEnviarMensaje: () -> Unit,
    onNavigateToPerfilUsuario: (Long) -> Unit = {}
) {
    val listState = rememberLazyListState()
    val items = remember(uiState.mensajes) { agruparPorDia(uiState.mensajes) }

    LaunchedEffect(items.size) {
        if (items.isNotEmpty()) {
            listState.animateScrollToItem(items.lastIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Chat",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        RolBadge(rolInfo = uiState.miRol)
                    }
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
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                if (items.isEmpty() && !uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No hay mensajes todav\u00EDa.\n\u00A1S\u00E9 el primero en escribir!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                items(items, key = {
                    when (it) {
                        is ChatListItem.DateHeader -> "hdr_${it.label}"
                        is ChatListItem.Message -> "msg_${it.mensaje.id}"
                    }
                }) { item ->
                    when (item) {
                        is ChatListItem.DateHeader -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item.label,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                        }
                        is ChatListItem.Message -> {
                            MensajeBubble(
                                mensaje = item.mensaje,
                                esPropio = item.mensaje.usuarioId == uiState.usuarioId,
                                onAvatarClick = { onNavigateToPerfilUsuario(item.mensaje.usuarioId) }
                            )
                        }
                    }
                }
            }

            if (!uiState.puedeEscribir) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "\uD83D\uDC4A Como ${uiState.miRol.emoji} ${uiState.miRol.nombre} a\u00FAn no puedes escribir. \u00A1Sigue participando para crecer!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.textoInput,
                    onValueChange = onTextoCambiado,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escribe un mensaje...") },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                    ),
                    enabled = uiState.puedeEscribir,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = { onEnviarMensaje() }),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
                IconButton(
                    onClick = onEnviarMensaje,
                    enabled = uiState.puedeEscribir && uiState.textoInput.isNotBlank(),
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = if (uiState.puedeEscribir && uiState.textoInput.isNotBlank())
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Enviar",
                        tint = if (uiState.puedeEscribir && uiState.textoInput.isNotBlank())
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun MensajeBubble(
    mensaje: Mensaje,
    esPropio: Boolean,
    onAvatarClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (esPropio) Arrangement.End else Arrangement.Start
    ) {
        if (!esPropio) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clickable(onClick = onAvatarClick),
                contentAlignment = Alignment.Center
            ) {
                if (mensaje.usuarioImagen.isNotBlank()) {
                    AsyncImage(
                        model = mensaje.usuarioImagen,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = mensaje.usuarioNombre.take(2).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(RoundedCornerShape(
                    topStart = if (esPropio) 16.dp else 4.dp,
                    topEnd = if (esPropio) 4.dp else 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ))
                .background(
                    if (esPropio) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                )
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            if (!esPropio) {
                Text(
                    text = "@${mensaje.usuarioNombreUsuario}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
            Text(
                text = mensaje.texto,
                style = MaterialTheme.typography.bodyMedium,
                color = if (esPropio) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = mensaje.fechaHora.take(16).drop(11).take(5),
                style = MaterialTheme.typography.labelSmall,
                color = if (esPropio) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
