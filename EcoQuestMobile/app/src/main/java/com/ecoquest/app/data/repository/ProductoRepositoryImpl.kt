package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.ProductoDao
import com.ecoquest.app.data.local.mapper.toDomain
import com.ecoquest.app.domain.model.Producto
import com.ecoquest.app.domain.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductoRepositoryImpl @Inject constructor(
    private val productoDao: ProductoDao
) : ProductoRepository {

    override fun getAll(): Flow<List<Producto>> = productoDao.getAll().map { entities ->
        entities.map { it.toDomain() }
    }
}
