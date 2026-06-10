package com.example.myapplication

data class Comunidad(
    var comunidadId: Int,
    var nombre: String,
    var imagen: String,
    var descripcion: String,
    var rol: Rol,
    var usuarios: MutableList<Usuario> = mutableListOf(),
    var eventos: MutableList<Evento> = mutableListOf()
) {
    constructor() : this(
        comunidadId = 0,
        nombre = "",
        imagen = "",
        descripcion = "",
        rol = Rol(), // Asegúrate de tener un constructor por defecto en Rol
        usuarios = mutableListOf(),
        eventos = mutableListOf()
    )
}
