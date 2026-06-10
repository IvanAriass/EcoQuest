package com.ecoquest.app.domain.usecase.auth

import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.UsuarioRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(usuario: Usuario, password: String): Result<Unit> {
        return runCatching {
            usuarioRepository.upsert(usuario)
        }
    }
}
