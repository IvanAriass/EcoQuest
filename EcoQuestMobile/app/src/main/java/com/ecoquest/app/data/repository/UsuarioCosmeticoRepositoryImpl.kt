package com.ecoquest.app.data.repository

import com.ecoquest.app.data.local.dao.UsuarioCosmeticoDao
import com.ecoquest.app.data.local.entity.UsuarioCosmeticoEntity
import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.dto.UsuarioCosmeticoDto
import com.ecoquest.app.domain.model.UsuarioCosmetico
import com.ecoquest.app.domain.repository.UsuarioCosmeticoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsuarioCosmeticoRepositoryImpl @Inject constructor(
    private val usuarioCosmeticoDao: UsuarioCosmeticoDao,
    private val apiService: ApiService
) : UsuarioCosmeticoRepository {

    override fun getByUsuario(usuarioId: Long): Flow<List<UsuarioCosmetico>> =
        usuarioCosmeticoDao.getByUsuario(usuarioId).map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refresh(usuarioId: Long) {
        runCatching {
            val dtos = apiService.getCosmeticos(usuarioId)
            usuarioCosmeticoDao.deleteByUsuario(usuarioId)
            usuarioCosmeticoDao.upsertAll(dtos.map { it.toEntity(usuarioId) })
        }
    }

    override suspend fun aplicarCosmetico(usuarioId: Long, productoId: Long): Boolean {
        return runCatching {
            val response = apiService.aplicarCosmetico(usuarioId, productoId)
            if (response.isSuccessful) {
                refresh(usuarioId)
                true
            } else false
        }.getOrDefault(false)
    }

    override suspend fun desaplicarCosmetico(usuarioId: Long, productoId: Long): Boolean {
        return runCatching {
            val response = apiService.desaplicarCosmetico(usuarioId, productoId)
            if (response.isSuccessful) {
                refresh(usuarioId)
                true
            } else false
        }.getOrDefault(false)
    }
}

private fun UsuarioCosmeticoDto.toEntity(usuarioId: Long) = UsuarioCosmeticoEntity(
    id = id,
    usuarioId = usuarioId,
    productoId = producto?.id ?: 0,
    productoNombre = producto?.nombre.orEmpty(),
    productoTipo = producto?.tipo.orEmpty(),
    productoDescripcion = producto?.descripcion.orEmpty(),
    productoImagen = producto?.imagen.orEmpty(),
    productoPrecio = producto?.precio ?: 0,
    aplicado = aplicado,
    fechaCanje = fechaCanje
)

private fun UsuarioCosmeticoEntity.toDomain() = UsuarioCosmetico(
    id = id,
    productoId = productoId,
    productoNombre = productoNombre,
    productoTipo = productoTipo,
    productoDescripcion = productoDescripcion,
    productoImagen = productoImagen.let { img ->
        if (img.isBlank()) return@let img
        if (img.startsWith("http://") || img.startsWith("https://"))
            img.replace("http://localhost:9000", "http://10.0.2.2:9000")
        else
            "http://10.0.2.2:9000/api/productos/imagen/$img"
    },
    productoPrecio = productoPrecio,
    aplicado = aplicado,
    fechaCanje = fechaCanje
)
