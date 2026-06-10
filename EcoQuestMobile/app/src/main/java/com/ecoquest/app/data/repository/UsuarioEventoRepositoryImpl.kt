package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.EventoDao
import com.ecoquest.app.data.local.dao.UsuarioDao
import com.ecoquest.app.data.local.dao.UsuarioEventoDao
import com.ecoquest.app.data.local.entity.UsuarioEventoEntity
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.domain.model.Evento
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioEventoRepositoryImpl @Inject constructor(
    private val usuarioEventoDao: UsuarioEventoDao,
    private val eventoDao: EventoDao,
    private val usuarioDao: UsuarioDao
) : UsuarioEventoRepository {

    override fun getEventosByUsuario(usuarioId: Long): Flow<List<Evento>> =
        usuarioEventoDao.getByUsuario(usuarioId).map { relaciones ->
            relaciones.mapNotNull { relacion ->
                eventoDao.getById(relacion.eventoId).firstOrNull()?.toDomain()
            }
        }

    override fun getUsuariosByEvento(eventoId: Long): Flow<List<Usuario>> =
        usuarioEventoDao.getByEvento(eventoId).map { relaciones ->
            relaciones.mapNotNull { relacion ->
                usuarioDao.getById(relacion.usuarioId).firstOrNull()?.toDomain()
            }
        }

    override suspend fun unirse(usuarioId: Long, eventoId: Long) {
        usuarioEventoDao.upsert(UsuarioEventoEntity(usuarioId = usuarioId, eventoId = eventoId))
    }

    override suspend fun abandonar(usuarioId: Long, eventoId: Long) {
        usuarioEventoDao.delete(usuarioId, eventoId)
    }
}
