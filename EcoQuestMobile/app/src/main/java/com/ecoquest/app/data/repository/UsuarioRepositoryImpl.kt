package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.UsuarioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioDao: UsuarioDao,
    private val apiService: ApiService
) : UsuarioRepository {

    override fun getAll(): Flow<List<Usuario>> = usuarioDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }

    override fun getById(id: Long): Flow<Usuario?> = usuarioDao.getById(id).map { entity ->
        entity?.toDomain()
    }

    override fun getByNombreUsuario(nombreUsuario: String): Flow<Usuario?> =
        usuarioDao.getByNombreUsuario(nombreUsuario).map { entity ->
            entity?.toDomain()
        }

    override suspend fun getByEmailOnce(email: String): Usuario? =
        usuarioDao.getByEmailOnce(email)?.toDomain()

    override suspend fun refreshUsuarios() {
        runCatching {
            val usuarios = apiService.getUsuarios()
            usuarios.forEach { dto ->
                val entity = dto.toDomain().toEntity()
                usuarioDao.upsert(entity)
            }
        }
    }

    override suspend fun upsert(usuario: Usuario) = usuarioDao.upsert(usuario.toEntity())

    override suspend fun delete(usuario: Usuario) = usuarioDao.delete(usuario.toEntity())

    override suspend fun deleteAll() = usuarioDao.deleteAll()
}
