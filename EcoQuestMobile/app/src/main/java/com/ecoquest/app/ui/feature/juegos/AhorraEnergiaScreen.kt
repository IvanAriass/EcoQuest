package com.ecoquest.app.ui.feature.juegos

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private data class Habito(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val esSostenible: Boolean,
    val explicacion: String,
    val puntuacion: Int = 10
)

private val habitos = listOf(
    Habito(
        id = 1,
        titulo = "Dejar el cargador enchufado sin uso",
        descripcion = "Mantienes el cargador del m\u00f3vil conectado a la corriente aunque no est\u00e9 cargando nada.",
        esSostenible = false,
        explicacion = "Los cargadores siguen consumiendo electricidad aunque no est\u00e9n conectados a un dispositivo. Desench\u00fafalos cuando no los uses."
    ),
    Habito(
        id = 2,
        titulo = "Usar bombillas LED de bajo consumo",
        descripcion = "Has reemplazado todas las bombillas de tu casa por luces LED de bajo consumo energ\u00e9tico.",
        esSostenible = true,
        explicacion = "Las bombillas LED consumen hasta un 80% menos de energ\u00eda que las incandescentes y duran mucho m\u00e1s."
    ),
    Habito(
        id = 3,
        titulo = "Poner la lavadora con agua caliente siempre",
        descripcion = "Siempre programas la lavadora con agua caliente para que limpie mejor la ropa.",
        esSostenible = false,
        explicacion = "Calentar agua representa el 90% del consumo de energ\u00eda de la lavadora. Usar agua fr\u00eda o tibia reduce dr\u00e1sticamente el consumo."
    ),
    Habito(
        id = 4,
        titulo = "Secar la ropa al aire libre",
        descripcion = "Tiendes la ropa al sol en lugar de usar la secadora el\u00e9ctrica.",
        esSostenible = true,
        explicacion = "Las secadoras son uno de los electrodom\u00e9sticos que m\u00e1s energ\u00eda consumen. Secar al aire libre es gratis y adem\u00e1s cuida tus prendas."
    ),
    Habito(
        id = 5,
        titulo = "Dejar el ordenador encendido toda la noche",
        descripcion = "Apagas la pantalla pero dejas el ordenador encendido mientras duermes.",
        esSostenible = false,
        explicacion = "Un ordenador encendido 8 horas extra consume aproximadamente 0.4 kWh. Apagarlo cuando no lo usas puede ahorrar hasta 150 kWh al a\u00f1o."
    ),
    Habito(
        id = 6,
        titulo = "Usar transporte p\u00fablico o bicicleta",
        descripcion = "Prefieres el autob\u00fas, metro o bicicleta antes que el coche para tus desplazamientos diarios.",
        esSostenible = true,
        explicacion = "El transporte representa una gran parte de las emisiones de CO2. Usar transporte p\u00fablico o bicicleta reduce significativamente tu huella de carbono."
    ),
    Habito(
        id = 7,
        titulo = "Abrir la ventana con la calefacci\u00f3n encendida",
        descripcion = "Tienes la calefacci\u00f3n a tope pero abres las ventanas para ventilar la habitaci\u00f3n.",
        esSostenible = false,
        explicacion = "Calentar el exterior es un desperdicio energ\u00e9tico enorme. Ventila primero y luego enciende la calefacci\u00f3n."
    ),
    Habito(
        id = 8,
        titulo = "Aprovechar la luz natural del d\u00eda",
        descripcion = "Abres las cortinas y persianas para iluminar tu casa con luz solar en lugar de encender luces.",
        esSostenible = true,
        explicacion = "Aprovechar la luz natural reduce el consumo de electricidad y adem\u00e1s mejora tu estado de \u00e1nimo y productividad."
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AhorraEnergiaScreen(onBack: () -> Unit) {
    var habitoActual by remember { mutableIntStateOf(0) }
    var puntuacion by remember { mutableIntStateOf(0) }
    var respondido by remember { mutableStateOf(false) }
    var respuestaUsuario by remember { mutableStateOf<Boolean?>(null) }
    var juegoTerminado by remember { mutableStateOf(false) }
    var aciertos by remember { mutableIntStateOf(0) }

    val totalHabitos = habitos.size
    val habito = habitos[habitoActual]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ahorra Energ\u00eda", fontWeight = FontWeight.Bold) },
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
            if (!juegoTerminado) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (habitoActual + 1).toFloat() / totalHabitos },
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "H\u00e1bito ${habitoActual + 1} de $totalHabitos",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Puntuaci\u00f3n: $puntuacion pts",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(20.dp))

                AnimatedContent(
                    targetState = habitoActual,
                    transitionSpec = {
                        (slideInHorizontally { it } + fadeIn()).togetherWith(slideOutHorizontally { -it } + fadeOut())
                    },
                    label = "habito"
                ) { _ ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(64.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Bolt,
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = habito.titulo,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = habito.descripcion,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (!respondido) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                respondido = true
                                respuestaUsuario = true
                                if (habito.esSostenible) {
                                    puntuacion += habito.puntuacion
                                    aciertos++
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sostenible", fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = {
                                respondido = true
                                respuestaUsuario = false
                                if (!habito.esSostenible) {
                                    puntuacion += habito.puntuacion
                                    aciertos++
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE53935)
                            )
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("No sostenible", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                if (respondido) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (respuestaUsuario == habito.esSostenible)
                                Color(0xFF4CAF50).copy(alpha = 0.1f)
                            else
                                Color(0xFFE53935).copy(alpha = 0.1f)
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (respuestaUsuario == habito.esSostenible)
                                        Icons.Filled.CheckCircle else Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                    tint = if (respuestaUsuario == habito.esSostenible)
                                        Color(0xFF4CAF50) else Color(0xFFE53935)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (respuestaUsuario == habito.esSostenible)
                                        "\u00a1Correcto!" else "Incorrecto",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (respuestaUsuario == habito.esSostenible)
                                        Color(0xFF4CAF50) else Color(0xFFE53935)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = habito.explicacion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    if (habitoActual == totalHabitos - 1) {
                                        juegoTerminado = true
                                    } else {
                                        habitoActual++
                                        respondido = false
                                        respuestaUsuario = null
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = if (habitoActual == totalHabitos - 1)
                                        "Ver resultado" else "Siguiente h\u00e1bito",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Box(
                            modifier = Modifier.size(80.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Bolt,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "\u00a1Reto completado!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "$aciertos de $totalHabitos acertados",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$puntuacion puntos Eco",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = {
                                habitoActual = 0
                                puntuacion = 0
                                respondido = false
                                respuestaUsuario = null
                                juegoTerminado = false
                                aciertos = 0
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Jugar de nuevo", fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = onBack,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        ) {
                            Text("Volver a juegos", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}
