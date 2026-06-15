package com.ecoquest.app.ui.feature.juegos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.unit.sp

private data class Pregunta(
    val pregunta: String,
    val opciones: List<String>,
    val correcta: Int,
    val explicacion: String
)

private val preguntas = listOf(
    Pregunta(
        pregunta = "\u00bfQu\u00e9 gas de efecto invernadero es el m\u00e1s abundante en la atm\u00f3sfera?",
        opciones = listOf("Di\u00f3xido de carbono (CO2)", "Metano (CH4)", "Vapor de agua (H2O)", "\u00d3xido nitroso (N2O)"),
        correcta = 0,
        explicacion = "El CO2 es el gas de efecto invernadero m\u00e1s abundante emitido por actividades humanas, principalmente por la quema de combustibles f\u00f3siles."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 porcentaje del agua del planeta es agua dulce disponible para consumo humano?",
        opciones = listOf("0.5%", "2.5%", "10%", "25%"),
        correcta = 0,
        explicacion = "Menos del 0.5% del agua de la Tierra es agua dulce disponible. El 97.5% es agua salada y el 2% restante est\u00e1 congelada en glaciares."
    ),
    Pregunta(
        pregunta = "\u00bfCu\u00e1nto tiempo tarda una botella de pl\u00e1stico en degradarse en la naturaleza?",
        opciones = listOf("50 a\u00f1os", "100 a\u00f1os", "450 a\u00f1os", "1000 a\u00f1os"),
        correcta = 2,
        explicacion = "Una botella de pl\u00e1stico puede tardar hasta 450 a\u00f1os en degradarse completamente en el medio ambiente."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 tipo de energ\u00eda renovable genera m\u00e1s electricidad a nivel mundial?",
        opciones = listOf("Solar", "E\u00f3lica", "Hidroel\u00e9ctrica", "Geot\u00e9rmica"),
        correcta = 2,
        explicacion = "La energ\u00eda hidroel\u00e9ctrica es la fuente renovable que m\u00e1s electricidad genera en el mundo."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 significa la sigla 'ADR' en gesti\u00f3n de residuos?",
        opciones = listOf("Alto Deber de Reciclaje", "Agenda de Desarrollo Responsable", "Atenuaci\u00f3n de Desechos Radiactivos", "No es una sigla ambiental"),
        correcta = 3,
        explicacion = "En realidad ninguna de las opciones corresponde. Las siglas ambientales m\u00e1s conocidas son RSU (Residuos S\u00f3lidos Urbanos) y RAEE (Residuos de Aparatos El\u00e9ctricos)."
    ),
    Pregunta(
        pregunta = "\u00bfCu\u00e1l es el principal objetivo de la econom\u00eda circular?",
        opciones = listOf("Reciclar todo", "Reducir, reutilizar y reciclar", "Minimizar residuos y mantener recursos en uso", "Producir m\u00e1s con menos"),
        correcta = 2,
        explicacion = "La econom\u00eda circular busca minimizar los residuos y mantener los recursos en uso el mayor tiempo posible, cerrando el ciclo de vida de los productos."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 animal est\u00e1 en mayor peligro de extinci\u00f3n actualmente?",
        opciones = listOf("Tigre de Bengala", "Oso Panda", "Lince Ib\u00e9rico", "Vaquita Marina"),
        correcta = 3,
        explicacion = "La vaquita marina es el mam\u00edfero marino m\u00e1s amenazado del mundo, con menos de 10 ejemplares en libertad."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 d\u00eda se celebra el D\u00eda de la Tierra?",
        opciones = listOf("22 de marzo", "22 de abril", "5 de junio", "5 de julio"),
        correcta = 1,
        explicacion = "El D\u00eda de la Tierra se celebra cada a\u00f1o el 22 de abril desde 1970."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 material es el m\u00e1s reciclado del mundo?",
        opciones = listOf("Vidrio", "Papel", "Acero", "Pl\u00e1stico"),
        correcta = 2,
        explicacion = "El acero es el material m\u00e1s reciclado del mundo, con una tasa de reciclaje superior al 80% en muchos pa\u00edses."
    ),
    Pregunta(
        pregunta = "\u00bfQu\u00e9 es una 'isla de calor' urbana?",
        opciones = listOf(
            "Una zona con clima tropical en la ciudad",
            "\u00c1rea urbana con temperaturas m\u00e1s altas que sus alrededores",
            "Un vertedero de residuos electr\u00f3nicos",
            "Un parque solar dentro de la ciudad"
        ),
        correcta = 1,
        explicacion = "Una isla de calor urbana ocurre cuando las ciudades tienen temperaturas significativamente m\u00e1s altas que las zonas rurales circundantes debido a la absorci\u00f3n de calor por edificios y asfalto."
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcoQuizScreen(onBack: () -> Unit) {
    var preguntaActual by remember { mutableIntStateOf(0) }
    var puntuacion by remember { mutableIntStateOf(0) }
    var seleccionada by remember { mutableStateOf(-1) }
    var mostrandoResultado by remember { mutableStateOf(false) }
    var respuestasCorrectas by remember { mutableIntStateOf(0) }

    val totalPreguntas = preguntas.size
    val esUltimaPregunta = preguntaActual == totalPreguntas - 1

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eco-Quiz", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        if (!mostrandoResultado) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = { (preguntaActual + 1).toFloat() / totalPreguntas },
                    modifier = Modifier.fillMaxWidth().height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Pregunta ${preguntaActual + 1} de $totalPreguntas",
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

                Text(
                    text = preguntas[preguntaActual].pregunta,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(20.dp))

                preguntas[preguntaActual].opciones.forEachIndexed { index, opcion ->
                    val esCorrecta = index == preguntas[preguntaActual].correcta
                    val esSeleccionada = index == seleccionada
                    val colorBorde = when {
                        seleccionada == -1 -> MaterialTheme.colorScheme.outlineVariant
                        esCorrecta -> Color(0xFF4CAF50)
                        esSeleccionada -> Color(0xFFE53935)
                        else -> MaterialTheme.colorScheme.outlineVariant
                    }
                    val colorFondo = when {
                        seleccionada == -1 -> MaterialTheme.colorScheme.surface
                        esCorrecta -> Color(0xFF4CAF50).copy(alpha = 0.1f)
                        esSeleccionada -> Color(0xFFE53935).copy(alpha = 0.1f)
                        else -> MaterialTheme.colorScheme.surface
                    }

                    Card(
                        onClick = {
                            if (seleccionada == -1) {
                                seleccionada = index
                                if (esCorrecta) {
                                    puntuacion += 10
                                    respuestasCorrectas++
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = colorFondo),
                        border = CardDefaults.outlinedCardBorder().copy(
                            width = if (seleccionada != -1 && (esCorrecta || esSeleccionada)) 2.dp else 0.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.size(28.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        when {
                                            seleccionada == -1 -> MaterialTheme.colorScheme.surfaceVariant
                                            esCorrecta -> Color(0xFF4CAF50)
                                            esSeleccionada -> Color(0xFFE53935)
                                            else -> MaterialTheme.colorScheme.surfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (seleccionada != -1) {
                                    if (esCorrecta) {
                                        Icon(Icons.Filled.Check, contentDescription = null,
                                            modifier = Modifier.size(16.dp), tint = Color.White)
                                    } else if (esSeleccionada) {
                                        Icon(Icons.Filled.Close, contentDescription = null,
                                            modifier = Modifier.size(16.dp), tint = Color.White)
                                    }
                                } else {
                                    Text(
                                        text = "${'A' + index}",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = opcion,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = seleccionada != -1,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = preguntas[preguntaActual].explicacion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = {
                                    if (esUltimaPregunta) {
                                        mostrandoResultado = true
                                    } else {
                                        preguntaActual++
                                        seleccionada = -1
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = if (esUltimaPregunta) "Ver resultado" else "Siguiente pregunta",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        } else {
            ResultadoQuiz(
                puntuacion = puntuacion,
                respuestasCorrectas = respuestasCorrectas,
                totalPreguntas = totalPreguntas,
                onReintentar = {
                    preguntaActual = 0
                    puntuacion = 0
                    seleccionada = -1
                    mostrandoResultado = false
                    respuestasCorrectas = 0
                },
                onSalir = onBack
            )
        }
    }
}

@Composable
private fun ResultadoQuiz(
    puntuacion: Int,
    respuestasCorrectas: Int,
    totalPreguntas: Int,
    onReintentar: () -> Unit,
    onSalir: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Eco,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "\u00a1Quiz completado!",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "$respuestasCorrectas de $totalPreguntas correctas",
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
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = null, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Jugar de nuevo", fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onSalir,
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
