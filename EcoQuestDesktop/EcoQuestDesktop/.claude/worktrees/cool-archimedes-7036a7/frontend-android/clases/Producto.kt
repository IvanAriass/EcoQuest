package com.example.myapplication

data class Producto(
    var productoId: Int,
    var nombre: String,
    var descripcion: String,
    var puntos: Int
) {
    constructor() : this(
        productoId = 0,
        nombre = "",
        descripcion = "",
        puntos = 0
    )
}
