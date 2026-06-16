package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.MensajeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MensajeDao {

    @Query("SELECT * FROM mensajes WHERE comunidadId = :comunidadId ORDER BY fechaHora ASC")
    fun getByComunidad(comunidadId: Long): Flow<List<MensajeEntity>>

    @Upsert
    suspend fun upsertAll(mensajes: List<MensajeEntity>)

    @Query("DELETE FROM mensajes WHERE comunidadId = :comunidadId")
    suspend fun deleteByComunidad(comunidadId: Long)
}
