package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Reto
import kotlinx.coroutines.flow.Flow

interface RetoRepository {
    fun getAll(): Flow<List<Reto>>
    suspend fun refreshRetos()
}
