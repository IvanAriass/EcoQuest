package com.ecoquest.app.ui.feature.tienda

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.components.general.SectionTitle
import com.ecoquest.app.ui.components.producto.ProductoCard
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TiendaScreen(
    uiState: TiendaUiState,
    onEvent: (TiendaEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionTitle(text = "Tienda", Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
        if (uiState.categorias.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = uiState.selectedCategory == null,
                    onClick = { onEvent(TiendaEvent.SelectCategory(null)) },
                    label = { Text("Todas") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                uiState.categorias.forEach { categoria ->
                    FilterChip(
                        selected = uiState.selectedCategory == categoria,
                        onClick = { onEvent(TiendaEvent.SelectCategory(categoria)) },
                        label = { Text(categoria) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.filteredProductos) { producto ->
                ProductoCard(
                    producto = producto,
                    onClick = { }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TiendaScreenPreview() {
    EcoQuestMobileTheme {
        TiendaScreen(uiState = TiendaUiState(), onEvent = {})
    }
}
