package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario_cosmeticos")
data class UsuarioCosmeticoEntity(
    @PrimaryKey val id: Long = 0,
    val usuarioId: Long = 0,
    val productoId: Long = 0,
    val productoNombre: String = "",
    val productoTipo: String = "",
    val productoDescripcion: String = "",
    val productoImagen: String = "",
    val productoPrecio: Int = 0,
    val aplicado: Boolean = false,
    val fechaCanje: String = ""
)
