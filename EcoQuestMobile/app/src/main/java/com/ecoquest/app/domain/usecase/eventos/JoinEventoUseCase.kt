package com.ecoquest.app.domain.usecase.eventos

import com.ecoquest.app.domain.repository.UsuarioEventoRepository
import javax.inject.Inject

class JoinEventoUseCase @Inject constructor(
    private val usuarioEventoRepository: UsuarioEventoRepository
) {
    suspend operator fun invoke(usuarioId: Long, eventoId: Long) {
        usuarioEventoRepository.unirse(usuarioId, eventoId)
    }
}
