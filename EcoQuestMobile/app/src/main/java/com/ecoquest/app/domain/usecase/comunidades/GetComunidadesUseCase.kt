package com.ecoquest.app.domain.usecase.comunidades

import com.ecoquest.app.domain.model.Comunidad
import com.ecoquest.app.domain.repository.ComunidadRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetComunidadesUseCase @Inject constructor(
    private val comunidadRepository: ComunidadRepository
) {
    operator fun invoke(): Flow<List<Comunidad>> = comunidadRepository.getAll()
}
