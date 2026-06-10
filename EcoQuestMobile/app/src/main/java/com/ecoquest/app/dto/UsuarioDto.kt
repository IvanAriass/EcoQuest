package com.ecoquest.app.data.dto

import com.google.gson.annotations.SerializedName

// contraseña no aparece — la API no la expone en UsuarioDTO.
data class UsuarioDto(
    @SerializedName("id")           val id: Long = 0,
    @SerializedName("nombreUsuario") val nombreUsuario: String = "",
    @SerializedName("nombre")       val nombre: String = "",
    @SerializedName("apellido")     val apellido: String = "",
    @SerializedName("descripcion")  val descripcion: String = "",
    @SerializedName("edad")         val edad: Int = 0,
    @SerializedName("email")        val email: String = "",
    @SerializedName("imagen")       val imagen: String = "",
    @SerializedName("comunidades")  val comunidades: List<ComunidadResumenDto> = emptyList(),
    @SerializedName("eventos")      val eventos: List<EventoDto> = emptyList()
)
