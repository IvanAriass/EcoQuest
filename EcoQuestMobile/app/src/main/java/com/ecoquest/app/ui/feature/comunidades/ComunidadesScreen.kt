package com.ecoquest.app.ui.feature.comunidades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.components.comunidad.ComunidadCard
import com.ecoquest.app.ui.components.comunidad.ComunidadDialog
import com.ecoquest.app.ui.components.comunidad.CrearComunidadCard
import com.ecoquest.app.ui.components.general.SearchBar
import com.ecoquest.app.ui.components.general.SectionTitle

@Composable
fun ComunidadesScreen(
    uiState: ComunidadesUiState,
    onEvent: (ComunidadesEvent) -> Unit
) {
    val comunidades = uiState.comunidadesFiltradas
    val rows = comunidades.chunked(2)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 80.dp)
    ) {
        item {
            SectionTitle(text = "Comunidades")
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = uiState.textoBusqueda,
                onQueryChange = { onEvent(ComunidadesEvent.OnBusquedaChanged(it)) },
                placeholder = "Buscar comunidades",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        items(rows, key = { it.firstOrNull()?.id ?: 0L }) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { comunidad ->
                    ComunidadCard(
                        comunidad = comunidad,
                        onClick = { onEvent(ComunidadesEvent.OnComunidadClick(comunidad.id.toInt())) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowItems.size < 2) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        item {
            CrearComunidadCard(
                onClick = { onEvent(ComunidadesEvent.OnCrearComunidad) }
            )
        }
    }

    uiState.dialogo?.let { config ->
        ComunidadDialog(
            config = config,
            onDismiss = { onEvent(ComunidadesEvent.OnDismissDialog) },
            onConfirm = { nombre, descripcion, imagen ->
                onEvent(ComunidadesEvent.OnGuardarComunidad(nombre, descripcion, imagen))
            }
        )
    }
}
