package com.ecoquest.app.models

import java.time.LocalDateTime

data class Evento(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val fechaHora: LocalDateTime = LocalDateTime.now(),
    val ubicacion: String = "",
    val imagen: String = "",
    val estado: String = "",
    val creadorId: Long = 0,
    val comunidad: Comunidad? = null,
    val usuarios: List<Usuario> = emptyList()
)