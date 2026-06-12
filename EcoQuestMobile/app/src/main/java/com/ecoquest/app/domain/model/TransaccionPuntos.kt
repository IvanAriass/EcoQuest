package com.ecoquest.app.domain.model

data class TransaccionPuntos(
    val id: Long = 0,
    val puntos: Int = 0,
    val tipo: String = "",
    val concepto: String = "",
    val referenciaId: Long? = null,
    val fecha: String = ""
)
