package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.TransaccionPuntosEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransaccionPuntosDao {
    @Query("SELECT * FROM transacciones_puntos WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    fun getByUsuario(usuarioId: Long): Flow<List<TransaccionPuntosEntity>>

    @Query("SELECT * FROM transacciones_puntos WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    suspend fun getAllByUsuario(usuarioId: Long): List<TransaccionPuntosEntity>

    @Upsert
    suspend fun upsertAll(transacciones: List<TransaccionPuntosEntity>)

    @Query("DELETE FROM transacciones_puntos WHERE usuarioId = :usuarioId")
    suspend fun deleteByUsuario(usuarioId: Long)

    @Query("DELETE FROM transacciones_puntos")
    suspend fun deleteAll()
}
