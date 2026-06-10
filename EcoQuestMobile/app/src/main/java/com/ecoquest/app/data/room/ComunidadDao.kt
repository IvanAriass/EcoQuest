package com.ecoquest.app.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.services.ComunidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ComunidadDao {

    @Query("SELECT * FROM comunidades")
    fun getAll(): Flow<List<ComunidadEntity>>

    @Query("SELECT * FROM comunidades WHERE id = :id")
    fun getById(id: Long): Flow<ComunidadEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIfAbsent(comunidades: List<ComunidadEntity>)

    @Upsert
    suspend fun upsertAll(comunidades: List<ComunidadEntity>)

    @Upsert
    suspend fun upsert(comunidad: ComunidadEntity)

    @Delete
    suspend fun delete(comunidad: ComunidadEntity)

    @Query("DELETE FROM comunidades")
    suspend fun deleteAll()
}