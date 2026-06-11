package com.ecoquest.app.domain.model

data class Producto(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val imagen: String = "",
    val precio: Int = 0
)
