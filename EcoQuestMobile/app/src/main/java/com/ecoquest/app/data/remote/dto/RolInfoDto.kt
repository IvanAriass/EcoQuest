package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RolInfoDto(
    @SerializedName("id")         val id: String = "",
    @SerializedName("nombre")     val nombre: String = "",
    @SerializedName("emoji")      val emoji: String = "",
    @SerializedName("nivel")      val nivel: Int = 0,
    @SerializedName("descripcion") val descripcion: String = "",
    @SerializedName("permisos")   val permisos: List<String> = emptyList()
)
