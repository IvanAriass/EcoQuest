package com.ecoquest.app.data.repositories

import com.ecoquest.app.data.room.UsuarioComunidadDao
import com.ecoquest.app.data.services.UsuarioComunidadEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioComunidadRepository @Inject constructor(
    private val usuarioComunidadDao: UsuarioComunidadDao
) {

    fun getByComunidad(comunidadId: Long): Flow<List<UsuarioComunidadEntity>> = usuarioComunidadDao.getByComunidad(comunidadId)

    fun getByUsuario(usuarioId: Long): Flow<List<UsuarioComunidadEntity>> = usuarioComunidadDao.getByUsuario(usuarioId)

    suspend fun upsertAll(relaciones: List<UsuarioComunidadEntity>) = usuarioComunidadDao.upsertAll(relaciones)

    suspend fun deleteByComunidad(comunidadId: Long) = usuarioComunidadDao.deleteByComunidad(comunidadId)

    suspend fun deleteByUsuario(usuarioId: Long) = usuarioComunidadDao.deleteByUsuario(usuarioId)
}