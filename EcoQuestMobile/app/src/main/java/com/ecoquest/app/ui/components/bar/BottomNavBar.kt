package com.ecoquest.app.ui.components.bar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecoquest.app.ui.theme.Green30

enum class BottomNavTab(val label: String, val routePrefix: String) {
    HOME("Home", "Home"),
    EVENTOS("Eventos", "Eventos"),
    COMUNIDADES("Comunidad", "Comunidades"),
    TIENDA("Tienda", "Tienda"),
    PERFIL("Perfil", "Perfil")
}

private data class NavIconSet(
    val filled: androidx.compose.ui.graphics.vector.ImageVector,
    val outlined: androidx.compose.ui.graphics.vector.ImageVector
)

private val iconMap = mapOf(
    BottomNavTab.HOME to NavIconSet(Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavTab.EVENTOS to NavIconSet(Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
    BottomNavTab.COMUNIDADES to NavIconSet(Icons.Filled.Groups, Icons.Outlined.Groups),
    BottomNavTab.TIENDA to NavIconSet(Icons.Filled.Storefront, Icons.Outlined.Storefront),
    BottomNavTab.PERFIL to NavIconSet(Icons.Filled.Person, Icons.Outlined.Person)
)

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onTabSelected: (BottomNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = when {
        currentRoute?.contains("Home", ignoreCase = true) == true && currentRoute?.contains("Evento", ignoreCase = true) != true -> 0
        currentRoute?.contains("Evento", ignoreCase = true) == true -> 1
        currentRoute?.contains("Comunidad", ignoreCase = true) == true -> 2
        currentRoute?.contains("Tienda", ignoreCase = true) == true -> 3
        currentRoute?.contains("Perfil", ignoreCase = true) == true || currentRoute?.contains("Ajustes", ignoreCase = true) == true -> 4
        else -> 0
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .padding(bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Green30)
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavTab.entries.forEachIndexed { index, tab ->
                    val isSelected = index == selectedIndex
                    val icons = iconMap[tab]!!

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .then(
                                if (isSelected) Modifier.background(Color.White.copy(alpha = 0.18f))
                                else Modifier
                            )
                            .clickable { onTabSelected(tab) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = if (isSelected) icons.filled else icons.outlined,
                                contentDescription = tab.label,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tab.label,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                color = Color.White.copy(alpha = if (isSelected) 1f else 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}
