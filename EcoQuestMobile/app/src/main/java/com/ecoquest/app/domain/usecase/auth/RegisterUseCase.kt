package com.ecoquest.app.domain.usecase.auth

import com.ecoquest.app.data.remote.AuthApiService
import com.ecoquest.app.data.remote.dto.auth.RegisterRequest
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.repository.UsuarioRepository
import com.ecoquest.app.managers.TokenManager
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager,
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(nombreUsuario: String, nombre: String, email: String, password: String): Result<Unit> {
        return runCatching {
            val response = authApiService.register(
                RegisterRequest(nombreUsuario, nombre, email, password)
            )
            tokenManager.saveToken(response.token, response.usuario.id)
            val usuario = response.usuario.toDomain()
            usuarioRepository.upsert(usuario)
        }
    }
}
