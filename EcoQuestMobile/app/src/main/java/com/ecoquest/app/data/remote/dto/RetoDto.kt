package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RetoDto(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("nombre") val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String = "",
    @SerializedName("puntos") val puntos: Int = 0,
    @SerializedName("tipo") val tipo: String = "",
    @SerializedName("requisitoCantidad") val requisitoCantidad: Int = 1,
    @SerializedName("frecuencia") val frecuencia: String = "ILIMITADA"
)
