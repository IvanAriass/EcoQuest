package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsuarioResumenDto(
    @SerializedName("nombreUsuario") val nombreUsuario: String = "",
    @SerializedName("rol")           val rol: String = ""
)
