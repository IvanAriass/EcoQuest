package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MensajeDto(
    @SerializedName("id")                val id: Long = 0,
    @SerializedName("texto")             val texto: String = "",
    @SerializedName("fechaHora")         val fechaHora: String = "",
    @SerializedName("usuarioId")         val usuarioId: Long = 0,
    @SerializedName("usuarioNombre")     val usuarioNombre: String = "",
    @SerializedName("usuarioNombreUsuario") val usuarioNombreUsuario: String = "",
    @SerializedName("usuarioImagen")     val usuarioImagen: String? = null,
    @SerializedName("comunidadId")       val comunidadId: Long = 0
)
