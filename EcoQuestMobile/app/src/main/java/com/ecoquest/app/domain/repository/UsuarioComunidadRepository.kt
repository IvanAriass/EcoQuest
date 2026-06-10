package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioComunidadRepository {
    fun getMiembrosByComunidad(comunidadId: Long): Flow<List<Usuario>>
    fun getComunidadesByUsuario(usuarioId: Long): Flow<List<Comunidad>>
    suspend fun unirse(usuarioId: Long, comunidadId: Long, rol: String)
    suspend fun abandonar(usuarioId: Long, comunidadId: Long)
}
