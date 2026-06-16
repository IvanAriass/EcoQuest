package com.ecoquest.app.domain.model

data class Producto(
    val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val imagen: String = "",
    val precio: Int = 0,
    val tipo: String = "",
    val categoria: String = ""
) {
    val tipoLabel: String
        get() = when (tipo) {
            "MARCO" -> "Marco"
            "TEMA" -> "Tema"
            "INSIGNIA" -> "Insignia"
            "ESTILO_NOMBRE" -> "Estilo de Nombre"
            else -> categoria
        }

    val tipoIcon: String
        get() = tipo
}
