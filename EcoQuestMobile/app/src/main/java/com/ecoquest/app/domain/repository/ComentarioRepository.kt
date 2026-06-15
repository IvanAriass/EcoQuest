package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Comentario
import kotlinx.coroutines.flow.Flow

interface ComentarioRepository {
    fun getByEventoId(eventoId: Long): Flow<List<Comentario>>
    suspend fun refreshComentarios(eventoId: Long)
    suspend fun crear(eventoId: Long, usuarioId: Long, texto: String, comentarioPadreId: Long? = null): Comentario?
    suspend fun obtenerRespuestas(eventoId: Long, comentarioId: Long): List<Comentario>
}
