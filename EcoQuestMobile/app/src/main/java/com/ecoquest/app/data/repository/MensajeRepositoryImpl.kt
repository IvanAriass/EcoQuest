package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.MensajeDao
import com.ecoquest.app.data.local.entity.MensajeEntity
import com.ecoquest.app.data.local.mapper.toDomain as localToDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain as remoteToDomain
import com.ecoquest.app.domain.model.Mensaje
import com.ecoquest.app.domain.repository.MensajeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MensajeRepositoryImpl @Inject constructor(
    private val mensajeDao: MensajeDao,
    private val apiService: ApiService
) : MensajeRepository {

    override fun getByComunidad(comunidadId: Long): Flow<List<Mensaje>> =
        mensajeDao.getByComunidad(comunidadId).map { entities ->
            entities.map { it.localToDomain() }
        }

    override suspend fun refreshMensajes(comunidadId: Long) {
        runCatching {
            val mensajes = apiService.getMensajes(comunidadId)
            val domain = mensajes.map { it.remoteToDomain() }
            mensajeDao.deleteByComunidad(comunidadId)
            mensajeDao.upsertAll(domain.map { it.toEntity() })
        }
    }

    override suspend fun enviarMensaje(comunidadId: Long, usuarioId: Long, texto: String): Mensaje? {
        return runCatching {
            val dto = apiService.crearMensaje(comunidadId, mapOf(
                "usuarioId" to usuarioId,
                "texto" to texto
            ))
            val mensaje = dto.remoteToDomain()
            mensajeDao.upsertAll(listOf(mensaje.toEntity()))
            mensaje
        }.getOrNull()
    }
}
