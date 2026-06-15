package com.ecoquest.app.domain.model

data class Comentario(
    val id: Long = 0,
    val usuarioNombre: String = "",
    val usuarioNombreUsuario: String = "",
    val usuarioImagen: String = "",
    val usuarioId: Long = 0,
    val texto: String = "",
    val fechaHora: String = "",
    val comentarioPadreId: Long? = null,
    val cantidadRespuestas: Int = 0,
    val respuestas: List<Comentario> = emptyList()
)
