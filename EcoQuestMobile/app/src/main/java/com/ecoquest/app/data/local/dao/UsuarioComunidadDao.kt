package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.UsuarioComunidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioComunidadDao {

    @Query("SELECT * FROM usuario_comunidad WHERE comunidadId = :comunidadId")
    fun getByComunidad(comunidadId: Long): Flow<List<UsuarioComunidadEntity>>

    @Query("SELECT * FROM usuario_comunidad WHERE usuarioId = :usuarioId")
    fun getByUsuario(usuarioId: Long): Flow<List<UsuarioComunidadEntity>>

    @Upsert
    suspend fun upsertAll(relaciones: List<UsuarioComunidadEntity>)

    @Query("DELETE FROM usuario_comunidad WHERE comunidadId = :comunidadId")
    suspend fun deleteByComunidad(comunidadId: Long)

    @Query("DELETE FROM usuario_comunidad WHERE usuarioId = :usuarioId")
    suspend fun deleteByUsuario(usuarioId: Long)
}
