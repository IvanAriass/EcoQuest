package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.entity.UsuarioComunidadEntity
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioComunidadRepositoryImpl @Inject constructor(
    private val usuarioComunidadDao: UsuarioComunidadDao,
    private val usuarioDao: UsuarioDao
) : UsuarioComunidadRepository {

    override fun getMiembrosByComunidad(comunidadId: Long): Flow<List<Usuario>> =
        usuarioComunidadDao.getByComunidad(comunidadId).map { relaciones ->
            relaciones.mapNotNull { relacion ->
                usuarioDao.getById(relacion.usuarioId).firstOrNull()?.toDomain()
            }
        }

    override fun getComunidadesByUsuario(usuarioId: Long): Flow<List<Comunidad>> {
        return usuarioComunidadDao.getByUsuario(usuarioId).map { relaciones ->
            relaciones.map { relacion ->
                com.ecoquest.app.domain.model.Comunidad(id = relacion.comunidadId)
            }
        }
    }

    override suspend fun unirse(usuarioId: Long, comunidadId: Long, rol: String) {
        usuarioComunidadDao.upsertAll(
            listOf(UsuarioComunidadEntity(usuarioId = usuarioId, comunidadId = comunidadId, rol = rol))
        )
    }

    override suspend fun abandonar(usuarioId: Long, comunidadId: Long) {
        usuarioComunidadDao.deleteByUsuario(usuarioId)
    }
}
