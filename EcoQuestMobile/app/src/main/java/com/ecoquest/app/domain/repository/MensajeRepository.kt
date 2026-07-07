package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Mensaje
import kotlinx.coroutines.flow.Flow

interface MensajeRepository {
    fun getByComunidad(comunidadId: Long): Flow<List<Mensaje>>
    suspend fun refreshMensajes(comunidadId: Long)
    suspend fun enviarMensaje(comunidadId: Long, usuarioId: Long, texto: String): Mensaje?
}
