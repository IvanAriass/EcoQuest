package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.RetoDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.repository.RetoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetoRepositoryImpl @Inject constructor(
    private val retoDao: RetoDao,
    private val apiService: ApiService
) : RetoRepository {

    override fun getAll(): Flow<List<Reto>> =
        retoDao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun refreshRetos() {
        try {
            val retos = apiService.getRetos()
            retoDao.deleteAll()
            retoDao.upsertAll(retos.map { dto ->
                com.ecoquest.app.data.local.entity.RetoEntity(
                    id = dto.id,
                    nombre = dto.nombre,
                    descripcion = dto.descripcion,
                    puntos = dto.puntos,
                    tipo = dto.tipo,
                    requisitoCantidad = dto.requisitoCantidad,
                    frecuencia = dto.frecuencia
                )
            })
        } catch (_: Exception) { }
    }
}
