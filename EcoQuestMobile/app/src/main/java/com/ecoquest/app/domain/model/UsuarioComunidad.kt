package com.ecoquest.app.domain.model

data class UsuarioComunidad(
    val id: Long = 0,
    val usuario: Usuario = Usuario(),
    val comunidad: Comunidad = Comunidad(),
    val rol: String = ""
)
