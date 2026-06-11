package com.ecoquest.app.domain.usecase.auth

import com.ecoquest.app.data.remote.AuthApiService
import com.ecoquest.app.data.remote.dto.auth.LoginRequest
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.Usuario
import com.ecoquest.app.domain.repository.UsuarioRepository
import com.ecoquest.app.managers.TokenManager
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager,
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Usuario> {
        return runCatching {
            val response = authApiService.login(LoginRequest(email, password))
            tokenManager.saveToken(response.token, response.usuario.id)
            val usuario = response.usuario.toDomain()
            usuarioRepository.upsert(usuario)
            usuario
        }
    }
}
