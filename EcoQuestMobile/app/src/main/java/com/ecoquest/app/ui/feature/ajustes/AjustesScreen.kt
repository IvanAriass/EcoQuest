package com.ecoquest.app.ui.feature.ajustes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.ui.theme.GreenBar

@Composable
fun AjustesScreen(
    uiState: AjustesUiState,
    onEvent: (AjustesEvent) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Ajustes", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1C1C1C))

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Apariencia", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Tema oscuro", fontSize = 16.sp)
                    Switch(
                        checked = uiState.temaOscuro,
                        onCheckedChange = { onEvent(AjustesEvent.OnToggleTema) },
                        colors = SwitchDefaults.colors(checkedTrackColor = GreenBar)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onLogout() },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Cerrar sesión", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}
