package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecoquest.app.data.local.entity.ComentarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComentarioDao {

    @Query("SELECT * FROM comentarios WHERE eventoId = :eventoId AND comentarioPadreId IS NULL ORDER BY fechaHora ASC")
    fun getByEventoId(eventoId: Long): Flow<List<ComentarioEntity>>

    @Query("SELECT * FROM comentarios WHERE comentarioPadreId = :comentarioPadreId ORDER BY fechaHora ASC")
    fun getRespuestasByComentarioPadreId(comentarioPadreId: Long): Flow<List<ComentarioEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(comentario: ComentarioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(comentarios: List<ComentarioEntity>)

    @Query("DELETE FROM comentarios WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM comentarios WHERE eventoId = :eventoId")
    suspend fun deleteByEventoId(eventoId: Long)

    @Query("DELETE FROM comentarios")
    suspend fun deleteAll()
}
