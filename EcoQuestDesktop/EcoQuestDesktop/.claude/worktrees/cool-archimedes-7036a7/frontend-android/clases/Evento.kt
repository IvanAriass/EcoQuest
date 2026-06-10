package com.example.myapplication

import java.time.LocalDateTime

data class Evento(
    var eventoId: Int,
    var nombre: String,
    var ubicacion: String,
    var descripcion: String,
    var imagen: String,
    var fecha: LocalDateTime
) {
    constructor() : this(
        eventoId = 0,
        nombre = "",
        ubicacion = "",
        descripcion = "",
        imagen = "",
        fecha = LocalDateTime.now()
    )
}