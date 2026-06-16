package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.UsuarioCosmeticoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioCosmeticoDao {

    @Query("SELECT * FROM usuario_cosmeticos WHERE usuarioId = :usuarioId ORDER BY fechaCanje DESC")
    fun getByUsuario(usuarioId: Long): Flow<List<UsuarioCosmeticoEntity>>

    @Query("SELECT * FROM usuario_cosmeticos WHERE usuarioId = :usuarioId ORDER BY fechaCanje DESC")
    suspend fun getAllByUsuario(usuarioId: Long): List<UsuarioCosmeticoEntity>

    @Query("SELECT * FROM usuario_cosmeticos WHERE usuarioId = :usuarioId AND aplicado = 1")
    suspend fun getAplicadosByUsuario(usuarioId: Long): List<UsuarioCosmeticoEntity>

    @Upsert
    suspend fun upsertAll(cosmeticos: List<UsuarioCosmeticoEntity>)

    @Upsert
    suspend fun upsert(cosmetico: UsuarioCosmeticoEntity)

    @Query("DELETE FROM usuario_cosmeticos WHERE usuarioId = :usuarioId")
    suspend fun deleteByUsuario(usuarioId: Long)

    @Query("DELETE FROM usuario_cosmeticos")
    suspend fun deleteAll()
}
