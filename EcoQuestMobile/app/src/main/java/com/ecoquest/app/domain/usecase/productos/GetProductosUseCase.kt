package com.ecoquest.app.domain.usecase.productos

import com.ecoquest.app.domain.model.Producto
import com.ecoquest.app.domain.repository.ProductoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductosUseCase @Inject constructor(
    private val productoRepository: ProductoRepository
) {
    operator fun invoke(): Flow<List<Producto>> = productoRepository.getAll()
}
