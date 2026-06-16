package com.ecoquest.app.ui.feature.perfil

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val Gold = Color(0xFFFFD700)
private val Coral = Color(0xFFFF6B6B)
private val Orange = Color(0xFFFF6B35)
private val Purple = Color(0xFF9C27B0)
private val Green = Color(0xFF0D6B43)
private val GreenAccent = Color(0xFF4CAF50)
private val Teal = Color(0xFF00695C)
private val TealLight = Color(0xFF4DB6AC)
private val Indigo = Color(0xFF3F51B5)
private val IndigoLight = Color(0xFF9FA8DA)
private val Rose = Color(0xFFAD1457)
private val RoseLight = Color(0xFFF06292)
private val RainbowColors = listOf(
    Color(0xFFFF0000), Color(0xFFFF7F00), Color(0xFFFFFF00),
    Color(0xFF00FF00), Color(0xFF0000FF), Color(0xFF4B0082), Color(0xFF8F00FF)
)

data class MarcoVisual(
    val colors: List<Color>,
    val borderWidth: Dp = 6.dp
)

data class TemaVisual(
    val gradient: List<Color>
)

data class InsigniaVisual(
    val backgroundColor: Color,
    val iconTint: Color
)

data class EstiloNombreVisual(
    val brush: Brush?
)

data class CosmeticoVisual(
    val marco: MarcoVisual? = null,
    val tema: TemaVisual? = null,
    val insignia: InsigniaVisual? = null,
    val estiloNombre: EstiloNombreVisual? = null
)

private val cosmeticosMap = mapOf(
    1L to CosmeticoVisual(
        marco = MarcoVisual(colors = listOf(Gold, Color(0xFFFFA000)), borderWidth = 6.dp)
    ),
    2L to CosmeticoVisual(
        marco = MarcoVisual(colors = listOf(Coral, Gold), borderWidth = 8.dp)
    ),
    3L to CosmeticoVisual(
        tema = TemaVisual(gradient = listOf(Green, GreenAccent))
    ),
    4L to CosmeticoVisual(
        tema = TemaVisual(gradient = listOf(Orange, Purple))
    ),
    5L to CosmeticoVisual(
        insignia = InsigniaVisual(backgroundColor = GreenAccent, iconTint = Color.White)
    ),
    6L to CosmeticoVisual(
        estiloNombre = EstiloNombreVisual(brush = Brush.horizontalGradient(RainbowColors))
    ),
    7L to CosmeticoVisual(
        tema = TemaVisual(gradient = listOf(Teal, TealLight))
    ),
    8L to CosmeticoVisual(
        tema = TemaVisual(gradient = listOf(Indigo, IndigoLight))
    ),
    9L to CosmeticoVisual(
        tema = TemaVisual(gradient = listOf(Rose, RoseLight))
    )
)

fun getCosmeticoVisual(productoId: Long): CosmeticoVisual {
    return cosmeticosMap[productoId] ?: CosmeticoVisual()
}
