package com.ecoquest.app.ui.feature.juegos

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private data class CartaMemorama(
    val id: Int,
    val emoji: String,
    val parejaId: Int,
    val color: Color
)

private fun generarTablero(): List<CartaMemorama> = listOf(
    CartaMemorama(0, "\uD83C\uDF3F", 0, Color(0xFF4CAF50)),
    CartaMemorama(1, "\uD83C\uDF3F", 0, Color(0xFF4CAF50)),
    CartaMemorama(2, "\uD83D\uDC33", 1, Color(0xFF2196F3)),
    CartaMemorama(3, "\uD83D\uDC33", 1, Color(0xFF2196F3)),
    CartaMemorama(4, "\uD83C\uDF31", 2, Color(0xFFFF9800)),
    CartaMemorama(5, "\uD83C\uDF31", 2, Color(0xFFFF9800)),
    CartaMemorama(6, "\uD83D\uDC26", 3, Color(0xFF9C27B0)),
    CartaMemorama(7, "\uD83D\uDC26", 3, Color(0xFF9C27B0)),
    CartaMemorama(8, "\uD83D\uDCA7", 4, Color(0xFF00BCD4)),
    CartaMemorama(9, "\uD83D\uDCA7", 4, Color(0xFF00BCD4)),
    CartaMemorama(10, "\u2600\uFE0F", 5, Color(0xFFFF5722)),
    CartaMemorama(11, "\u2600\uFE0F", 5, Color(0xFFFF5722)),
).shuffled().mapIndexed { index, carta -> carta.copy(id = index) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoramaScreen(onBack: () -> Unit) {
    var cartas by remember { mutableStateOf(generarTablero()) }
    var volteadas by remember { mutableStateOf(listOf<Int>()) }
    var parejasEncontradas by remember { mutableIntStateOf(0) }
    var movimientos by remember { mutableIntStateOf(0) }
    var bloqueado by remember { mutableStateOf(false) }
    var juegoCompletado by remember { mutableStateOf(false) }
    val parejasCompletadas = remember { mutableStateOf(setOf<Int>()) }
    val totalParejas = 6

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memorama Verde", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Parejas: $parejasEncontradas/$totalParejas",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Movimientos: $movimientos",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (juegoCompletado) {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "\uD83C\uDF89",
                            fontSize = 64.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "\u00a1Felicidades!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Completaste el memorama en $movimientos movimientos",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = {
                                    cartas = generarTablero()
                                    volteadas = emptyList()
                                    parejasCompletadas.value = emptySet()
                                    parejasEncontradas = 0
                                    movimientos = 0
                                    bloqueado = false
                                    juegoCompletado = false
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Jugar de nuevo", fontWeight = FontWeight.SemiBold)
                            }
                            Button(
                                onClick = onBack,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            ) {
                                Text("Volver", fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(12.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cartas, key = { it.id }) { carta ->
                        val completada = carta.parejaId in parejasCompletadas.value

                        CartaMemoramaItem(
                            carta = carta,
                            volteada = volteadas.contains(carta.id) || completada,
                            onClick = {
                                if (!bloqueado && !completada && !volteadas.contains(carta.id) && volteadas.size < 2) {
                                    volteadas = volteadas + carta.id
                                    if (volteadas.size == 2) {
                                        movimientos++
                                        val primera = cartas.first { it.id == volteadas[0] }
                                        val segunda = cartas.first { it.id == volteadas[1] }
                                        if (primera.parejaId == segunda.parejaId) {
                                            parejasCompletadas.value = parejasCompletadas.value + primera.parejaId
                                            parejasEncontradas++
                                            volteadas = emptyList()
                                            if (parejasEncontradas == totalParejas) {
                                                juegoCompletado = true
                                            }
                                        } else {
                                            bloqueado = true
                                            coroutineScope.launch {
                                                delay(800)
                                                volteadas = emptyList()
                                                bloqueado = false
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CartaMemoramaItem(
    carta: CartaMemorama,
    volteada: Boolean,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (volteada) 180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f
            }
            .clickable(enabled = !volteada, onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (volteada) 4.dp else 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (volteada) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation > 90f) {
                Text(
                    text = carta.emoji,
                    fontSize = 28.sp,
                    modifier = Modifier.graphicsLayer { rotationY = -rotation }
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Eco,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp).graphicsLayer { rotationY = -rotation },
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
