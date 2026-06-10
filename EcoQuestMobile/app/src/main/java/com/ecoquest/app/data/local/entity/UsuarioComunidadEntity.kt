package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuario_comunidad",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ComunidadEntity::class,
            parentColumns = ["id"],
            childColumns = ["comunidadId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuarioId"), Index("comunidadId")]
)
data class UsuarioComunidadEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuarioId: Long = 0,
    val comunidadId: Long = 0,
    val rol: String = ""
)
