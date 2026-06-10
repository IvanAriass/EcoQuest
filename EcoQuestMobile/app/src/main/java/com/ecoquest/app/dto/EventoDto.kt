package com.ecoquest.app.data.dto

import com.google.gson.annotations.SerializedName

// Evento completo tal como lo serializa la API.
// comunidad y usuarios están anotados con @JsonIgnore en Spring → no aparecen en el JSON.
data class EventoDto(
    @SerializedName("id")          val id: Long = 0,
    @SerializedName("nombre")      val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String = "",
    @SerializedName("fechaHora")   val fechaHora: String = "",   // "2026-05-10T09:00:00"
    @SerializedName("ubicacion")   val ubicacion: String = "",
    @SerializedName("imagen")      val imagen: String = "",
    @SerializedName("estado")      val estado: String = ""
)
