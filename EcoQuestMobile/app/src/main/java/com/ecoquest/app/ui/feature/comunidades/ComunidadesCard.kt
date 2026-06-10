package com.ecoquest.app.ui.feature.comunidades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ecoquest.app.domain.model.Comunidad

@Composable
fun ComunidadesCard(
    comunidad: Comunidad,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = if (comunidad.imagen.isNotEmpty()) comunidad.imagen else R.drawable.iconoeco,
                contentDescription = comunidad.nombre,
                modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = comunidad.nombre, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C1C1C), maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = comunidad.descripcion, fontSize = 11.sp, color = Color.Gray, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
