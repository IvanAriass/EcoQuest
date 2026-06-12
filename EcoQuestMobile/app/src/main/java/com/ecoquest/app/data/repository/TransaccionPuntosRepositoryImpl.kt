package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.TransaccionPuntosDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import com.ecoquest.app.managers.GlobalToastManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransaccionPuntosRepositoryImpl @Inject constructor(
    private val transaccionPuntosDao: TransaccionPuntosDao,
    private val apiService: ApiService,
    private val globalToastManager: GlobalToastManager
) : TransaccionPuntosRepository {

    private val mutex = Mutex()

    override fun getByUsuario(usuarioId: Long): Flow<List<TransaccionPuntos>> =
        transaccionPuntosDao.getByUsuario(usuarioId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refresh(usuarioId: Long, notifyNew: Boolean): List<TransaccionPuntos> {
        return try {
            mutex.withLock {
                doRefresh(usuarioId, notifyNew)
            }
        } catch (_: Exception) { emptyList() }
    }

    private suspend fun doRefresh(usuarioId: Long, notifyNew: Boolean): List<TransaccionPuntos> {
        val oldAll = transaccionPuntosDao.getAllByUsuario(usuarioId)
        val hasExistingData = oldAll.isNotEmpty()
        val oldGanadoIds = oldAll
            .filter { it.tipo == "GANADO" }
            .map { it.id }
            .toSet()
        val shouldNotify = notifyNew || hasExistingData

        val dtoList = apiService.getHistorialPuntos(usuarioId)
        transaccionPuntosDao.deleteByUsuario(usuarioId)
        transaccionPuntosDao.upsertAll(dtoList.map { dto ->
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

        val domainList = dtoList.map { it.toDomain() }
        val newGanados = domainList.filter { it.tipo == "GANADO" && it.id !in oldGanadoIds }

        if (newGanados.isEmpty() && shouldNotify && oldGanadoIds.isNotEmpty()) {
            delay(1000)
            val retryList = apiService.getHistorialPuntos(usuarioId)
            transaccionPuntosDao.deleteByUsuario(usuarioId)
            transaccionPuntosDao.upsertAll(retryList.map { dto ->
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
            val retryDomain = retryList.map { it.toDomain() }
            for (item in retryDomain) {
                if (item.tipo == "GANADO" && item.id !in oldGanadoIds) {
                    globalToastManager.show(
                        message = item.concepto,
                        points = item.puntos
                    )
                }
            }
            return retryDomain
        }

        if (shouldNotify) {
            for (item in newGanados) {
                globalToastManager.show(
                    message = item.concepto,
                    points = item.puntos
                )
            }
        }
        return domainList
    }

    override suspend fun getSaldo(usuarioId: Long): Int {
        return try {
            apiService.getSaldoPuntos(usuarioId)
        } catch (_: Exception) { 0 }
    }
}
