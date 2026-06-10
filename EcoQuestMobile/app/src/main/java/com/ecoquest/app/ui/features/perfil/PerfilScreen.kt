package com.ecoquest.app.ui.features.perfil

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ecoquest.app.models.Comunidad
import com.ecoquest.app.models.Evento
import com.ecoquest.app.models.Usuario
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import com.ecoquest.app.ui.theme.greenPrimary
import java.time.LocalDateTime

@Composable
fun PerfilScreen(
    uiState: PerfilUiState,
    onEvent: (PerfilEvent) -> Unit
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,
                android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            onEvent(PerfilEvent.OnFotoSeleccionada(uri.toString()))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Avatar — pulsar para cambiar foto
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    launcher.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.usuario.imagen.isNotEmpty()) {
                AsyncImage(
                    model = uiState.usuario.imagen,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = uiState.usuario.nombreUsuario.take(1).uppercase(),
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Nombre completo
        Text(
            text = "${uiState.usuario.nombre} ${uiState.usuario.apellido}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E2E2E)
        )

        // Nombre de usuario
        Text(
            text = "@${uiState.usuario.nombreUsuario}",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Comunidades
        ToggleListButton(
            label = "Comunidades",
            expanded = uiState.showComunidades,
            onClick = { onEvent(PerfilEvent.OnToggleComunidades) }
        )

        AnimatedVisibility(visible = uiState.showComunidades) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.comunidades.isEmpty()) {
                    Text(
                        text = "No perteneces a ninguna comunidad",
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    uiState.comunidades.forEach { comunidad ->
                        ComunidadItem(comunidad = comunidad)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Eventos
        ToggleListButton(
            label = "Eventos",
            expanded = uiState.showEventos,
            onClick = { onEvent(PerfilEvent.OnToggleEventos) }
        )

        AnimatedVisibility(visible = uiState.showEventos) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.eventos.isEmpty()) {
                    Text(
                        text = "No participas en ningún evento",
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                } else {
                    uiState.eventos.forEach { evento ->
                        EventoItem(evento = evento)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ToggleListButton(
    label: String,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = GreenBar)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
private fun ComunidadItem(comunidad: Comunidad) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(greenPrimary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = comunidad.nombre.take(1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = greenPrimary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = comunidad.nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = comunidad.descripcion,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun EventoItem(evento: Evento) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GreenBar.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = evento.fechaHora.dayOfMonth.toString(),
                    fontWeight = FontWeight.Bold,
                    color = GreenBar,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = evento.nombre,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
                Text(
                    text = evento.ubicacion,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
    }
}

// ─── Previews ───
private val fakeUsuario = Usuario(
    id = 1L,
    nombreUsuario = "manolo99",
    contraseña = "",
    nombre = "Manolo",
    apellido = "Martinez Alvarez",
    descripcion = "",
    edad = 25,
    email = "manolo@email.com",
    imagen = ""
)

@Preview(showBackground = true, name = "PerfilScreen - vacío")
@Composable
fun PerfilScreenPreview() {
    EcoQuestMobileTheme {
        PerfilScreen(
            uiState = PerfilUiState(usuario = fakeUsuario),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, name = "PerfilScreen - comunidades desplegadas")
@Composable
fun PerfilScreenComunidadesPreview() {
    EcoQuestMobileTheme {
        PerfilScreen(
            uiState = PerfilUiState(
                usuario = fakeUsuario,
                showComunidades = true,
                comunidades = listOf(
                    Comunidad(id = 1L, nombre = "EcoValencia", descripcion = "Sostenibilidad en Valencia", imagen = ""),
                    Comunidad(id = 2L, nombre = "Madrid Sostenible", descripcion = "Iniciativas en Madrid", imagen = "")
                )
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, name = "PerfilScreen - eventos desplegados")
@Composable
fun PerfilScreenEventosPreview() {
    EcoQuestMobileTheme {
        PerfilScreen(
            uiState = PerfilUiState(
                usuario = fakeUsuario,
                showEventos = true,
                eventos = listOf(
                    Evento(id = 1L, nombre = "Limpieza playa", descripcion = "Limpieza de residuos", fechaHora = LocalDateTime.now(), ubicacion = "Valencia", imagen = "", estado = ""),
                    Evento(id = 2L, nombre = "Plantación árboles", descripcion = "Reforestación urbana", fechaHora = LocalDateTime.now(), ubicacion = "Madrid", imagen = "", estado = "")
                )
            ),
            onEvent = {}
        )
    }
}
