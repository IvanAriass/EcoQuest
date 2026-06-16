package com.ecoquest.app.data.local.mapper

import com.ecoquest.app.data.local.entity.ComentarioEntity
import com.ecoquest.app.data.local.entity.ComunidadEntity
import com.ecoquest.app.data.local.entity.EventoEntity
import com.ecoquest.app.data.local.entity.ProductoEntity
import com.ecoquest.app.data.local.entity.RetoEntity
import com.ecoquest.app.data.local.entity.TransaccionPuntosEntity
import com.ecoquest.app.data.local.entity.UsuarioEntity
import com.ecoquest.app.domain.model.Comentario
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Producto
import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.model.Usuario

private const val API_BASE = "http://10.0.2.2:9000/api"
private const val LOCALHOST = "http://localhost:9000"

private fun String.toImageUrl(entityPath: String): String {
    if (isBlank()) return this
    return if (startsWith("http://") || startsWith("https://"))
        replace(LOCALHOST, "http://10.0.2.2:9000")
    else
        "$API_BASE/$entityPath/$this"
}

fun UsuarioEntity.toDomain(): Usuario = Usuario(
    id = id,
    nombreUsuario = nombreUsuario,
    contrasena = contrasena,
    nombre = nombre,
    apellido = apellido,
    descripcion = descripcion,
    fechaNacimiento = fechaNacimiento,
    email = email,
    imagen = imagen.toImageUrl("usuarios/imagen")
)

fun Usuario.toEntity(): UsuarioEntity = UsuarioEntity(
    id = id,
    nombreUsuario = nombreUsuario,
    contrasena = contrasena,
    nombre = nombre,
    apellido = apellido,
    descripcion = descripcion,
    fechaNacimiento = fechaNacimiento,
    email = email,
    imagen = imagen
)

fun ComunidadEntity.toDomain(): Comunidad = Comunidad(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen.toImageUrl("comunidades/imagen"),
    estado = estado,
    creadorId = creadorId
)

fun Comunidad.toEntity(): ComunidadEntity = ComunidadEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen,
    estado = estado,
    creadorId = creadorId
)

fun EventoEntity.toDomain(): Evento = Evento(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    fechaHora = fechaHora,
    ubicacion = ubicacion,
    imagen = imagen.toImageUrl("eventos/imagen"),
    estado = estado,
    comunidadId = comunidadId,
    creadorId = creadorId,
    nombreComunidad = nombreComunidad
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
    creadorId = creadorId,
    nombreComunidad = nombreComunidad
)

fun ProductoEntity.toDomain(): Producto = Producto(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen.toImageUrl("productos/imagen"),
    precio = precio,
    tipo = tipo,
    categoria = categoria
)

fun Producto.toEntity(): ProductoEntity = ProductoEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen,
    precio = precio,
    tipo = tipo,
    categoria = categoria
)

fun RetoEntity.toDomain(): Reto = Reto(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    puntos = puntos,
    tipo = tipo,
    requisitoCantidad = requisitoCantidad,
    frecuencia = frecuencia
)

fun ComentarioEntity.toDomain(): Comentario = Comentario(
    id = id,
    usuarioNombre = usuarioNombre,
    usuarioNombreUsuario = usuarioNombreUsuario.ifBlank { usuarioNombre },
    usuarioImagen = usuarioImagen.toImageUrl("usuarios/imagen"),
    usuarioId = usuarioId,
    texto = texto,
    fechaHora = fechaHora,
    comentarioPadreId = comentarioPadreId,
    cantidadRespuestas = cantidadRespuestas
)

fun Comentario.toEntity(eventoId: Long): ComentarioEntity = ComentarioEntity(
    id = id,
    eventoId = eventoId,
    usuarioId = usuarioId,
    usuarioNombre = usuarioNombre,
    usuarioNombreUsuario = usuarioNombreUsuario,
    usuarioImagen = usuarioImagen,
    texto = texto,
    fechaHora = fechaHora,
    comentarioPadreId = comentarioPadreId,
    cantidadRespuestas = cantidadRespuestas
)

fun TransaccionPuntosEntity.toDomain(): TransaccionPuntos = TransaccionPuntos(
    id = id,
    puntos = puntos,
    tipo = tipo,
    concepto = concepto,
    referenciaId = referenciaId,
    fecha = fecha
)
