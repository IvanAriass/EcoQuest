package com.ecoquest.app.data.repositories

import com.ecoquest.app.data.room.UsuarioEventoDao
import com.ecoquest.app.data.services.UsuarioEventoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioEventoRepository @Inject constructor(
    private val usuarioEventoDao: UsuarioEventoDao
) {

    fun getAll(): Flow<List<UsuarioEventoEntity>> = usuarioEventoDao.getAll()

    fun getByUsuario(usuarioId: Long): Flow<List<UsuarioEventoEntity>> = usuarioEventoDao.getByUsuario(usuarioId)

    fun getByEvento(eventoId: Long): Flow<List<UsuarioEventoEntity>> = usuarioEventoDao.getByEvento(eventoId)

    suspend fun upsertAll(relaciones: List<UsuarioEventoEntity>) = usuarioEventoDao.upsertAll(relaciones)

    suspend fun upsert(relacion: UsuarioEventoEntity) = usuarioEventoDao.upsert(relacion)

    suspend fun delete(usuarioId: Long, eventoId: Long) = usuarioEventoDao.delete(usuarioId, eventoId)

    suspend fun deleteByUsuario(usuarioId: Long) = usuarioEventoDao.deleteByUsuario(usuarioId)

    suspend fun deleteByEvento(eventoId: Long) = usuarioEventoDao.deleteByEvento(eventoId)

    suspend fun deleteAll() = usuarioEventoDao.deleteAll()
}