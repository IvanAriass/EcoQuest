package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.TransaccionPuntos
import kotlinx.coroutines.flow.Flow

interface TransaccionPuntosRepository {
    fun getByUsuario(usuarioId: Long): Flow<List<TransaccionPuntos>>
    suspend fun refresh(usuarioId: Long)
    suspend fun getSaldo(usuarioId: Long): Int
}
