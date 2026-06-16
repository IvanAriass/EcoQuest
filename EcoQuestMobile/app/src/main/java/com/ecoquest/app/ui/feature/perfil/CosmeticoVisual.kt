package com.ecoquest.app.ui.feature.perfil

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material.icons.filled.Star
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
private val Emerald = Color(0xFF2E7D32)
private val EmeraldLight = Color(0xFF66BB6A)
private val Sapphire = Color(0xFF1565C0)
private val SapphireLight = Color(0xFF42A5F5)
private val Ruby = Color(0xFFC62828)
private val RubyLight = Color(0xFFEF5350)
private val NatureBlue = Color(0xFF1565C0)
private val RecycleOrange = Color(0xFFE65100)
private val StarGold = Color(0xFFFF8F00)
private val FireRed = Color(0xFFFF1744)
private val FireOrange = Color(0xFFFF6D00)
private val FireYellow = Color(0xFFFFEA00)
private val IceCyan = Color(0xFF00BCD4)
private val IceBlue = Color(0xFF42A5F5)
private val IceWhite = Color(0xFFE3F2FD)
private val NeonGreen = Color(0xFF00E676)
private val NeonPink = Color(0xFFE040FB)
private val RainbowColors = listOf(
    Color(0xFFFF0000), Color(0xFFFF7F00), Color(0xFFFFFF00),
    Color(0xFF00FF00), Color(0xFF0000FF), Color(0xFF4B0082), Color(0xFF8F00FF)
)
private val FireColors = listOf(FireRed, FireOrange, FireYellow)
private val IceColors = listOf(IceCyan, IceBlue, IceWhite)
private val NeonColors = listOf(NeonGreen, IceCyan, NeonPink)

data class MarcoVisual(
    val colors: List<Color>,
    val borderWidth: Dp = 6.dp
)

data class TemaVisual(
    val gradient: List<Color>
)

data class InsigniaVisual(
    val backgroundColor: Color,
    val iconTint: Color,
    val icono: ImageVector = Icons.Filled.Eco
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
        insignia = InsigniaVisual(backgroundColor = GreenAccent, iconTint = Color.White, icono = Icons.Filled.Eco)
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
    ),
    10L to CosmeticoVisual(
        marco = MarcoVisual(colors = listOf(Emerald, EmeraldLight), borderWidth = 6.dp)
    ),
    11L to CosmeticoVisual(
        marco = MarcoVisual(colors = listOf(Sapphire, SapphireLight), borderWidth = 6.dp)
    ),
    12L to CosmeticoVisual(
        marco = MarcoVisual(colors = listOf(Ruby, RubyLight), borderWidth = 6.dp)
    ),
    13L to CosmeticoVisual(
        insignia = InsigniaVisual(backgroundColor = NatureBlue, iconTint = Color.White, icono = Icons.Filled.Park)
    ),
    14L to CosmeticoVisual(
        insignia = InsigniaVisual(backgroundColor = RecycleOrange, iconTint = Color.White, icono = Icons.Filled.Recycling)
    ),
    15L to CosmeticoVisual(
        insignia = InsigniaVisual(backgroundColor = StarGold, iconTint = Color.White, icono = Icons.Filled.Star)
    ),
    16L to CosmeticoVisual(
        estiloNombre = EstiloNombreVisual(brush = Brush.horizontalGradient(FireColors))
    ),
    17L to CosmeticoVisual(
        estiloNombre = EstiloNombreVisual(brush = Brush.horizontalGradient(IceColors))
    ),
    18L to CosmeticoVisual(
        estiloNombre = EstiloNombreVisual(brush = Brush.horizontalGradient(NeonColors))
    )
)

fun getCosmeticoVisual(productoId: Long): CosmeticoVisual {
    return cosmeticosMap[productoId] ?: CosmeticoVisual()
}
