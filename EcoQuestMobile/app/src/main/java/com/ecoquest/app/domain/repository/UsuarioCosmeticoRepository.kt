package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.UsuarioCosmetico
import kotlinx.coroutines.flow.Flow

interface UsuarioCosmeticoRepository {
    fun getByUsuario(usuarioId: Long): Flow<List<UsuarioCosmetico>>
    suspend fun refresh(usuarioId: Long)
    suspend fun aplicarCosmetico(usuarioId: Long, productoId: Long): Boolean
    suspend fun desaplicarCosmetico(usuarioId: Long, productoId: Long): Boolean
}
