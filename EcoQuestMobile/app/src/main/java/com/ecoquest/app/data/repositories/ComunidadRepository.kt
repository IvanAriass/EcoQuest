package com.ecoquest.app.data.repositories

import com.ecoquest.app.data.room.ComunidadDao
import com.ecoquest.app.data.services.ComunidadEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComunidadRepository @Inject constructor(
    private val comunidadDao: ComunidadDao
) {

    fun getAll(): Flow<List<ComunidadEntity>> = comunidadDao.getAll()

    fun getById(id: Long): Flow<ComunidadEntity?> = comunidadDao.getById(id)

    suspend fun insertAllIfAbsent(comunidades: List<ComunidadEntity>) = comunidadDao.insertAllIfAbsent(comunidades)

    suspend fun upsertAll(comunidades: List<ComunidadEntity>) = comunidadDao.upsertAll(comunidades)

    suspend fun upsert(comunidad: ComunidadEntity) = comunidadDao.upsert(comunidad)

    suspend fun delete(comunidad: ComunidadEntity) = comunidadDao.delete(comunidad)

    suspend fun deleteAll() = comunidadDao.deleteAll()
}