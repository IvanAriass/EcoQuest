package com.ecoquest.app.data.remote

import com.ecoquest.app.data.remote.dto.auth.AuthResponse
import com.ecoquest.app.data.remote.dto.auth.LoginRequest
import com.ecoquest.app.data.remote.dto.auth.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}
