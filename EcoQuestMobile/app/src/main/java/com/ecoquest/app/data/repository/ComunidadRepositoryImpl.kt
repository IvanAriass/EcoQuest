package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.ComunidadDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.repository.ComunidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComunidadRepositoryImpl @Inject constructor(
    private val comunidadDao: ComunidadDao
) : ComunidadRepository {

    override fun getAll(): Flow<List<Comunidad>> = comunidadDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }

    override fun getById(id: Long): Flow<Comunidad?> = comunidadDao.getById(id).map { entity ->
        entity?.toDomain()
    }

    override suspend fun upsert(comunidad: Comunidad) = comunidadDao.upsert(comunidad.toEntity())

    override suspend fun delete(comunidad: Comunidad) = comunidadDao.delete(comunidad.toEntity())

    override suspend fun deleteAll() = comunidadDao.deleteAll()
}
