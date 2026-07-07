package com.ecoquest.app.ui.feature.gestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.RolInfo
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.ComunidadRepository
import com.ecoquest.app.domain.repository.RolRepository
import com.ecoquest.app.managers.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MiembroGestion(
    val usuario: Usuario,
    val rolInfo: RolInfo,
    val esSolicitante: Boolean = false
)

data class RolEditable(
    val id: String,
    val nombre: String,
    val emoji: String,
    val nivel: Int,
    val descripcion: String,
    val permisos: List<String>,
    val expandido: Boolean = false
)

data class NuevoRolForm(
    val nombre: String = "",
    val nivel: String = "1",
    val emoji: String = "",
    val descripcion: String = "",
    val permisos: Set<String> = emptySet()
)

data class GestionComunidadUiState(
    val comunidad: Comunidad? = null,
    val nombreEdit: String = "",
    val descripcionEdit: String = "",
    val miembros: List<MiembroGestion> = emptyList(),
    val rolesDisponibles: List<RolInfo> = emptyList(),
    val rolesEditables: List<RolEditable> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val mensajeExito: String? = null,
    val dialogExpulsar: Long? = null,
    val mostrarDialogoNuevoRol: Boolean = false,
    val nuevoRolForm: NuevoRolForm = NuevoRolForm(),
    val dialogoEliminarRol: String? = null
)

val PERMISOS_DISPONIBLES = listOf(
    "LEER_MENSAJES",
    "ESCRIBIR_MENSAJES",
    "CREAR_EVENTOS",
    "MODERAR_CHAT",
    "GESTIONAR_MIEMBROS",
    "ADMINISTRAR"
)

