package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TransaccionPuntosDto(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("puntos") val puntos: Int = 0,
    @SerializedName("tipo") val tipo: String = "",
    @SerializedName("concepto") val concepto: String = "",
    @SerializedName("referenciaId") val referenciaId: Long? = null,
    @SerializedName("fecha") val fecha: String = ""
)
