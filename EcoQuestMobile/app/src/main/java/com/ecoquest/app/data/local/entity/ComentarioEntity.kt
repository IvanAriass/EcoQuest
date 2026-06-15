package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comentarios")
data class ComentarioEntity(
    @PrimaryKey val id: Long = 0,
    val eventoId: Long = 0,
    val usuarioId: Long = 0,
    val usuarioNombre: String = "",
    val usuarioNombreUsuario: String = "",
    val usuarioImagen: String = "",
    val texto: String = "",
    val fechaHora: String = "",
    val comentarioPadreId: Long? = null,
    val cantidadRespuestas: Int = 0
)
