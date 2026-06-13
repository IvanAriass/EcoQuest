package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transacciones_puntos")
data class TransaccionPuntosEntity(
    @PrimaryKey val id: Long = 0,
    val usuarioId: Long = 0,
    val puntos: Int = 0,
    val tipo: String = "",
    val concepto: String = "",
    val referenciaId: Long? = null,
    val fecha: String = ""
)
