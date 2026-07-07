package com.ecoquest.app.ui.feature.gestion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ecoquest.app.R
import com.ecoquest.app.domain.model.RolInfo
import com.ecoquest.app.ui.components.common.RolBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionComunidadScreen(
    uiState: GestionComunidadUiState,
    onEvent: (GestionComunidadEvent) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestionar comunidad", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
            uiState.mensajeExito?.let {
                Text(it, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = uiState.nombreEdit,
                        onValueChange = { onEvent(GestionComunidadEvent.OnNombreChanged(it)) },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = uiState.descripcionEdit,
                        onValueChange = { onEvent(GestionComunidadEvent.OnDescripcionChanged(it)) },
                        label = { Text("Acerca de") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                    Button(
                        onClick = { onEvent(GestionComunidadEvent.OnGuardarInfo) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Guardar cambios")
                    }
                }
            }

            SeccionRoles(uiState = uiState, onEvent = onEvent)

            Text(
                text = "Miembros (${uiState.miembros.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            uiState.miembros.forEach { miembro ->
                MiembroGestionCard(
                    miembro = miembro,
                    rolesDisponibles = uiState.rolesDisponibles,
                    onRolChanged = { nuevoRol ->
                        onEvent(GestionComunidadEvent.OnCambiarRol(miembro.usuario.id, nuevoRol))
                    },
                    onExpulsar = {
                        onEvent(GestionComunidadEvent.OnExpulsarMiembro(miembro.usuario.id))
                    }
                )
            }
        }

        uiState.dialogExpulsar?.let { targetId ->
            val nombre = uiState.miembros.find { it.usuario.id == targetId }?.usuario?.nombre ?: ""
            AlertDialog(
                onDismissRequest = { onEvent(GestionComunidadEvent.OnDismissDialog) },
                title = { Text("Expulsar miembro") },
                text = { Text("\u00BFEst\u00E1s seguro de expulsar a $nombre?") },
                confirmButton = {
                    TextButton(
                        onClick = { onEvent(GestionComunidadEvent.OnConfirmarExpulsion) },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Expulsar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onEvent(GestionComunidadEvent.OnDismissDialog) }) {
                        Text("Cancelar")
                    }
                }
            )
        }

        if (uiState.mostrarDialogoNuevoRol) {
            DialogoNuevoRol(uiState = uiState, onEvent = onEvent)
        }

        uiState.dialogoEliminarRol?.let { rolId ->
            val nombre = uiState.rolesEditables.find { it.id == rolId }?.nombre ?: rolId
            AlertDialog(
                onDismissRequest = { onEvent(GestionComunidadEvent.OnDismissDialog) },
                title = { Text("Eliminar rol") },
                text = { Text("\u00BFEst\u00E1s seguro de eliminar el rol \"$nombre\"? Los miembros con este rol pasarán a no tener rol asignado.") },
                confirmButton = {
                    TextButton(
                        onClick = { onEvent(GestionComunidadEvent.OnConfirmarEliminarRol) },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onEvent(GestionComunidadEvent.OnDismissDialog) }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
private fun SeccionRoles(
    uiState: GestionComunidadUiState,
    onEvent: (GestionComunidadEvent) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Roles y permisos (${uiState.rolesEditables.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            uiState.rolesEditables.forEach { rol ->
                RolEditableCard(rol = rol, onEvent = onEvent)
            }

            Spacer(modifier = Modifier.height(4.dp))
            OutlinedButton(
                onClick = { onEvent(GestionComunidadEvent.OnAbrirDialogoNuevoRol) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("A\u00F1adir nuevo rol")
            }
        }
    }
}

@Composable
private fun RolEditableCard(
    rol: RolEditable,
    onEvent: (GestionComunidadEvent) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEvent(GestionComunidadEvent.OnToggleRolExpandido(rol.id)) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = rol.emoji, fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = rol.nombre,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Nivel ${rol.nivel}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = { onEvent(GestionComunidadEvent.OnEliminarRol(rol.id)) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Eliminar rol",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Icon(
                    if (rol.expandido) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (rol.expandido) "Contraer" else "Expandir",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (rol.expandido) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = rol.descripcion,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Permisos:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    PERMISOS_DISPONIBLES.forEach { permiso ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onEvent(GestionComunidadEvent.OnPermisoToggle(rol.id, permiso)) }
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = permiso in rol.permisos,
                                onCheckedChange = { onEvent(GestionComunidadEvent.OnPermisoToggle(rol.id, permiso)) }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = permiso,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogoNuevoRol(
    uiState: GestionComunidadUiState,
    onEvent: (GestionComunidadEvent) -> Unit
) {
    val form = uiState.nuevoRolForm
    AlertDialog(
        onDismissRequest = { onEvent(GestionComunidadEvent.OnCerrarDialogoNuevoRol) },
        title = { Text("Nuevo rol") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = form.nombre,
                    onValueChange = { onEvent(GestionComunidadEvent.OnNuevoRolNombreChanged(it)) },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = form.nivel,
                    onValueChange = { onEvent(GestionComunidadEvent.OnNuevoRolNivelChanged(it)) },
                    label = { Text("Nivel (0-5)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = form.emoji,
                    onValueChange = { onEvent(GestionComunidadEvent.OnNuevoRolEmojiChanged(it)) },
                    label = { Text("Emoji") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = form.descripcion,
                    onValueChange = { onEvent(GestionComunidadEvent.OnNuevoRolDescripcionChanged(it)) },
                    label = { Text("Descripci\u00F3n") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                Text(
                    text = "Permisos:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold
                )
                PERMISOS_DISPONIBLES.forEach { permiso ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onEvent(GestionComunidadEvent.OnNuevoRolPermisoToggle(permiso)) }
                            .padding(vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = permiso in form.permisos,
                            onCheckedChange = { onEvent(GestionComunidadEvent.OnNuevoRolPermisoToggle(permiso)) }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = permiso, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onEvent(GestionComunidadEvent.OnGuardarNuevoRol) }) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = { onEvent(GestionComunidadEvent.OnCerrarDialogoNuevoRol) }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
private fun MiembroGestionCard(
    miembro: MiembroGestion,
    rolesDisponibles: List<RolInfo>,
    onRolChanged: (String) -> Unit,
    onExpulsar: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                if (miembro.usuario.imagen.isNotBlank()) {
                    AsyncImage(
                        model = miembro.usuario.imagen,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = miembro.usuario.nombre.take(2).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = miembro.usuario.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "@${miembro.usuario.nombreUsuario}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Box {
                RolBadge(rolInfo = miembro.rolInfo, onClick = { expanded = true })
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    rolesDisponibles.forEach { rol ->
                        DropdownMenuItem(
                            text = { Text("${rol.emoji} ${rol.nombre}") },
                            onClick = {
                                onRolChanged(rol.id)
                                expanded = false
                            }
                        )
                    }
                }
            }

            if (!miembro.esSolicitante) {
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = onExpulsar, modifier = Modifier.size(36.dp)) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Expulsar",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
