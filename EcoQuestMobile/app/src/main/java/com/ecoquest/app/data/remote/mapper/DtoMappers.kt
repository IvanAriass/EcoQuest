package com.ecoquest.app.data.remote.mapper

import com.ecoquest.app.data.remote.dto.ComunidadDto
import com.ecoquest.app.data.remote.dto.EventoDto
import com.ecoquest.app.data.remote.dto.ProductoDto
import com.ecoquest.app.data.remote.dto.RetoDto
import com.ecoquest.app.data.remote.dto.TransaccionPuntosDto
import com.ecoquest.app.data.remote.dto.UsuarioDto
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

fun UsuarioDto.toDomain(): Usuario = Usuario(
    id = id,
    nombreUsuario = nombreUsuario,
    contrasena = "",
    nombre = nombre,
    apellido = apellido,
    descripcion = descripcion,
    fechaNacimiento = fechaNacimiento,
    email = email,
    imagen = imagen.toImageUrl("usuarios/imagen")
)

fun ComunidadDto.toDomain(): Comunidad = Comunidad(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen.toImageUrl("comunidades/imagen"),
    estado = estado,
    creadorId = 0
)

fun EventoDto.toDomain(): Evento = Evento(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    fechaHora = fechaHora,
    ubicacion = ubicacion,
    imagen = imagen.toImageUrl("eventos/imagen"),
    estado = estado,
    comunidadId = 0,
    creadorId = 0,
    nombreComunidad = nombreComunidad ?: ""
)

fun ProductoDto.toDomain(): Producto = Producto(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    imagen = imagen.toImageUrl("productos/imagen"),
    precio = precio,
    categoria = categoria?.nombre.orEmpty()
)

fun RetoDto.toDomain(): Reto = Reto(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    puntos = puntos,
    tipo = tipo,
    requisitoCantidad = requisitoCantidad,
    frecuencia = frecuencia
)

fun TransaccionPuntosDto.toDomain(): TransaccionPuntos = TransaccionPuntos(
    id = id,
    puntos = puntos,
    tipo = tipo,
    concepto = concepto,
    referenciaId = referenciaId,
    fecha = fecha
)