@HiltViewModel
class GestionComunidadViewModel @Inject constructor(
    private val comunidadRepository: ComunidadRepository,
    private val rolRepository: RolRepository,
    private val tokenManager: TokenManager,
    private val apiService: ApiService
) : ViewModel() {

    private val _state = MutableStateFlow(GestionComunidadUiState())
    val state: StateFlow<GestionComunidadUiState> = _state.asStateFlow()

    private val usuarioId: Long
        get() = tokenManager.getUsuarioId().takeIf { it > 0 } ?: 1L

    fun cargar(comunidadId: Long) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, mensajeExito = null) }
            try {
                rolRepository.refreshRoles()
                val roles = rolRepository.getRoles()
                comunidadRepository.refreshComunidades()
                val comunidad = comunidadRepository.getById(comunidadId).first()

                val relaciones = apiService.usuariosDeComunidad(comunidadId)
                val miembros = relaciones.mapNotNull { uc ->
                    val usuarioDto = uc.usuario ?: return@mapNotNull null
                    val usuario = usuarioDto.toDomain()
                    val rolInfo = rolRepository.getRolById(uc.rol)
                    MiembroGestion(
                        usuario = usuario,
                        rolInfo = rolInfo,
                        esSolicitante = usuario.id == usuarioId
                    )
                }

                val rolesApi = apiService.getRolesGestion(comunidadId)
                val rolesEditables = rolesApi.map { it.toDomain() }.map { rol ->
                    RolEditable(
                        id = rol.id,
                        nombre = rol.nombre,
                        emoji = rol.emoji,
                        nivel = rol.nivel,
                        descripcion = rol.descripcion,
                        permisos = rol.permisos
                    )
                }

                _state.update {
                    it.copy(
                        comunidad = comunidad,
                        nombreEdit = comunidad?.nombre ?: "",
                        descripcionEdit = comunidad?.descripcion ?: "",
                        rolesDisponibles = roles,
                        rolesEditables = rolesEditables,
                        miembros = miembros,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Error al cargar datos") }
            }
        }
    }

    fun onEvent(event: GestionComunidadEvent) {
        val comunidadId = _state.value.comunidad?.id ?: return
        when (event) {
            is GestionComunidadEvent.OnNombreChanged -> {
                _state.update { it.copy(nombreEdit = event.nombre) }
            }
            is GestionComunidadEvent.OnDescripcionChanged -> {
                _state.update { it.copy(descripcionEdit = event.descripcion) }
            }
            is GestionComunidadEvent.OnGuardarInfo -> guardarInfo()
            is GestionComunidadEvent.OnCambiarRol -> cambiarRol(event.usuarioId, event.nuevoRol)
            is GestionComunidadEvent.OnExpulsarMiembro -> {
                _state.update { it.copy(dialogExpulsar = event.usuarioId) }
            }
            is GestionComunidadEvent.OnConfirmarExpulsion -> confirmarExpulsion()
            is GestionComunidadEvent.OnDismissDialog -> {
                _state.update { it.copy(dialogExpulsar = null, error = null, mensajeExito = null, dialogoEliminarRol = null) }
            }
            is GestionComunidadEvent.OnRefrescar -> {
                _state.value.comunidad?.let { cargar(it.id) }
            }

            is GestionComunidadEvent.OnAbrirDialogoNuevoRol -> {
                _state.update { it.copy(mostrarDialogoNuevoRol = true, nuevoRolForm = NuevoRolForm()) }
            }
            is GestionComunidadEvent.OnCerrarDialogoNuevoRol -> {
                _state.update { it.copy(mostrarDialogoNuevoRol = false) }
            }
            is GestionComunidadEvent.OnNuevoRolNombreChanged -> {
                _state.update { it.copy(nuevoRolForm = it.nuevoRolForm.copy(nombre = event.nombre)) }
            }
            is GestionComunidadEvent.OnNuevoRolNivelChanged -> {
                _state.update { it.copy(nuevoRolForm = it.nuevoRolForm.copy(nivel = event.nivel)) }
            }
            is GestionComunidadEvent.OnNuevoRolEmojiChanged -> {
                _state.update { it.copy(nuevoRolForm = it.nuevoRolForm.copy(emoji = event.emoji)) }
            }
            is GestionComunidadEvent.OnNuevoRolDescripcionChanged -> {
                _state.update { it.copy(nuevoRolForm = it.nuevoRolForm.copy(descripcion = event.descripcion)) }
            }
            is GestionComunidadEvent.OnNuevoRolPermisoToggle -> {
                val actual = _state.value.nuevoRolForm.permisos
                val nuevas = if (event.permiso in actual) actual - event.permiso else actual + event.permiso
                _state.update { it.copy(nuevoRolForm = it.nuevoRolForm.copy(permisos = nuevas)) }
            }
            is GestionComunidadEvent.OnGuardarNuevoRol -> guardarNuevoRol()

            is GestionComunidadEvent.OnToggleRolExpandido -> {
                _state.update { state ->
                    state.copy(rolesEditables = state.rolesEditables.map {
                        if (it.id == event.rolId) it.copy(expandido = !it.expandido) else it
                    })
                }
            }
            is GestionComunidadEvent.OnPermisoToggle -> {
                val idx = _state.value.rolesEditables.indexOfFirst { it.id == event.rolId }
                if (idx == -1) return
                val rol = _state.value.rolesEditables[idx]
                val nuevosPermisos = if (event.permiso in rol.permisos)
                    rol.permisos - event.permiso
                else
                    rol.permisos + event.permiso
                actualizarPermisosRol(rol.id, nuevosPermisos)
            }
            is GestionComunidadEvent.OnEliminarRol -> {
                _state.update { it.copy(dialogoEliminarRol = event.rolId) }
            }
            is GestionComunidadEvent.OnConfirmarEliminarRol -> {
                _state.value.dialogoEliminarRol?.let { eliminarRol(it) }
            }
        }
    }

    private fun guardarInfo() {
        val comunidad = _state.value.comunidad ?: return
        viewModelScope.launch {
            try {
                apiService.gestionarComunidad(
                    id = comunidad.id,
                    body = mapOf("nombre" to _state.value.nombreEdit, "descripcion" to _state.value.descripcionEdit),
                    solicitanteId = usuarioId
                )
                _state.update { it.copy(mensajeExito = "Informaci\u00F3n actualizada") }
                cargar(comunidad.id)
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al guardar") }
            }
        }
    }

    private fun cambiarRol(miembroId: Long, nuevoRol: String) {
        val comunidad = _state.value.comunidad ?: return
        viewModelScope.launch {
            try {
                apiService.cambiarRol(miembroId, comunidad.id, nuevoRol)
                _state.update { it.copy(mensajeExito = "Rol actualizado") }
                cargar(comunidad.id)
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al cambiar rol") }
            }
        }
    }

    private fun expulsar(miembroId: Long) {
        val comunidad = _state.value.comunidad ?: return
        viewModelScope.launch {
            try {
                apiService.expulsarMiembro(miembroId, comunidad.id, usuarioId)
                _state.update { it.copy(dialogExpulsar = null, mensajeExito = "Miembro expulsado") }
                cargar(comunidad.id)
            } catch (e: Exception) {
                _state.update { it.copy(dialogExpulsar = null, error = "Error al expulsar") }
            }
        }
    }

    fun confirmarExpulsion() {
        val miembroId = _state.value.dialogExpulsar ?: return
        expulsar(miembroId)
    }

    private fun guardarNuevoRol() {
        val form = _state.value.nuevoRolForm
        val comunidad = _state.value.comunidad ?: return
        if (form.nombre.isBlank()) {
            _state.update { it.copy(error = "El nombre del rol es obligatorio") }
            return
        }
        viewModelScope.launch {
            try {
                apiService.crearRol(
                    comunidadId = comunidad.id,
                    solicitanteId = usuarioId,
                    body = mapOf(
                        "nombre" to form.nombre.uppercase(),
                        "nivel" to (form.nivel.toIntOrNull() ?: 1),
                        "emoji" to form.emoji,
                        "descripcion" to form.descripcion,
                        "permisos" to form.permisos.toList()
                    )
                )
                _state.update { it.copy(mostrarDialogoNuevoRol = false, mensajeExito = "Rol creado") }
                cargar(comunidad.id)
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al crear rol") }
            }
        }
    }

    private fun actualizarPermisosRol(rolId: String, permisos: List<String>) {
        val comunidad = _state.value.comunidad ?: return
        viewModelScope.launch {
            try {
                apiService.actualizarPermisosRol(
                    comunidadId = comunidad.id,
                    rolId = rolId.toLongOrNull() ?: return@launch,
                    solicitanteId = usuarioId,
                    body = mapOf("permisos" to permisos)
                )
                _state.update { it.copy(mensajeExito = "Permisos actualizados") }
                cargar(comunidad.id)
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al actualizar permisos") }
            }
        }
    }

    private fun eliminarRol(rolId: String) {
        val comunidad = _state.value.comunidad ?: return
        viewModelScope.launch {
            try {
                apiService.eliminarRol(
                    comunidadId = comunidad.id,
                    rolId = rolId.toLongOrNull() ?: return@launch,
                    solicitanteId = usuarioId
                )
                _state.update { it.copy(dialogoEliminarRol = null, mensajeExito = "Rol eliminado") }
                cargar(comunidad.id)
            } catch (e: Exception) {
                _state.update { it.copy(error = "Error al eliminar rol") }
            }
        }
    }
}
