package com.ecoquest.app.ui.feature.comunidades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.components.general.SearchBar
import com.ecoquest.app.ui.components.general.SectionTitle
import com.ecoquest.app.ui.feature.comunidades.dialogo.CrearComunidadDialog
import com.ecoquest.app.ui.feature.comunidades.dialogo.EditarComunidadDialog

@Composable
fun ComunidadesScreen(
    uiState: ComunidadesUiState,
    onEvent: (ComunidadesEvent) -> Unit
) {
    val comunidadesFiltradas = if (uiState.textoBusqueda.isBlank()) uiState.comunidades
    else uiState.comunidades.filter {
        it.nombre.contains(uiState.textoBusqueda, ignoreCase = true) ||
        it.descripcion.contains(uiState.textoBusqueda, ignoreCase = true)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 80.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            SectionTitle(text = "Comunidades")
            Spacer(modifier = Modifier.height(16.dp))
            SearchBar(
                query = uiState.textoBusqueda,
                onQueryChange = { onEvent(ComunidadesEvent.OnBusquedaChanged(it)) },
                placeholder = "Buscar comunidades",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(comunidadesFiltradas) { comunidad ->
            ComunidadesCard(
                comunidad = comunidad,
                onClick = { onEvent(ComunidadesEvent.OnComunidadClick(comunidad.id.toInt())) }
            )
        }

        item {
            ComunidadesGrid(
                onClick = { onEvent(ComunidadesEvent.OnCrearComunidad) }
            )
        }
    }

    if (uiState.showCrearDialog) {
        CrearComunidadDialog(
            onDismiss = { onEvent(ComunidadesEvent.OnDismissDialog) },
            onConfirm = { nombre, descripcion, imagen ->
                onEvent(ComunidadesEvent.OnGuardarComunidad(nombre, descripcion, imagen))
            }
        )
    }

    if (uiState.showEditarDialog && uiState.comunidadAEditar != null) {
        EditarComunidadDialog(
            comunidad = uiState.comunidadAEditar!!,
            onDismiss = { onEvent(ComunidadesEvent.OnDismissDialog) },
            onConfirm = { nombre, descripcion, imagen ->
                onEvent(ComunidadesEvent.OnEditarComunidad(
                    uiState.comunidadAEditar!!.id, nombre, descripcion, imagen
                ))
            }
        )
    }

    FloatingActionButton(
        onClick = { onEvent(ComunidadesEvent.OnCrearComunidad) },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Crear comunidad")
    }
}
