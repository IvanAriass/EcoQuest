package com.ecoquest.app.ui.features.comunidades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ecoquest.app.R
import com.ecoquest.app.models.Comunidad

@Composable
fun ComunidadesGrid(
    comunidades: List<Comunidad>,
    onComunidadClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(comunidades) { comunidad ->
            ComunidadesCard(
                comunidad = comunidad,
                onClick = onComunidadClick
            )
        }
    }
}