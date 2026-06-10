package com.ecoquest.app.data.repositories

import com.ecoquest.app.data.room.UsuarioDao
import com.ecoquest.app.data.services.UsuarioEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepository @Inject constructor(
    private val usuarioDao: UsuarioDao
) {

    fun getAll(): Flow<List<UsuarioEntity>> = usuarioDao.getAll()

    fun getById(id: Long): Flow<UsuarioEntity?> = usuarioDao.getById(id)

    fun getByNombreUsuario(nombreUsuario: String): Flow<UsuarioEntity?> = usuarioDao.getByNombreUsuario(nombreUsuario)

    suspend fun upsertAll(usuarios: List<UsuarioEntity>) = usuarioDao.upsertAll(usuarios)

    suspend fun upsert(usuario: UsuarioEntity) = usuarioDao.upsert(usuario)

    suspend fun delete(usuario: UsuarioEntity) = usuarioDao.delete(usuario)

    suspend fun deleteAll() = usuarioDao.deleteAll()
}