package com.ecoquest.app.data.services

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
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = EventoEntity::class,
            parentColumns = ["id"],
            childColumns = ["eventoId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("usuarioId"), Index("eventoId")]
)
data class UsuarioEventoEntity(
    val usuarioId: Long = 0,
    val eventoId: Long = 0
)