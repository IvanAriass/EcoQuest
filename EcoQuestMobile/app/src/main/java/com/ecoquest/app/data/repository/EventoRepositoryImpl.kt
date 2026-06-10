package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.EventoDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.repository.EventoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventoRepositoryImpl @Inject constructor(
    private val eventoDao: EventoDao
) : EventoRepository {

    override fun getAll(): Flow<List<Evento>> = eventoDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }

    override fun getById(id: Long): Flow<Evento?> = eventoDao.getById(id).map { entity ->
        entity?.toDomain()
    }

    override fun getByComunidad(comunidadId: Long): Flow<List<Evento>> =
        eventoDao.getByComunidad(comunidadId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun upsert(evento: Evento) = eventoDao.upsert(evento.toEntity())

    override suspend fun delete(evento: Evento) = eventoDao.delete(evento.toEntity())

    override suspend fun deleteAll() = eventoDao.deleteAll()
}
