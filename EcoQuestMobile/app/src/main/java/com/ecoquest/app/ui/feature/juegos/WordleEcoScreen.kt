package com.ecoquest.app.ui.feature.juegos

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

private val todasPalabras = listOf(
    "VERDE", "FLORA", "FAUNA", "SUELO", "AGUAS",
    "MARES", "SOLAR", "ROBLE", "PINOS", "PALMA",
    "CICLO", "LAGOS", "NUBES", "VIDAS", "HOJAS",
    "RAMAS", "MONTE", "SELVA", "PLAYA", "ARBOL",
    "NIEVE", "ROCAS", "LLANO", "PRADO",
)

private val tecladoFilas = listOf(
    listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"),
    listOf("A", "S", "D", "F", "G", "H", "J", "K", "L", "\u00d1"),
    listOf("ENT", "Z", "X", "C", "V", "B", "N", "M", "BS"),
)

private enum class EstadoLetra { SIN_ESTADO, AUSENTE, PRESENTE, CORRECTO }

private fun colorEstadoLetra(estado: EstadoLetra): Color = when (estado) {
    EstadoLetra.CORRECTO -> Color(0xFF4CAF50)
    EstadoLetra.PRESENTE -> Color(0xFFE6B800)
    EstadoLetra.AUSENTE -> Color(0xFF787C7E)
    EstadoLetra.SIN_ESTADO -> Color(0xFFD3D6DA)
}

private fun palabraDiaria(): String {
    val dias = LocalDate.now().toEpochDay()
    val idx = (dias % todasPalabras.size).toInt()
    return todasPalabras[idx]
}

private fun calcularEstados(intento: String, palabra: String): List<EstadoLetra> {
    val restantes = palabra.toMutableList()
    val estados = Array(5) { EstadoLetra.AUSENTE }
    for (i in 0..4) {
        if (intento[i].toString() == palabra[i].toString()) {
            estados[i] = EstadoLetra.CORRECTO
            restantes[restantes.indexOf(palabra[i])] = ' '
        }
    }
    for (i in 0..4) {
        if (estados[i] == EstadoLetra.CORRECTO) continue
        val idx = restantes.indexOfFirst { it.toString() == intento[i].toString() }
        if (idx >= 0) {
            estados[i] = EstadoLetra.PRESENTE
            restantes[idx] = ' '
        }
    }
    return estados.toList()
}

private fun calcularEstadoTeclas(intentos: List<String>, palabra: String): Map<String, EstadoLetra> {
    val mapa = mutableMapOf<String, EstadoLetra>()
    for (intento in intentos) {
        val estados = calcularEstados(intento, palabra)
        for (i in 0..4) {
            val letra = intento[i].toString()
            val previo = mapa[letra] ?: EstadoLetra.SIN_ESTADO
            if (estados[i].ordinal > previo.ordinal) {
                mapa[letra] = estados[i]
            }
        }
    }
    return mapa
}

private const val PREFS_NAME = "ecoquest_wordle"
private const val KEY_FECHA = "fecha"
private const val KEY_INTENTOS = "intentos"
private const val KEY_ENTRADA = "entrada"
private const val KEY_TERMINADO = "terminado"
private const val KEY_GANO = "gano"

private fun cargarEstado(context: Context, fecha: String, palabra: String): WordleState? {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    val fechaGuardada = prefs.getString(KEY_FECHA, null) ?: return null
    if (fechaGuardada != fecha) return null
    val intentosRaw = prefs.getString(KEY_INTENTOS, null) ?: return null
    val entrada = prefs.getString(KEY_ENTRADA, "") ?: ""
    val terminado = prefs.getBoolean(KEY_TERMINADO, false)
    val gano = prefs.getBoolean(KEY_GANO, false)
    val intentos = if (intentosRaw.isEmpty()) emptyList() else intentosRaw.split(",")
    val estadosIntentos = intentos.map { calcularEstados(it, palabra) }
    return WordleState(
        intentos = intentos,
        estadosIntentos = estadosIntentos,
        entradaActual = entrada,
        juegoTerminado = terminado,
        gano = gano,
    )
}

