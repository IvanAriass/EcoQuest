package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Evento
import kotlinx.coroutines.flow.Flow

interface EventoRepository {
    fun getAll(): Flow<List<Evento>>
    fun getById(id: Long): Flow<Evento?>
    fun getByComunidad(comunidadId: Long): Flow<List<Evento>>
    suspend fun refreshEventos()
    suspend fun upsert(evento: Evento)
    suspend fun delete(evento: Evento)
    suspend fun deleteAll()
}
