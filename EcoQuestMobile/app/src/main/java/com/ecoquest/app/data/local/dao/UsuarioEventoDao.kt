package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.UsuarioEventoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioEventoDao {

    @Query("SELECT * FROM usuario_evento")
    fun getAll(): Flow<List<UsuarioEventoEntity>>

    @Query("SELECT * FROM usuario_evento WHERE usuarioId = :usuarioId")
    fun getByUsuario(usuarioId: Long): Flow<List<UsuarioEventoEntity>>

    @Query("SELECT * FROM usuario_evento WHERE eventoId = :eventoId")
    fun getByEvento(eventoId: Long): Flow<List<UsuarioEventoEntity>>

    @Upsert
    suspend fun upsertAll(relaciones: List<UsuarioEventoEntity>)

    @Upsert
    suspend fun upsert(relacion: UsuarioEventoEntity)

    @Query("DELETE FROM usuario_evento WHERE usuarioId = :usuarioId AND eventoId = :eventoId")
    suspend fun delete(usuarioId: Long, eventoId: Long)

    @Query("DELETE FROM usuario_evento WHERE usuarioId = :usuarioId")
    suspend fun deleteByUsuario(usuarioId: Long)

    @Query("DELETE FROM usuario_evento WHERE eventoId = :eventoId")
    suspend fun deleteByEvento(eventoId: Long)

    @Query("DELETE FROM usuario_evento")
    suspend fun deleteAll()
}
