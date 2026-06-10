package com.ecoquest.app.data.repositories

import com.ecoquest.app.data.room.EventoDao
import com.ecoquest.app.data.services.EventoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventoRepository @Inject constructor(
    private val eventoDao: EventoDao
) {

    fun getAll(): Flow<List<EventoEntity>> = eventoDao.getAll()

    suspend fun insertAllIfAbsent(eventos: List<EventoEntity>) = eventoDao.insertAllIfAbsent(eventos)

    fun getById(id: Long): Flow<EventoEntity?> = eventoDao.getById(id)

    fun getByComunidad(comunidadId: Long): Flow<List<EventoEntity>> = eventoDao.getByComunidad(comunidadId)

    suspend fun upsertAll(eventos: List<EventoEntity>) = eventoDao.upsertAll(eventos)

    suspend fun upsert(evento: EventoEntity) = eventoDao.upsert(evento)

    suspend fun delete(evento: EventoEntity) = eventoDao.delete(evento)

    suspend fun deleteAll() = eventoDao.deleteAll()
}