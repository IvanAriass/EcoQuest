package com.ecoquest.app.domain.usecase.auth

import com.ecoquest.app.domain.repository.UsuarioRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        val usuario = usuarioRepository.getByEmailOnce(email)
            ?: return Result.failure(Exception("Usuario no encontrado"))

        if (usuario.contrasena != password) {
            return Result.failure(Exception("Contraseña incorrecta"))
        }

        return Result.success(Unit)
    }
}
