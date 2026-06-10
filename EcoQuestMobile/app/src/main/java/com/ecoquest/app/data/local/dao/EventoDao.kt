package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.EventoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {

    @Query("SELECT * FROM eventos")
    fun getAll(): Flow<List<EventoEntity>>

    @Query("SELECT * FROM eventos WHERE id = :id")
    fun getById(id: Long): Flow<EventoEntity?>

    @Query("SELECT * FROM eventos WHERE comunidadId = :comunidadId")
    fun getByComunidad(comunidadId: Long): Flow<List<EventoEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIfAbsent(eventos: List<EventoEntity>)

    @Upsert
    suspend fun upsertAll(eventos: List<EventoEntity>)

    @Upsert
    suspend fun upsert(evento: EventoEntity)

    @Delete
    suspend fun delete(evento: EventoEntity)

    @Query("DELETE FROM eventos")
    suspend fun deleteAll()
}
