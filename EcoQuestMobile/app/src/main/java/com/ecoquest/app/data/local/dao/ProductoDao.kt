package com.ecoquest.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ecoquest.app.data.local.entity.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos")
    fun getAll(): Flow<List<ProductoEntity>>

    @Query("SELECT * FROM productos WHERE id = :id")
    fun getById(id: Long): Flow<ProductoEntity?>

    @Upsert
    suspend fun upsert(producto: ProductoEntity)

    @Query("DELETE FROM productos")
    suspend fun deleteAll()
}
