package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.ComunidadDao
import com.ecoquest.app.data.local.dao.UsuarioComunidadDao
import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.entity.UsuarioComunidadEntity
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.repository.ComunidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComunidadRepositoryImpl @Inject constructor(
    private val comunidadDao: ComunidadDao,
    private val usuarioDao: UsuarioDao,
    private val usuarioComunidadDao: UsuarioComunidadDao,
    private val apiService: ApiService
) : ComunidadRepository {

    override fun getAll(): Flow<List<Comunidad>> {
        val comunidadesFlow = comunidadDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
        val relacionesFlow = usuarioComunidadDao.getAll()
        return comunidadesFlow.combine(relacionesFlow) { comunidades, relaciones ->
            comunidades.map { comunidad ->
                val miembros = relaciones.filter { it.comunidadId == comunidad.id }
                comunidad.copy(
                    usuarios = miembros.map { com.ecoquest.app.domain.model.UsuarioComunidad(rol = it.rol) }
                )
            }
        }
    }

    override fun getById(id: Long): Flow<Comunidad?> {
        val comunidadFlow = comunidadDao.getById(id).map { it?.toDomain() }
        val relacionesFlow = usuarioComunidadDao.getByComunidad(id)
        return comunidadFlow.combine(relacionesFlow) { comunidad, relaciones ->
            comunidad?.copy(
                usuarios = relaciones.map { com.ecoquest.app.domain.model.UsuarioComunidad(rol = it.rol) }
            )
        }
    }

    override suspend fun refreshComunidades() {
        runCatching {
            val apiUsuarios = apiService.getUsuarios()
            apiUsuarios.forEach { dto ->
                usuarioDao.upsert(dto.toDomain().toEntity())
            }

            val comunidades = apiService.getComunidades()
            comunidades.forEach { dto ->
                comunidadDao.upsert(dto.toDomain().toEntity())

                val relaciones = dto.usuarios.mapNotNull { resumen ->
                    val usuarioEntity = usuarioDao.getByNombreUsuarioOnce(resumen.nombreUsuario)
                    usuarioEntity?.let {
                        UsuarioComunidadEntity(usuarioId = it.id, comunidadId = dto.id, rol = resumen.rol)
                    }
                }
                usuarioComunidadDao.deleteByComunidad(dto.id)
                usuarioComunidadDao.upsertAll(relaciones)
            }
        }
    }

    override suspend fun upsert(comunidad: Comunidad) = comunidadDao.upsert(comunidad.toEntity())

    override suspend fun delete(comunidad: Comunidad) = comunidadDao.delete(comunidad.toEntity())

    override suspend fun deleteAll() = comunidadDao.deleteAll()
}
