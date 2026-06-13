package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.RetoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RetoDao {
    @Query("SELECT * FROM retos")
    fun getAll(): Flow<List<RetoEntity>>

    @Query("SELECT * FROM retos WHERE id = :id")
    fun getById(id: Long): Flow<RetoEntity?>

    @Upsert
    suspend fun upsertAll(retos: List<RetoEntity>)

    @Query("DELETE FROM retos")
    suspend fun deleteAll()
}
