package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.ComentarioDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Comentario
import com.ecoquest.app.domain.repository.ComentarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComentarioRepositoryImpl @Inject constructor(
    private val comentarioDao: ComentarioDao,
    private val apiService: ApiService
) : ComentarioRepository {

    override fun getByEventoId(eventoId: Long): Flow<List<Comentario>> =
        comentarioDao.getByEventoId(eventoId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refreshComentarios(eventoId: Long) {
        runCatching {
            val comentarios = apiService.getComentarios(eventoId)
            comentarios.forEach { dto ->
                val entity = dto.toDomain().toEntity(eventoId)
                comentarioDao.upsert(entity)
            }
        }
    }

    override suspend fun crear(eventoId: Long, usuarioId: Long, texto: String, comentarioPadreId: Long?): Comentario? {
        return runCatching {
            val body = mutableMapOf<String, Any>(
                "usuarioId" to usuarioId,
                "texto" to texto
            )
            if (comentarioPadreId != null) {
                body["comentarioPadreId"] = comentarioPadreId
            }
            val dto = apiService.crearComentario(eventoId, body)
            val entity = dto.toDomain().toEntity(eventoId)
            comentarioDao.upsert(entity)
            entity.toDomain()
        }.getOrNull()
    }

    override suspend fun obtenerRespuestas(eventoId: Long, comentarioId: Long): List<Comentario> {
        return runCatching {
            val dtos = apiService.getRespuestas(eventoId, comentarioId)
            dtos.map { it.toDomain() }
        }.getOrDefault(emptyList())
    }
}
