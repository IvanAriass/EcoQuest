package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.TransaccionPuntosDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransaccionPuntosRepositoryImpl @Inject constructor(
    private val transaccionPuntosDao: TransaccionPuntosDao,
    private val apiService: ApiService
) : TransaccionPuntosRepository {

    override fun getByUsuario(usuarioId: Long): Flow<List<TransaccionPuntos>> =
        transaccionPuntosDao.getByUsuario(usuarioId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refresh(usuarioId: Long) {
        try {
            val historial = apiService.getHistorialPuntos(usuarioId)
            transaccionPuntosDao.deleteByUsuario(usuarioId)
            transaccionPuntosDao.upsertAll(historial.map { dto ->
                com.ecoquest.app.data.local.entity.TransaccionPuntosEntity(
                    id = dto.id,
                    usuarioId = usuarioId,
                    puntos = dto.puntos,
                    tipo = dto.tipo,
                    concepto = dto.concepto,
                    referenciaId = dto.referenciaId,
                    fecha = dto.fecha
                )
            })
        } catch (_: Exception) { }
    }

    override suspend fun getSaldo(usuarioId: Long): Int {
        return try {
            apiService.getSaldoPuntos(usuarioId)
        } catch (_: Exception) { 0 }
    }
}
