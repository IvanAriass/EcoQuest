package com.ecoquest.app.domain.model

data class RolInfo(
    val id: String = "",
    val nombre: String = "",
    val emoji: String = "",
    val nivel: Int = 0,
    val descripcion: String = "",
    val permisos: List<String> = emptyList()
)
