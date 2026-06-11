package com.ecoquest.app.data.remote.dto.auth

import com.ecoquest.app.data.remote.dto.UsuarioDto

data class AuthResponse(
    val token: String,
    val usuario: UsuarioDto
)
