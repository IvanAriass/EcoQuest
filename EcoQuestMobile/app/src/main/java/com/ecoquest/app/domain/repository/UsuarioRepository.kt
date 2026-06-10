package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioRepository {
    fun getAll(): Flow<List<Usuario>>
    fun getById(id: Long): Flow<Usuario?>
    fun getByNombreUsuario(nombreUsuario: String): Flow<Usuario?>
    suspend fun getByEmailOnce(email: String): Usuario?
    suspend fun upsert(usuario: Usuario)
    suspend fun delete(usuario: Usuario)
    suspend fun deleteAll()
}
