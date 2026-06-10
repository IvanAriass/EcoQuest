package com.ecoquest.app.data.services

import com.ecoquest.app.data.dto.ComunidadDto
import com.ecoquest.app.data.dto.EventoDto
import com.ecoquest.app.data.dto.UsuarioDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("comunidades")
    suspend fun getComunidades(): List<ComunidadDto>

    @GET("comunidades/{id}")
    suspend fun getComunidad(@Path("id") id: Long): ComunidadDto

    @POST("comunidades")
    suspend fun crearComunidad(@Body comunidad: ComunidadDto): ComunidadDto

    @PUT("comunidades/{id}")
    suspend fun actualizarComunidad(@Path("id") id: Long, @Body comunidad: ComunidadDto): ComunidadDto

    @DELETE("comunidades/{id}")
    suspend fun eliminarComunidad(@Path("id") id: Long): Response<Void>

    @GET("usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Long): UsuarioDto

    @GET("eventos")
    suspend fun getEventos(): List<EventoDto>

    @GET("eventos/{id}")
    suspend fun getEvento(@Path("id") id: Long): EventoDto

    @POST("eventos")
    suspend fun crearEvento(@Body evento: EventoDto): EventoDto

    @DELETE("eventos/{id}")
    suspend fun eliminarEvento(@Path("id") id: Long): Response<Void>

    @POST("usuario-comunidad/unirse")
    suspend fun unirseAComunidad(
        @Query("usuarioId") usuarioId: Long,
        @Query("comunidadId") comunidadId: Long,
        @Query("rol") rol: String
    ): Response<Unit>

    @DELETE("usuario-comunidad/abandonar")
    suspend fun abandonarComunidad(
        @Query("usuarioId") usuarioId: Long,
        @Query("comunidadId") comunidadId: Long
    ): Response<Void>
}