private fun guardarEstado(context: Context, fecha: String, state: WordleState) {
    val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    prefs.edit()
        .putString(KEY_FECHA, fecha)
        .putString(KEY_INTENTOS, state.intentos.joinToString(","))
        .putString(KEY_ENTRADA, state.entradaActual)
        .putBoolean(KEY_TERMINADO, state.juegoTerminado)
        .putBoolean(KEY_GANO, state.gano)
        .apply()
}

private fun limpiarEstado(context: Context) {
    context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().clear().apply()
}

private data class WordleState(
    val intentos: List<String> = emptyList(),
    val estadosIntentos: List<List<EstadoLetra>> = emptyList(),
    val entradaActual: String = "",
    val juegoTerminado: Boolean = false,
    val gano: Boolean = false,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordleEcoScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val fechaHoy = remember { LocalDate.now().toString() }
    val palabraCorrecta = remember { palabraDiaria() }
    val estadoTeclas = remember { mutableStateOf(mapOf<String, EstadoLetra>()) }
    val savedState = remember { cargarEstado(context, fechaHoy, palabraCorrecta) }

    var intentos by remember { mutableStateOf(savedState?.intentos ?: emptyList()) }
    var estadosIntentos by remember { mutableStateOf(savedState?.estadosIntentos ?: emptyList<List<EstadoLetra>>()) }
    var entradaActual by remember { mutableStateOf(savedState?.entradaActual ?: "") }
    var juegoTerminado by remember { mutableStateOf(savedState?.juegoTerminado ?: false) }
    var gano by remember { mutableStateOf(savedState?.gano ?: false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var animarFila by remember { mutableIntStateOf(-1) }

    val juegoBloqueado = juegoTerminado

    LaunchedEffect(Unit) {
        if (intentos.isNotEmpty()) {
            estadoTeclas.value = calcularEstadoTeclas(intentos, palabraCorrecta)
        }
    }

    fun persistir() {
        guardarEstado(
            context, fechaHoy,
            WordleState(intentos, estadosIntentos, entradaActual, juegoTerminado, gano)
        )
    }

    fun handleTecla(tecla: String) {
        if (juegoBloqueado) return
        mensajeError = null

        when (tecla) {
            "BS" -> {
                if (entradaActual.isNotEmpty()) {
                    entradaActual = entradaActual.dropLast(1)
                    persistir()
                }
            }
            "ENT" -> {
                if (entradaActual.length < 5) {
                    mensajeError = "Demasiado corta"
                    return
                }
                if (intentos.size >= 6) return

                val palabra = entradaActual
                val restantes = palabraCorrecta.toMutableList()
                val nuevosEstados = Array(5) { EstadoLetra.AUSENTE }

                for (i in 0..4) {
                    if (palabra[i].toString() == palabraCorrecta[i].toString()) {
                        nuevosEstados[i] = EstadoLetra.CORRECTO
                        restantes[restantes.indexOf(palabraCorrecta[i])] = ' '
                    }
                }
                for (i in 0..4) {
                    if (nuevosEstados[i] == EstadoLetra.CORRECTO) continue
                    val letra = palabra[i].toString()
                    val idx = restantes.indexOfFirst { it.toString() == letra }
                    if (idx >= 0) {
                        nuevosEstados[i] = EstadoLetra.PRESENTE
                        restantes[idx] = ' '
                    }
                }

                val nuevasTeclas = estadoTeclas.value.toMutableMap()
                for (i in 0..4) {
                    val letra = palabra[i].toString()
                    val previo = nuevasTeclas[letra] ?: EstadoLetra.SIN_ESTADO
                    if (nuevosEstados[i].ordinal > previo.ordinal) {
                        nuevasTeclas[letra] = nuevosEstados[i]
                    }
                }
                estadoTeclas.value = nuevasTeclas

                intentos = intentos + palabra
                estadosIntentos = estadosIntentos + listOf(nuevosEstados.toList())
                entradaActual = ""
                animarFila = intentos.size - 1

                if (palabra == palabraCorrecta) {
                    gano = true
                    juegoTerminado = true
                } else if (intentos.size >= 6) {
                    juegoTerminado = true
                }

                persistir()
            }
            else -> {
                if (entradaActual.length < 5) {
                    entradaActual += tecla
                    persistir()
                }
            }
        }
    }

    fun reiniciar() {
        limpiarEstado(context)
        intentos = emptyList()
        estadosIntentos = emptyList()
        entradaActual = ""
        juegoTerminado = false
        gano = false
        mensajeError = null
        animarFila = -1
        estadoTeclas.value = emptyMap()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Eco-Wordle", fontWeight = FontWeight.Bold) },
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
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = fechaHoy,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))

            mensajeError?.let {
                Text(
                    text = it,
                    color = Color(0xFFE53935),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (fila in 0..5) {
                    val esFilaActual = fila == intentos.size
                    val esFilaSubmit = fila < intentos.size

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(vertical = 3.dp)
                    ) {
                        for (col in 0..4) {
                            val letra = when {
                                esFilaSubmit -> intentos[fila][col].toString()
                                esFilaActual && col < entradaActual.length -> entradaActual[col].toString()
                                else -> ""
                            }
                            val estado = if (esFilaSubmit) estadosIntentos[fila][col] else EstadoLetra.SIN_ESTADO
                            val mostrarLetra = letra.isNotEmpty()

                            val colorFondo by animateColorAsState(
                                targetValue = when {
                                    esFilaSubmit -> colorEstadoLetra(estado)
                                    esFilaActual && mostrarLetra -> MaterialTheme.colorScheme.surfaceVariant
                                    else -> MaterialTheme.colorScheme.surface
                                },
                                animationSpec = tween(300),
                                label = "cell"
                            )
                            val colorTexto = when {
                                esFilaSubmit -> Color.White
                                esFilaActual -> MaterialTheme.colorScheme.onSurface
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                            val colorBorde = when {
                                esFilaSubmit -> Color.Transparent
                                esFilaActual && mostrarLetra -> MaterialTheme.colorScheme.outline
                                else -> Color(0xFFD3D6DA)
                            }

                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(colorFondo)
                                    .border(2.dp, colorBorde, RoundedCornerShape(6.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (mostrarLetra) {
                                    Text(
                                        text = letra,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorTexto
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (juegoTerminado) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(56.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Eco,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if (gano) {
                        Text(
                            text = "\u00a1Felicidades! Adivinaste en ${intentos.size} intentos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Text(
                            text = "La palabra era:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = palabraCorrecta,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 8.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Vuelve ma\u00f1ana para una nueva palabra",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
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
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                // Keyboard
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    tecladoFilas.forEach { fila ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(vertical = 3.dp)
                        ) {
                            fila.forEach { tecla ->
                                val estado = estadoTeclas.value[tecla] ?: EstadoLetra.SIN_ESTADO
                                val esEspecial = tecla == "ENT" || tecla == "BS"

                                Box(
                                    modifier = Modifier
                                        .then(
                                            if (esEspecial) Modifier.width(56.dp)
                                            else Modifier.width(30.dp)
                                        )
                                        .height(42.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(
                                            when {
                                                estado == EstadoLetra.CORRECTO -> Color(0xFF4CAF50)
                                                estado == EstadoLetra.PRESENTE -> Color(0xFFE6B800)
                                                estado == EstadoLetra.AUSENTE -> Color(0xFF787C7E)
                                                esEspecial -> MaterialTheme.colorScheme.surfaceVariant
                                                else -> Color(0xFFD3D6DA)
                                            }
                                        )
                                        .clickable { handleTecla(tecla) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = tecla,
                                        fontSize = if (esEspecial) 10.sp else 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = when {
                                            estado != EstadoLetra.SIN_ESTADO -> Color.White
                                            esEspecial -> MaterialTheme.colorScheme.onSurfaceVariant
                                            else -> Color(0xFF393A3F)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}
