package com.ecoquest.app.ui.feature.tienda

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ecoquest.app.ui.components.general.SectionTitle
import com.ecoquest.app.ui.components.producto.ProductoCard
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme

@Composable
fun TiendaScreen(
    uiState: TiendaUiState,
    onEvent: (TiendaEvent) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        items(uiState.productos) { producto ->
            ProductoCard(
                producto = producto,
                onClick = { }
            )
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
