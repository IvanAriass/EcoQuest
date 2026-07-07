package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "mensajes",
    foreignKeys = [
        ForeignKey(
            entity = ComunidadEntity::class,
            parentColumns = ["id"],
            childColumns = ["comunidadId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("comunidadId")]
)
data class MensajeEntity(
    @PrimaryKey val id: Long = 0,
    val texto: String = "",
    val fechaHora: String = "",
    val usuarioId: Long = 0,
    val usuarioNombre: String = "",
    val usuarioNombreUsuario: String = "",
    val usuarioImagen: String = "",
    val comunidadId: Long = 0
)
