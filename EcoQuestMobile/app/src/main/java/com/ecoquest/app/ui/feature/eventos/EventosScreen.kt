package com.ecoquest.app.ui.feature.eventos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.ui.components.evento.EventoCard
import com.ecoquest.app.ui.components.general.SearchBar
import com.ecoquest.app.ui.components.general.SectionTitle
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme

@Composable
fun EventosScreen(
    uiState: EventosUiState,
    onEvent: (EventosEvent) -> Unit,
    onNavigateToEvento: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            SectionTitle(text = "Eventos")
            Spacer(modifier = Modifier.height(12.dp))
            SearchBar(
                query = uiState.textoBusqueda,
                onQueryChange = { onEvent(EventosEvent.OnBusquedaChanged(it)) },
                placeholder = "Buscar evento"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(uiState.eventosFiltrados) { evento ->
            EventoCard(evento = evento, onClick = { onNavigateToEvento(evento.id) })
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
