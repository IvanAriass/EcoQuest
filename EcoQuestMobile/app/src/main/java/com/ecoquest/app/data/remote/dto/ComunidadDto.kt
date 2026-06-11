package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ComunidadDto(
    @SerializedName("id")               val id: Long = 0,
    @SerializedName("nombre")           val nombre: String = "",
    @SerializedName("descripcion")      val descripcion: String = "",
    @SerializedName("imagen")           val imagen: String = "",
    @SerializedName("estado")           val estado: String = "",
    @SerializedName("motivoCancelacion") val motivoCancelacion: String? = null,
    @SerializedName("roles")            val roles: String? = null,
    @SerializedName("usuarios")         val usuarios: List<UsuarioResumenDto> = emptyList(),
    @SerializedName("eventos")          val eventos: List<EventoDto> = emptyList()
)
