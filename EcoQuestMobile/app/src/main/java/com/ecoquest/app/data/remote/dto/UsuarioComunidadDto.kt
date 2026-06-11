package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsuarioComunidadDto(
    @SerializedName("id")              val id: Long = 0,
    @SerializedName("usuario")         val usuario: UsuarioDto? = null,
    @SerializedName("comunidad")       val comunidad: ComunidadDto? = null,
    @SerializedName("rol")             val rol: String = "",
    @SerializedName("nombreUsuario")   val nombreUsuario: String? = null,
    @SerializedName("nombreComunidad") val nombreComunidad: String? = null
)
