package com.ecoquest.app.ui.feature.eventos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.ui.components.evento.EventoCard
import com.ecoquest.app.ui.components.general.SearchBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme

@Composable
fun EventosScreen(
    uiState: EventosUiState,
    onEvent: (EventosEvent) -> Unit,
    onNavigateToEvento: (Long) -> Unit
) {
    var selectedFilter by remember { mutableStateOf(0) }

    val statusFilters = listOf(
        "Todos" to Icons.Filled.CalendarMonth,
        "Eventos" to Icons.AutoMirrored.Filled.EventNote,
        "Noticias" to Icons.Filled.Notifications,
        "Completados" to Icons.Filled.TaskAlt
    )

    val filteredByStatus = if (selectedFilter == 0) uiState.eventosFiltrados
    else {
        val filterMap = mapOf(
            1 to "Evento Comunitario",
            2 to "Noticia",
            3 to "Cerrado"
        )
        val targetStatus = filterMap[selectedFilter]
        if (targetStatus != null) uiState.eventosFiltrados.filter { it.estado == targetStatus }
        else uiState.eventosFiltrados
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 20.dp, bottom = 80.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Eventos",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Icon(
                        imageVector = Icons.Filled.CalendarMonth,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = uiState.textoBusqueda,
                onQueryChange = { onEvent(EventosEvent.OnBusquedaChanged(it)) },
                placeholder = "Buscar eventos",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(statusFilters.size) { index ->
                    val (label, icon) = statusFilters[index]
                    FilterChip(
                        selected = selectedFilter == index,
                        onClick = { selectedFilter = index },
                        leadingIcon = {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        },
                        label = { Text(label) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(filteredByStatus) { evento ->
            EventoCard(evento = evento, onClick = { onNavigateToEvento(evento.id) })
        }

        if (filteredByStatus.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay eventos disponibles",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventosScreenPreview() {
    val fakeEventos = listOf(
        Evento(id = 1L, nombre = "Recogida de Basura", descripcion = "En la playa del Postiguer", fechaHora = "2026-06-15", estado = "Evento Comunitario"),
        Evento(id = 2L, nombre = "Nuevos fondos ODS", descripcion = "Mayor remuneración para proyectos sostenibles", fechaHora = "2026-06-20", estado = "Noticia")
    )
    EcoQuestMobileTheme {
        EventosScreen(uiState = EventosUiState(eventos = fakeEventos), onEvent = {}, onNavigateToEvento = {})
    }
}
