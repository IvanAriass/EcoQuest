package com.ecoquest.app.ui.feature.gestion

sealed interface GestionComunidadEvent {
    data class OnNombreChanged(val nombre: String) : GestionComunidadEvent
    data class OnDescripcionChanged(val descripcion: String) : GestionComunidadEvent
    data object OnGuardarInfo : GestionComunidadEvent
    data class OnCambiarRol(val usuarioId: Long, val nuevoRol: String) : GestionComunidadEvent
    data class OnExpulsarMiembro(val usuarioId: Long) : GestionComunidadEvent
    data object OnConfirmarExpulsion : GestionComunidadEvent
    data object OnDismissDialog : GestionComunidadEvent
    data object OnRefrescar : GestionComunidadEvent

    data object OnAbrirDialogoNuevoRol : GestionComunidadEvent
    data object OnCerrarDialogoNuevoRol : GestionComunidadEvent
    data class OnNuevoRolNombreChanged(val nombre: String) : GestionComunidadEvent
    data class OnNuevoRolNivelChanged(val nivel: String) : GestionComunidadEvent
    data class OnNuevoRolEmojiChanged(val emoji: String) : GestionComunidadEvent
    data class OnNuevoRolDescripcionChanged(val descripcion: String) : GestionComunidadEvent
    data class OnNuevoRolPermisoToggle(val permiso: String) : GestionComunidadEvent
    data object OnGuardarNuevoRol : GestionComunidadEvent

    data class OnToggleRolExpandido(val rolId: String) : GestionComunidadEvent
    data class OnPermisoToggle(val rolId: String, val permiso: String) : GestionComunidadEvent
    data class OnEliminarRol(val rolId: String) : GestionComunidadEvent
    data object OnConfirmarEliminarRol : GestionComunidadEvent
}
