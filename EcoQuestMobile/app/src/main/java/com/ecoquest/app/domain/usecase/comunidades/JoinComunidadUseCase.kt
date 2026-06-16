package com.ecoquest.app.domain.usecase.comunidades

import com.ecoquest.app.domain.repository.UsuarioComunidadRepository
import javax.inject.Inject

class JoinComunidadUseCase @Inject constructor(
    private val usuarioComunidadRepository: UsuarioComunidadRepository
) {
    suspend operator fun invoke(usuarioId: Long, comunidadId: Long, rol: String = "SEMILLA") {
        usuarioComunidadRepository.unirse(usuarioId, comunidadId, rol)
    }
}
