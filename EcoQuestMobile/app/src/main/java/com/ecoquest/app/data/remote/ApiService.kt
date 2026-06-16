package com.ecoquest.app.data.remote

import com.ecoquest.app.data.remote.dto.CanjeProductoDto
import com.ecoquest.app.data.remote.dto.CategoriaDto
import com.ecoquest.app.data.remote.dto.ComentarioDto
import com.ecoquest.app.data.remote.dto.ComunidadDto
import com.ecoquest.app.data.remote.dto.EventoDto
import com.ecoquest.app.data.remote.dto.ProductoDto
import com.ecoquest.app.data.remote.dto.RetoDto
import com.ecoquest.app.data.remote.dto.TransaccionPuntosDto
import com.ecoquest.app.data.remote.dto.UsuarioComunidadDto
import com.ecoquest.app.data.remote.dto.UsuarioCosmeticoDto
import com.ecoquest.app.data.remote.dto.UsuarioDto
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

    @GET("categorias")
    suspend fun getCategorias(): List<CategoriaDto>

    @GET("productos")
    suspend fun getProductos(): List<ProductoDto>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Long): ProductoDto

    @GET("retos")
    suspend fun getRetos(): List<RetoDto>

    @GET("retos/{id}")
    suspend fun getReto(@Path("id") id: Long): RetoDto

    @GET("usuarios/{usuarioId}/puntos/historial")
    suspend fun getHistorialPuntos(@Path("usuarioId") usuarioId: Long): List<TransaccionPuntosDto>

    @GET("usuarios/{usuarioId}/puntos/saldo")
    suspend fun getSaldoPuntos(@Path("usuarioId") usuarioId: Long): Int

    @GET("eventos/{eventoId}/comentarios")
    suspend fun getComentarios(@Path("eventoId") eventoId: Long): List<ComentarioDto>

    @POST("eventos/{eventoId}/comentarios")
    suspend fun crearComentario(
        @Path("eventoId") eventoId: Long,
        @Body body: Map<String, @JvmSuppressWildcards Any>
    ): ComentarioDto

    @GET("eventos/{eventoId}/comentarios/{comentarioId}/respuestas")
    suspend fun getRespuestas(
        @Path("eventoId") eventoId: Long,
        @Path("comentarioId") comentarioId: Long
    ): List<ComentarioDto>

    // --- Canje de productos ---

    @POST("usuarios/{usuarioId}/canjear/{productoId}")
    suspend fun canjearProducto(
        @Path("usuarioId") usuarioId: Long,
        @Path("productoId") productoId: Long
    ): Response<Unit>

    @GET("usuarios/{usuarioId}/canjes")
    suspend fun getCanjes(@Path("usuarioId") usuarioId: Long): List<CanjeProductoDto>

    // --- Cosméticos ---

    @GET("usuarios/{usuarioId}/cosmeticos")
    suspend fun getCosmeticos(@Path("usuarioId") usuarioId: Long): List<UsuarioCosmeticoDto>

    @GET("usuarios/{usuarioId}/cosmeticos/aplicados")
    suspend fun getCosmeticosAplicados(@Path("usuarioId") usuarioId: Long): List<UsuarioCosmeticoDto>

    @POST("usuarios/{usuarioId}/cosmeticos/{productoId}/aplicar")
    suspend fun aplicarCosmetico(
        @Path("usuarioId") usuarioId: Long,
        @Path("productoId") productoId: Long
    ): Response<Void>

    @POST("usuarios/{usuarioId}/cosmeticos/{productoId}/desaplicar")
    suspend fun desaplicarCosmetico(
        @Path("usuarioId") usuarioId: Long,
        @Path("productoId") productoId: Long
    ): Response<Void>
}
