package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.Producto
import kotlinx.coroutines.flow.Flow

interface ProductoRepository {
    fun getAll(): Flow<List<Producto>>
    suspend fun refreshProductos()
}
