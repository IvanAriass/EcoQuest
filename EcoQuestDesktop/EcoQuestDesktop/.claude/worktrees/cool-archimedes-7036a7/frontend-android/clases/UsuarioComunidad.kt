package com.example.myapplication

data class UsuarioComunidad(
    var usuarioComunidadId: Int,
    var usuarioId: Int,
    var comunidadId: Int,
    var rol: Rol
) {
    constructor() : this(
        usuarioComunidadId = 0,
        usuarioId = 0,
        comunidadId = 0,
        rol = Rol()
    )
}
