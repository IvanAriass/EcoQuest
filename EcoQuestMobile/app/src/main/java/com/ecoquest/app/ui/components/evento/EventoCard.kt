package com.ecoquest.app.ui.components.evento

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.ui.theme.GreenBar

@Composable
fun EventoCard(
    evento: Evento,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(colorBorde(evento.estado))
            )

            Spacer(modifier = Modifier.width(10.dp))

            AsyncImage(
                model = if (evento.imagen.isNotEmpty()) evento.imagen else R.drawable.iconoeco,
                contentDescription = null,
                modifier = Modifier.size(52.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f).padding(vertical = 10.dp)) {
                Text(
                    text = evento.estado.ifBlank { "Evento Comunitario" },
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Text(
                    text = evento.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1C),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = evento.descripcion,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            AsyncImage(
                model = if (evento.imagen.isNotEmpty()) evento.imagen else R.drawable.playa,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

private fun colorBorde(estado: String): Color = when (estado.lowercase()) {
    "noticia" -> Color(0xFFE53935)
    "urgente" -> Color(0xFFFF6F00)
    "cerrado" -> Color(0xFF757575)
    else -> GreenBar
}
