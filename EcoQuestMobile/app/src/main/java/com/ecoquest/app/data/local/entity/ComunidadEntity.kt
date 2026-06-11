package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comunidades")
data class ComunidadEntity(
    @PrimaryKey val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val imagen: String = "",
    val estado: String = "",
    val creadorId: Long = 0
)
