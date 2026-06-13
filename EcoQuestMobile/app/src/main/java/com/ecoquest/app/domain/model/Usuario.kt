package com.ecoquest.app.domain.model

data class Usuario(
    val id: Long = 0,
    val nombreUsuario: String = "",
    val contrasena: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val descripcion: String = "",
    val fechaNacimiento: String? = null,
    val email: String = "",
    val imagen: String = "",
    val comunidades: List<UsuarioComunidad> = emptyList(),
    val eventos: List<Evento> = emptyList()
) {
    val edad: Int
        get() {
            if (fechaNacimiento == null) return 0
            return try {
                val birthDate = java.time.LocalDate.parse(fechaNacimiento)
                java.time.Period.between(birthDate, java.time.LocalDate.now()).years
            } catch (_: Exception) { 0 }
        }
}
