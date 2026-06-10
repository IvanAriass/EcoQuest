package com.ecoquest.app.data.services

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ecoquest.app.data.services.ComunidadEntity

@Entity(
    tableName = "eventos",
    foreignKeys = [
        ForeignKey(
            entity = ComunidadEntity::class,
            parentColumns = ["id"],
            childColumns = ["comunidadId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index("comunidadId")]
)
data class EventoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val fechaHora: String = "",   // guardado como String ISO: "2026-05-10T09:00:00"
    val ubicacion: String = "",
    val imagen: String = "",
    val estado: String = "",
    val comunidadId: Long = 0,
    val creadorId: Long = 0
)