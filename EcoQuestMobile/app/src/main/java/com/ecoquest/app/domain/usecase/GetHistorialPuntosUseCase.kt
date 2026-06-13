package com.ecoquest.app.domain.usecase

import com.ecoquest.app.domain.model.TransaccionPuntos
import com.ecoquest.app.domain.repository.TransaccionPuntosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistorialPuntosUseCase @Inject constructor(
    private val transaccionPuntosRepository: TransaccionPuntosRepository
) {
    operator fun invoke(usuarioId: Long): Flow<List<TransaccionPuntos>> =
        transaccionPuntosRepository.getByUsuario(usuarioId)
}
