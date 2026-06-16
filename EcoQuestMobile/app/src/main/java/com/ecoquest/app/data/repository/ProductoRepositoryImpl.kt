package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.ProductoDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.data.local.mapper.toEntity
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Producto
import com.ecoquest.app.domain.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepositoryImpl @Inject constructor(
    private val productoDao: ProductoDao,
    private val apiService: ApiService
) : ProductoRepository {

    override fun getAll(): Flow<List<Producto>> = productoDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }

    override suspend fun refreshProductos() {
        runCatching {
            val productos = apiService.getProductos()
            productos.forEach { dto ->
                productoDao.upsert(dto.toDomain().toEntity())
            }
        }
    }

    override suspend fun canjearProducto(usuarioId: Long, productoId: Long): String? {
        return runCatching {
            val response = apiService.canjearProducto(usuarioId, productoId)
            if (response.isSuccessful) null
            else response.errorBody()?.string() ?: "Error desconocido"
        }.getOrDefault("Error de conexion")
    }
}
