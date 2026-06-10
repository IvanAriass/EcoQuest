package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "usuario_evento",
    primaryKeys = ["usuarioId", "eventoId"],
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EventoEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuarioId"), Index("eventoId")]
)
data class UsuarioEventoEntity(
    val usuarioId: Long = 0,
    val eventoId: Long = 0
)
