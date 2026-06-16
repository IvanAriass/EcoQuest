package com.ecoquest.app.domain.model

data class Mensaje(
    val id: Long = 0,
    val texto: String = "",
    val fechaHora: String = "",
    val usuarioId: Long = 0,
    val usuarioNombre: String = "",
    val usuarioNombreUsuario: String = "",
    val usuarioImagen: String = "",
    val comunidadId: Long = 0
)
