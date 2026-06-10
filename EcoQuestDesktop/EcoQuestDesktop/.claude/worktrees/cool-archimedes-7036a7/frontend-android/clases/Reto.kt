package com.example.myapplication

data class Reto(
    var retoId: Int,
    var nombre: String,
    var descripcion: String,
    var puntos: Int
) {
    constructor() : this(
        retoId = 0,
        nombre = "",
        descripcion = "",
        puntos = 0
    )
}
