package com.ecoquest.app.domain.model

data class Juego(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val icono: String = "SportsEsports",
    val colorHex: String = "#4CAF50"
)
