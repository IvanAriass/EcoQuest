package com.ecoquest.app.data.local.mapper

import com.ecoquest.app.data.local.entity.ComunidadEntity
import com.ecoquest.app.data.local.entity.EventoEntity
import com.ecoquest.app.data.local.entity.UsuarioEntity
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario

fun UsuarioEntity.toDomain(): Usuario = Usuario(
    id = id,
    nombreUsuario = nombreUsuario,
    nombre = nombre,
    apellido = apellido,
    descripcion = descripcion,
    edad = edad,
    email = email,
    imagen = imagen
)

fun Usuario.toEntity(): UsuarioEntity = UsuarioEntity(
    id = id,
    nombreUsuario = nombreUsuario,
    nombre = nombre,
    apellido = apellido,
    descripcion = descripcion,
    edad = edad,
    email = email,
    imagen = imagen
)

fun ComunidadEntity.toDomain(): Comunidad = Comunidad(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen,
    creadorId = creadorId
)

fun Comunidad.toEntity(): ComunidadEntity = ComunidadEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen,
    creadorId = creadorId
)

fun EventoEntity.toDomain(): Evento = Evento(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    fechaHora = fechaHora,
    ubicacion = ubicacion,
    imagen = imagen,
    estado = estado,
    comunidadId = comunidadId,
    creadorId = creadorId
)

fun Evento.toEntity(): EventoEntity = EventoEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    fechaHora = fechaHora,
    ubicacion = ubicacion,
    imagen = imagen,
    estado = estado,
    comunidadId = comunidadId,
    creadorId = creadorId
)
