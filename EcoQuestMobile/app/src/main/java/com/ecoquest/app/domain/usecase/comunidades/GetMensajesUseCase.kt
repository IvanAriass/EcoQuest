package com.ecoquest.app.domain.usecase.comunidades

import com.ecoquest.app.domain.model.Mensaje
import com.ecoquest.app.domain.repository.MensajeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMensajesUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    operator fun invoke(comunidadId: Long): Flow<List<Mensaje>> {
        return mensajeRepository.getByComunidad(comunidadId)
    }
}
