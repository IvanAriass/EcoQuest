package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class ProductoEntity(
    @PrimaryKey val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val imagen: String = "",
    val precio: Int = 0,
    val categoria: String = ""
)
