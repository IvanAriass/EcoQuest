package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "retos")
data class RetoEntity(
    @PrimaryKey val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val puntos: Int = 0,
    val tipo: String = "",
    val requisitoCantidad: Int = 1,
    val frecuencia: String = "ILIMITADA"
)
