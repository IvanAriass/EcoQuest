package com.ecoquest.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriaDto(
    @SerializedName("id")     val id: Long = 0,
    @SerializedName("nombre") val nombre: String = ""
)

data class ProductoDto(
    @SerializedName("id")          val id: Long = 0,
    @SerializedName("nombre")      val nombre: String = "",
    @SerializedName("descripcion") val descripcion: String = "",
    @SerializedName("imagen")      val imagen: String = "",
    @SerializedName("precio")      val precio: Int = 0,
    @SerializedName("tipo")        val tipo: String = "",
    @SerializedName("categoria")   val categoria: CategoriaDto? = null
)
