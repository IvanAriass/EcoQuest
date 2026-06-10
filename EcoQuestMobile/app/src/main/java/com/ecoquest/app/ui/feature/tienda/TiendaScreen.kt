package com.ecoquest.app.ui.feature.tienda

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.domain.model.Producto
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import com.ecoquest.app.ui.theme.GreenBar

@Composable
fun TiendaScreen(
    uiState: TiendaUiState,
    onEvent: (TiendaEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tienda",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C),
            textDecoration = TextDecoration.Underline
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(ProductoEjemplo.lista) { producto ->
                ProductoCard(producto = producto)
            }
        }
    }
}

@Composable
private fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = producto.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = producto.descripcion, fontSize = 12.sp, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${producto.precio} pts", fontWeight = FontWeight.Bold, color = GreenBar, fontSize = 16.sp)
        }
    }
}

private object ProductoEjemplo {
    val lista = listOf(
        Producto(id = 1L, nombre = "Planta", descripcion = "Planta decorativa", precio = 100),
        Producto(id = 2L, nombre = "Maceta", descripcion = "Maceta ecológica", precio = 50),
        Producto(id = 3L, nombre = "Semillas", descripcion = "Paquete de semillas", precio = 30),
        Producto(id = 4L, nombre = "Compost", descripcion = "Abono natural", precio = 80)
    )
}

@Preview(showBackground = true)
@Composable
fun TiendaScreenPreview() {
    EcoQuestMobileTheme {
        TiendaScreen(uiState = TiendaUiState(), onEvent = {})
    }
}
