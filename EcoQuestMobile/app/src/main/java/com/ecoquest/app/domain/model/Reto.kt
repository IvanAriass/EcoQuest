package com.ecoquest.app.domain.model

data class Reto(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val puntos: Int = 0,
    val tipo: String = "",
    val requisitoCantidad: Int = 1,
    val frecuencia: String = "ILIMITADA"
) {
    val frecuenciaLabel: String
        get() = when (frecuencia) {
            "DIARIA" -> "Diario"
            "UNICA" -> "Una vez"
            "ILIMITADA" -> "Ilimitado"
            else -> frecuencia
        }
}
