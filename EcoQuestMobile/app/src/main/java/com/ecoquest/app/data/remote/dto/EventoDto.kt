package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class EventoDto(
    @SerializedName("id")               val id: Long = 0,
    @SerializedName("nombre")           val nombre: String = "",
    @SerializedName("descripcion")      val descripcion: String = "",
    @SerializedName("fechaHora")        val fechaHora: String = "",
    @SerializedName("ubicacion")        val ubicacion: String = "",
    @SerializedName("imagen")           val imagen: String = "",
    @SerializedName("estado")           val estado: String = "",
    @SerializedName("motivoCancelacion") val motivoCancelacion: String? = null,
    @SerializedName("nombreComunidad")  val nombreComunidad: String? = null,
    @SerializedName("imagenComunidad")  val imagenComunidad: String? = null
)
