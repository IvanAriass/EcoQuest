package com.example.myapplication

import java.time.LocalDateTime

data class Usuario(
    var usuarioId: Int,
    var contraseña: String,
    var admin: Boolean,
    var nombreUsuario: String,
    var nombre: String,
    var apellidos: String,
    var correo: String,
    var imagen: String,
    var descripcion: String,
    var edad: LocalDateTime,
    var comunidades: MutableList<Comunidad> = mutableListOf(),
    var puntos: Int,
    var puntosExperiencia: Int,
    var nivel: Nivel,
    var productos: MutableList<Producto> = mutableListOf()
) {
    constructor() : this(
        usuarioId = 0,
        contraseña = "",
        admin = false,
        nombreUsuario = "",
        nombre = "",
        apellidos = "",
        correo = "",
        imagen = "",
        descripcion = "",
        edad = LocalDateTime.now(),
        puntos = 0,
        puntosExperiencia = 0,
        nivel = Nivel(),
        comunidades = mutableListOf(),
        productos = mutableListOf()
    )
}
