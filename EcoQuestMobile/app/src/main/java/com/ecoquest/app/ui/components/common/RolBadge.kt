package com.ecoquest.app.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.domain.model.RolInfo

@Composable
fun RolBadge(
    rolInfo: RolInfo,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val bgColor = when (rolInfo.nivel) {
        0 -> MaterialTheme.colorScheme.surfaceVariant
        1 -> MaterialTheme.colorScheme.primaryContainer
        2 -> MaterialTheme.colorScheme.tertiaryContainer
        3 -> MaterialTheme.colorScheme.secondaryContainer
        4 -> MaterialTheme.colorScheme.errorContainer
        5 -> MaterialTheme.colorScheme.inversePrimary
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    val contentColor = when (rolInfo.nivel) {
        0 -> MaterialTheme.colorScheme.onSurfaceVariant
        1 -> MaterialTheme.colorScheme.onPrimaryContainer
        2 -> MaterialTheme.colorScheme.onTertiaryContainer
        3 -> MaterialTheme.colorScheme.onSecondaryContainer
        4 -> MaterialTheme.colorScheme.onErrorContainer
        5 -> MaterialTheme.colorScheme.inverseOnSurface
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    val clickMod = if (onClick != null) modifier.clickable(onClick = onClick) else modifier
    Box(
        modifier = clickMod
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = rolInfo.emoji,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = rolInfo.nombre,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

@Composable
fun RolSelectorItem(
    rolInfo: RolInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = rolInfo.emoji, fontSize = 20.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = rolInfo.nombre,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
