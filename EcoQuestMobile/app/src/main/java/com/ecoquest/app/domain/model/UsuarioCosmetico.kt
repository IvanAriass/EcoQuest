package com.ecoquest.app.domain.model

data class UsuarioCosmetico(
    val id: Long = 0,
    val productoId: Long = 0,
    val productoNombre: String = "",
    val productoTipo: String = "",
    val productoDescripcion: String = "",
    val productoImagen: String = "",
    val productoPrecio: Int = 0,
    val aplicado: Boolean = false,
    val fechaCanje: String = ""
) {
    val tipoLabel: String
        get() = when (productoTipo) {
            "MARCO" -> "Marco"
            "TEMA" -> "Tema"
            "INSIGNIA" -> "Insignia"
            "ESTILO_NOMBRE" -> "Estilo de Nombre"
            else -> productoTipo
        }
}
