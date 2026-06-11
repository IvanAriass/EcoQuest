package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eventos")
data class EventoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nombre: String = "",
    val descripcion: String = "",
    val fechaHora: String = "",
    val ubicacion: String = "",
    val imagen: String = "",
    val estado: String = "",
    val comunidadId: Long = 0,
    val creadorId: Long = 0
)
