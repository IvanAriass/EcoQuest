package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UsuarioEventoRepository {
    fun getEventosByUsuario(usuarioId: Long): Flow<List<Evento>>
    fun getUsuariosByEvento(eventoId: Long): Flow<List<Usuario>>
    suspend fun unirse(usuarioId: Long, eventoId: Long)
    suspend fun abandonar(usuarioId: Long, eventoId: Long)
}
