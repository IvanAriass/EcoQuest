package com.ecoquest.app.ui.features.tienda

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.models.Producto
import com.ecoquest.app.ui.theme.GreenBar
import com.ecoquest.app.ui.theme.EcoQuestMobileTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TiendaScreen(
    uiState: TiendaUiState,
    onEvent: (TiendaEvent) -> Unit
) {
    val puntosTexto = remember(uiState.puntos) {
        NumberFormat.getNumberInstance(Locale.US).format(uiState.puntos)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Tienda de Puntos",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        HeroSection(
            puntosTexto = puntosTexto,
            onCanjear = { onEvent(TiendaEvent.OnCanjearClick) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Puntos Destacados",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1C1C),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (uiState.productosDestacados.isNotEmpty()) {
            ProductosDestacadosCard(
                productos = uiState.productosDestacados,
                onProductoClick = { id -> onEvent(TiendaEvent.OnProductoClick(id)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿Cómo se consiguen los puntos?",
            fontSize = 14.sp,
            color = Color(0xFF388E3C),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clickable { onEvent(TiendaEvent.OnComoCunseguirPuntosClick) }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun HeroSection(
    puntosTexto: String,
    onCanjear: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(230.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF9FBE7),
                        Color(0xFFDCEDC8)
                    )
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Hill layer 1 — back, lightest green
            val hill1 = Path().apply {
                moveTo(0f, h * 0.60f)
                cubicTo(w * 0.1f, h * 0.44f, w * 0.35f, h * 0.50f, w * 0.5f, h * 0.52f)
                cubicTo(w * 0.65f, h * 0.54f, w * 0.88f, h * 0.46f, w, h * 0.57f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(hill1, Color(0xFF81C784))

            // Hill layer 2 — medium green
            val hill2 = Path().apply {
                moveTo(0f, h * 0.70f)
                cubicTo(w * 0.12f, h * 0.56f, w * 0.38f, h * 0.60f, w * 0.55f, h * 0.63f)
                cubicTo(w * 0.72f, h * 0.66f, w * 0.9f, h * 0.57f, w, h * 0.67f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(hill2, Color(0xFF4CAF50))

            // Hill layer 3 — dark green
            val hill3 = Path().apply {
                moveTo(0f, h * 0.80f)
                cubicTo(w * 0.2f, h * 0.67f, w * 0.5f, h * 0.72f, w * 0.72f, h * 0.75f)
                cubicTo(w * 0.86f, h * 0.77f, w * 0.95f, h * 0.72f, w, h * 0.76f)
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(hill3, Color(0xFF388E3C))

            // Hill layer 4 — front, darkest
            val hill4 = Path().apply {
                moveTo(0f, h)
                cubicTo(w * 0.12f, h * 0.84f, w * 0.32f, h * 0.87f, w * 0.5f, h * 0.89f)
                cubicTo(w * 0.68f, h * 0.91f, w * 0.88f, h * 0.86f, w, h * 0.86f)
                lineTo(w, h)
                close()
            }
            drawPath(hill4, Color(0xFF2E7D32))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Tienes",
                fontSize = 22.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF4A6741)
            )
            Text(
                text = puntosTexto,
                fontSize = 54.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B4332)
            )
            Text(
                text = "puntos",
                fontSize = 16.sp,
                color = Color(0xFF4A6741)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onCanjear,
                colors = ButtonDefaults.buttonColors(containerColor = GreenBar),
                shape = RoundedCornerShape(24.dp),
                contentPadding = PaddingValues(horizontal = 36.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Canjear",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun ProductosDestacadosCard(
    productos: List<Producto>,
    onProductoClick: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(vertical = 4.dp)) {
            productos.forEach { producto ->
                ProductoItem(
                    producto = producto,
                    onClick = { onProductoClick(producto.id) }
                )
            }
        }
    }
}

@Composable
private fun ProductoItem(
    producto: Producto,
    onClick: () -> Unit
) {
    val precioPts = remember(producto.precio) {
        NumberFormat.getNumberInstance(Locale.US).format(producto.precio)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = Color(0xFFFFF3E0),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = producto.descripcion.ifBlank { "🎁" },
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = producto.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1C1C1C)
            )
            Text(
                text = "$precioPts pts",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

// ─── Previews ───

@Preview(showBackground = true, name = "TiendaScreen - con datos")
@Composable
fun TiendaScreenPreview() {
    EcoQuestMobileTheme {
        TiendaScreen(
            uiState = TiendaUiState(
                puntos = 1250,
                productosDestacados = listOf(
                    Producto(id = 1L, nombre = "Regalo Misterioso", descripcion = "🎁", precio = 1000)
                )
            ),
            onEvent = {}
        )
    }
}

@Preview(showBackground = true, name = "TiendaScreen - sin productos")
@Composable
fun TiendaScreenVaciaPreview() {
    EcoQuestMobileTheme {
        TiendaScreen(
            uiState = TiendaUiState(puntos = 0, productosDestacados = emptyList()),
            onEvent = {}
        )
    }
}
