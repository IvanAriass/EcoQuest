package com.example.myapplication

data class Nivel(
    var nivelId: Int,
    var nombre: String,
    var descripcion: String,
    var puntos: Int,
    var icono: String
) {
    constructor() : this(
        nivelId = 0,
        nombre = "",
        descripcion = "",
        puntos = 0,
        icono = ""
    )
}
