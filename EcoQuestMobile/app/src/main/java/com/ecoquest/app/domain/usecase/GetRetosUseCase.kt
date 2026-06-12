package com.ecoquest.app.domain.usecase

import com.ecoquest.app.domain.model.Reto
import com.ecoquest.app.domain.repository.RetoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRetosUseCase @Inject constructor(
    private val retoRepository: RetoRepository
) {
    operator fun invoke(): Flow<List<Reto>> = retoRepository.getAll()
}
