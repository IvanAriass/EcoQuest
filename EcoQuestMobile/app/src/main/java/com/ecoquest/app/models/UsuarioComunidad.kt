package com.ecoquest.app.models

data class UsuarioComunidad(
    val id: Long = 0,
    val usuario: Usuario = Usuario(),
    val comunidad: Comunidad = Comunidad(),
    val rol: String = ""
)