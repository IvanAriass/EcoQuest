package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Comunidad
import kotlinx.coroutines.flow.Flow

interface ComunidadRepository {
    fun getAll(): Flow<List<Comunidad>>
    fun getById(id: Long): Flow<Comunidad?>
    suspend fun upsert(comunidad: Comunidad)
    suspend fun delete(comunidad: Comunidad)
    suspend fun deleteAll()
}
