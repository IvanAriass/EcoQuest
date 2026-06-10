package com.ecoquest.app.data.dto

import com.google.gson.annotations.SerializedName

// Resumen de comunidad dentro de UsuarioDto: solo nombreComunidad y rol.
data class ComunidadResumenDto(
    @SerializedName("nombreComunidad") val nombreComunidad: String = "",
    @SerializedName("rol")             val rol: String = ""
)
