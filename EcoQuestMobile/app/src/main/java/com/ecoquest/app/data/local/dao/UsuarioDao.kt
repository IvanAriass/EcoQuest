package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuarios")
    fun getAll(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getById(id: Long): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario LIMIT 1")
    fun getByNombreUsuario(nombreUsuario: String): Flow<UsuarioEntity?>

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario LIMIT 1")
    suspend fun getByNombreUsuarioOnce(nombreUsuario: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getByEmailOnce(email: String): UsuarioEntity?

    @Upsert
    suspend fun upsertAll(usuarios: List<UsuarioEntity>)

    @Upsert
    suspend fun upsert(usuario: UsuarioEntity)

    @Delete
    suspend fun delete(usuario: UsuarioEntity)

    @Query("DELETE FROM usuarios")
    suspend fun deleteAll()
}
