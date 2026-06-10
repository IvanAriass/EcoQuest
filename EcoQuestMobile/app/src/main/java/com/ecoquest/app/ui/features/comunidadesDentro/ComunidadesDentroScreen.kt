package com.ecoquest.app.ui.features.comunidadesDentro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.CommunityMode
import com.ecoquest.app.R
import com.ecoquest.app.models.Comunidad
import com.ecoquest.app.ui.features.comunidades.dialogos.EditarComunidadDialog
import com.pmdm.proyectobase.ui.features.comunidadesDentro.CrearEventoDialog
import com.ecoquest.app.ui.features.comunidadesDentro.EditarEventoDialog
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme

@Composable
fun ComunidadesDentroScreen(
    comunidadId: Long,
    uiState: ComunidadesDentroUiState,
    onEvent: (ComunidadesDentroEvent) -> Unit,
    onNavigateToEvento: (Long) -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = uiState.comunidad?.nombre ?: "Comunidad",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E2E2E)
                )

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.iconoeco),
                        modifier = Modifier.size(40.dp),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }

            Text(
                text = uiState.comunidad?.descripcion ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF004D40),
                lineHeight = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = "Lista eventos",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            // Lista de eventos
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                uiState.eventos.forEach { evento ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onNavigateToEvento(evento.id) }
                        ) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.playa),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Text(
                                    text = evento.nombre,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        if (evento.creadorId == 1L) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.45f))
                            ) {
                                IconButton(
                                    onClick = { onEvent(ComunidadesDentroEvent.OnEditEventoClick(evento.id)) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Editar evento",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // FAB con menú desplegable
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 16.dp)
        ) {
            FloatingActionButton(
                onClick = { menuExpanded = true },
                containerColor = Color.White,
                contentColor = GreenBar
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Crear evento") },
                    onClick = {
                        menuExpanded = false
                        onEvent(ComunidadesDentroEvent.OnAddEventoClick)
                    }
                )
                if (uiState.esCreador) {
                    DropdownMenuItem(
                        text = { Text("Editar comunidad") },
                        onClick = {
                            menuExpanded = false
                            onEvent(ComunidadesDentroEvent.OnEditComunidadClick)
                        }
                    )
                }
            }
        }

        if (uiState.dialogMode == CommunityMode.EDIT) {
            EditarComunidadDialog(
                nombreInicial = uiState.comunidad?.nombre ?: "",
                descripcionInicial = uiState.comunidad?.descripcion ?: "",
                onConfirm = { nombre, descripcion ->
                    onEvent(ComunidadesDentroEvent.OnConfirmEditComunidad(nombre, descripcion))
                },
                onDismiss = { onEvent(ComunidadesDentroEvent.OnDismissDialog) }
            )
        }

        if (uiState.dialogMode == CommunityMode.CREATE) {
            CrearEventoDialog(
                onDismiss = { onEvent(ComunidadesDentroEvent.OnDismissDialog) },
                onConfirm = { nombre, descripcion, fechaHora ->
                    onEvent(ComunidadesDentroEvent.OnCreateEvento(nombre, descripcion, fechaHora))
                }
            )
        }

        if (uiState.dialogMode == CommunityMode.EDIT_EVENT && uiState.eventoEnEdicion != null) {
            EditarEventoDialog(
                nombreInicial = uiState.eventoEnEdicion.nombre,
                descripcionInicial = uiState.eventoEnEdicion.descripcion,
                fechaInicialISO = uiState.eventoEnEdicion.fechaHora.toString(),
                onDismiss = { onEvent(ComunidadesDentroEvent.OnDismissDialog) },
                onConfirm = { nombre, descripcion, fechaHora ->
                    onEvent(ComunidadesDentroEvent.OnConfirmEditEvento(nombre, descripcion, fechaHora))
                }
            )
        }
    }
}

// ─── Previews ───
private val fakeComunidad = Comunidad(
    id = 1L,
    nombre = "Todos Unidos",
    imagen = "",
    descripcion = "Somos la Comunidad Eco-Conexión, y ¡acabamos de aterrizar en esta aplicación!"
)

@Preview(showBackground = true, name = "ComunidadesDentroScreen - normal")
@Composable
fun ComunidadesDentroScreenPreview() {
    EcoQuestMobileTheme {
        ComunidadesDentroScreen(
            comunidadId = 1L,
            uiState = ComunidadesDentroUiState(comunidad = fakeComunidad),
            onEvent = {},
            onNavigateToEvento = {}
        )
    }
}

@Preview(showBackground = true, name = "ComunidadesDentroScreen - diálogo editar")
@Composable
fun ComunidadesDentroScreenEditarPreview() {
    EcoQuestMobileTheme {
        ComunidadesDentroScreen(
            comunidadId = 1L,
            uiState = ComunidadesDentroUiState(
                comunidad = fakeComunidad,
                dialogMode = CommunityMode.EDIT
            ),
            onEvent = {},
            onNavigateToEvento = {}
        )
    }
}

@Preview(showBackground = true, name = "ComunidadesDentroScreen - diálogo crear evento")
@Composable
fun ComunidadesDentroScreenCrearEventoPreview() {
    EcoQuestMobileTheme {
        ComunidadesDentroScreen(
            comunidadId = 1L,
            uiState = ComunidadesDentroUiState(
                comunidad = fakeComunidad,
                dialogMode = CommunityMode.CREATE
            ),
            onEvent = {},
            onNavigateToEvento = {}
        )
    }
}