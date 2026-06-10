package com.ecoquest.app.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.services.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios")
    fun getAll(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getById(id: Long): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario LIMIT 1")
    fun getByNombreUsuario(nombreUsuario: String): Flow<UsuarioEntity?>

    @Upsert
    suspend fun upsertAll(usuarios: List<UsuarioEntity>)

    @Upsert
    suspend fun upsert(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Query("DELETE FROM usuarios")
    suspend fun deleteAll()
}