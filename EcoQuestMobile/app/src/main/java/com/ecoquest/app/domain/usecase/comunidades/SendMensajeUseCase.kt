package com.ecoquest.app.domain.usecase.comunidades

import com.ecoquest.app.domain.model.Mensaje
import com.ecoquest.app.domain.repository.MensajeRepository
import javax.inject.Inject

class SendMensajeUseCase @Inject constructor(
    private val mensajeRepository: MensajeRepository
) {
    suspend operator fun invoke(comunidadId: Long, usuarioId: Long, texto: String): Mensaje? {
        return mensajeRepository.enviarMensaje(comunidadId, usuarioId, texto)
    }
}
