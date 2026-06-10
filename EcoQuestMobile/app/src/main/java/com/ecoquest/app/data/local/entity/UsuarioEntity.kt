package com.ecoquest.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey val id: Long = 0,
    val nombreUsuario: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val descripcion: String = "",
    val edad: Int = 0,
    val email: String = "",
    val imagen: String = ""
)
