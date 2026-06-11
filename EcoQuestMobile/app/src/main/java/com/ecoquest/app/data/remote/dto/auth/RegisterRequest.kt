package com.ecoquest.app.data.remote.dto.auth

data class RegisterRequest(
    val nombreUsuario: String,
    val nombre: String,
    val email: String,
    val password: String
)
