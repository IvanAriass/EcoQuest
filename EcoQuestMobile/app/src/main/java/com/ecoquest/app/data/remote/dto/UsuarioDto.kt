package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsuarioDto(
    @SerializedName("id")              val id: Long = 0,
    @SerializedName("nombreUsuario")   val nombreUsuario: String = "",
    @SerializedName("nombre")          val nombre: String = "",
    @SerializedName("apellido")        val apellido: String = "",
    @SerializedName("descripcion")     val descripcion: String = "",
    @SerializedName("fechaNacimiento") val fechaNacimiento: String? = null,
    @SerializedName("email")           val email: String = "",
    @SerializedName("imagen")          val imagen: String = "",
    @SerializedName("bloqueado")       val bloqueado: Boolean = false,
    @SerializedName("causaBloqueo")    val causaBloqueo: String? = null,
    @SerializedName("fechaBloqueo")    val fechaBloqueo: String? = null,
    @SerializedName("comunidades")     val comunidades: List<ComunidadResumenDto> = emptyList(),
    @SerializedName("eventos")         val eventos: List<EventoDto> = emptyList()
)
