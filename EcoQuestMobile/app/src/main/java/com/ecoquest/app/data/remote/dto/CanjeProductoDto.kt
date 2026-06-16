package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CanjeProductoDto(
    @SerializedName("id")             val id: Long = 0,
    @SerializedName("puntosGastados") val puntosGastados: Int = 0,
    @SerializedName("fecha")          val fecha: String = "",
    @SerializedName("producto")       val producto: ProductoDto? = null
)
