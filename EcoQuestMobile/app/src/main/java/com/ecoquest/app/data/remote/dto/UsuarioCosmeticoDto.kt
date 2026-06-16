package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UsuarioCosmeticoDto(
    @SerializedName("id")         val id: Long = 0,
    @SerializedName("aplicado")   val aplicado: Boolean = false,
    @SerializedName("fechaCanje") val fechaCanje: String = "",
    @SerializedName("producto")   val producto: ProductoDto? = null
)
