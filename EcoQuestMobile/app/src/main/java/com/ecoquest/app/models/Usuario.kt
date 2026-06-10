package com.ecoquest.app.models

import java.time.LocalDateTime

data class Usuario(
    val id: Long = 0,
    val nombreUsuario: String = "",
    val contraseña: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val descripcion: String = "",
    val edad: Int = 0,
    val email: String = "",
    val imagen: String = "",
    val comunidades: List<UsuarioComunidad> = emptyList(),
    val eventos: List<Evento> = emptyList()
)