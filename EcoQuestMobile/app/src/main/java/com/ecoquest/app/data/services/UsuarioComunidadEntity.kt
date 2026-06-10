package com.ecoquest.app.data.services

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ecoquest.app.data.services.UsuarioEntity

@Entity(
    tableName = "usuario_comunidad",
    foreignKeys = [
        ForeignKey(
            entity = UsuarioEntity::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = ComunidadEntity::class,
            parentColumns = ["id"],
            childColumns = ["comunidadId"],
            onDelete = ForeignKey.Companion.CASCADE
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