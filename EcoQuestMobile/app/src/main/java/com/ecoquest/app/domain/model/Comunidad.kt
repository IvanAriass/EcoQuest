package com.ecoquest.app.domain.model

data class Comunidad(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val imagen: String = "",
    val estado: String = "",
    val creadorId: Long = 0,
    val eventos: List<Evento> = emptyList(),
    val usuarios: List<UsuarioComunidad> = emptyList()
)
