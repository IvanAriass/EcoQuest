package com.ecoquest.app.domain.model

data class Evento(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val fechaHora: String = "",
    val ubicacion: String = "",
    val imagen: String = "",
    val estado: String = "",
    val comunidadId: Long = 0,
    val creadorId: Long = 0,
    val comunidad: Comunidad? = null,
    val usuarios: List<Usuario> = emptyList()
)
