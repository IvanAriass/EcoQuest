package com.ecoquest.app.data.remote

import com.ecoquest.app.data.remote.dto.ComunidadDto
import com.ecoquest.app.data.remote.dto.EventoDto
import com.ecoquest.app.data.remote.dto.ProductoDto
import com.ecoquest.app.data.remote.dto.UsuarioDto
import com.ecoquest.app.data.remote.dto.UsuarioComunidadDto
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Long): UsuarioDto

    @GET("usuarios")
    suspend fun getUsuarios(): List<UsuarioDto>

    @GET("usuarios/buscar")
    suspend fun buscarUsuarios(@Query("nombre") nombre: String): List<UsuarioDto>

    @GET("comunidades")
    suspend fun getComunidades(): List<ComunidadDto>

    @GET("comunidades/{id}")
    suspend fun getComunidad(@Path("id") id: Long): ComunidadDto

    @GET("comunidades/estado")
    suspend fun getComunidadesPorEstado(@Query("estado") estado: String): List<ComunidadDto>

    @POST("comunidades")
    suspend fun crearComunidad(@Body comunidad: ComunidadDto): ComunidadDto

    @PUT("comunidades/{id}")
    suspend fun actualizarComunidad(@Path("id") id: Long, @Body comunidad: ComunidadDto): ComunidadDto

    @DELETE("comunidades/{id}")
    suspend fun eliminarComunidad(@Path("id") id: Long): Response<Void>

    @GET("eventos")
    suspend fun getEventos(): List<EventoDto>

    @GET("eventos/{id}")
    suspend fun getEvento(@Path("id") id: Long): EventoDto

    @GET("eventos/buscar")
    suspend fun buscarEventos(@Query("nombre") nombre: String): List<EventoDto>

    @GET("eventos/estado")
    suspend fun getEventosPorEstado(@Query("estado") estado: String): List<EventoDto>

    @POST("eventos/comunidad/{comunidadId}")
    suspend fun crearEventoEnComunidad(@Path("comunidadId") comunidadId: Long, @Body evento: EventoDto): EventoDto

    @DELETE("eventos/{id}")
    suspend fun eliminarEvento(@Path("id") id: Long): Response<Void>

    @POST("eventos/{eventoId}/usuarios/{usuarioId}")
    suspend fun apuntarseAEvento(@Path("eventoId") eventoId: Long, @Path("usuarioId") usuarioId: Long): Response<Void>

    @DELETE("eventos/{eventoId}/usuarios/{usuarioId}")
    suspend fun desapuntarseDeEvento(@Path("eventoId") eventoId: Long, @Path("usuarioId") usuarioId: Long): Response<Void>

    @POST("usuario-comunidad/unirse")
    suspend fun unirseAComunidad(
        @Query("usuarioId") usuarioId: Long,
        @Query("comunidadId") comunidadId: Long,
        @Query("rol") rol: String
    ): Response<UsuarioComunidadDto>

    @DELETE("usuario-comunidad/abandonar")
    suspend fun abandonarComunidad(
        @Query("usuarioId") usuarioId: Long,
        @Query("comunidadId") comunidadId: Long
    ): Response<Void>

    @GET("usuario-comunidad/usuario/{usuarioId}")
    suspend fun comunidadesDeUsuario(@Path("usuarioId") usuarioId: Long): List<UsuarioComunidadDto>

    @GET("usuario-comunidad/comunidad/{comunidadId}")
    suspend fun usuariosDeComunidad(@Path("comunidadId") comunidadId: Long): List<UsuarioComunidadDto>

    @GET("productos")
    suspend fun getProductos(): List<ProductoDto>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Long): ProductoDto
}
