package com.example.myapplication

import java.time.LocalDateTime

data class UsuarioEvento(
    var usuarioEventoId: Int,
    var usuarioId: Int,
    var eventoId: Int,
    var fechaRegistro: LocalDateTime,
    var asistencia: Boolean
) {
    constructor() : this(
        usuarioEventoId = 0,
        usuarioId = 0,
        eventoId = 0,
        fechaRegistro = LocalDateTime.now(),
        asistencia = false
    )
}
