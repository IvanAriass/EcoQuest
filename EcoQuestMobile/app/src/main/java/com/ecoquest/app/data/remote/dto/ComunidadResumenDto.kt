package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ComunidadResumenDto(
    @SerializedName("nombreComunidad") val nombreComunidad: String = "",
    @SerializedName("rol")             val rol: String = ""
)
