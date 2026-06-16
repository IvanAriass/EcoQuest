package com.ecoquest.app.ui.feature.tienda

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.domain.model.Producto
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${uiState.saldoPuntos} pts",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val filterItems = listOf(null as String?) + uiState.tipos
        val filterLabels = listOf("Todas") + uiState.tipos.map { tipo ->
            when (tipo) {
                "MARCO" -> "Marcos"
                "TEMA" -> "Temas"
                "INSIGNIA" -> "Insignias"
                "ESTILO_NOMBRE" -> "Estilos"
                else -> tipo
            }
        }

        if (filterItems.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterItems.forEachIndexed { index, tipo ->
                    val selected = if (tipo == null) uiState.selectedTipo == null
                    else uiState.selectedTipo == tipo
                    FilterChip(
                        selected = selected,
                        onClick = {
                            onEvent(if (tipo == null) TiendaEvent.SelectTipo(null)
                            else TiendaEvent.SelectTipo(tipo))
                        },
                        label = { Text(filterLabels[index]) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

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
                    onClick = { onEvent(TiendaEvent.OnProductoClick(producto)) }
                )
            }
        }
    }

    uiState.productoSeleccionado?.let { producto ->
        DetalleProductoDialog(
            producto = producto,
            saldoPuntos = uiState.saldoPuntos,
            puedeCanjear = uiState.saldoPuntos >= producto.precio,
            onCerrar = { onEvent(TiendaEvent.OnCerrarDetalle) },
            onCanjear = { onEvent(TiendaEvent.OnConfirmarCanje) }
        )
    }

    if (uiState.canjeExitoso) {
        AlertDialog(
            onDismissRequest = { onEvent(TiendaEvent.OnCanjeConsumido) },
            icon = {
                Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp))
            },
            title = {
                Text("¡Canje exitoso!",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            },
            text = {
                Text("El cosmético se ha añadido a tu colección. Puedes aplicarlo desde tu perfil.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            },
            confirmButton = {
                TextButton(onClick = { onEvent(TiendaEvent.OnCanjeConsumido) }) {
                    Text("Aceptar")
                }
            }
        )
    }

    uiState.canjeError?.let { error ->
        AlertDialog(
            onDismissRequest = { onEvent(TiendaEvent.OnErrorConsumido) },
            icon = {
                Icon(Icons.Filled.Close, contentDescription = null, tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp))
            },
            title = {
                Text("Error", style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            },
            text = {
                Text(error, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            },
            confirmButton = {
                TextButton(onClick = { onEvent(TiendaEvent.OnErrorConsumido) }) {
                    Text("Aceptar")
                }
            }
        )
    }
}

@Composable
private fun DetalleProductoDialog(
    producto: Producto,
    saldoPuntos: Int,
    puedeCanjear: Boolean,
    onCerrar: () -> Unit,
    onCanjear: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCerrar,
        shape = RoundedCornerShape(24.dp),
        title = {
            Text(producto.nombre, style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold)
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = if (producto.imagen.isNotEmpty()) producto.imagen else R.drawable.iconoeco,
                        contentDescription = producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (producto.tipo.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = producto.tipoLabel,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = producto.descripcion.ifBlank { "Personaliza tu perfil con este exclusivo cosmético" },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Eco, contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${producto.precio} pts",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tu saldo: $saldoPuntos pts",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (puedeCanjear) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.error
                )

                if (!puedeCanjear) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "No tienes suficientes puntos",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = onCanjear,
                enabled = puedeCanjear
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Canjear")
            }
        },
        dismissButton = {
            TextButton(onClick = onCerrar) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TiendaScreenPreview() {
    EcoQuestMobileTheme {
        TiendaScreen(uiState = TiendaUiState(), onEvent = {})
    }
}
