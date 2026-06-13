package com.ecoquest.app.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

fun estadoColor(estado: String): Color = when (estado.lowercase()) {
    "noticia" -> Color(0xFFE53935)
    "urgente" -> Color(0xFFFF6F00)
    "cerrado" -> Color(0xFF757575)
    else -> Color(0xFF2E7D32)
}

@Composable
fun StatusBadge(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.ifBlank { "Evento" },
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(estadoColor(text), RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}
