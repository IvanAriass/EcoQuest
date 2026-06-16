package com.ecoquest.app.data.repository

import com.ecoquest.app.data.remote.ApiService
import com.ecoquest.app.data.remote.mapper.toDomain
import com.ecoquest.app.domain.model.RolInfo
import com.ecoquest.app.domain.repository.RolRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RolRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RolRepository {

    @Volatile
    private var cache: Map<String, RolInfo> = emptyMap()

    override suspend fun getRolById(id: String): RolInfo {
        if (cache.isEmpty()) refreshRoles()
        return cache[id.uppercase()] ?: RolInfo(id = id.uppercase(), nombre = id, nivel = 0)
    }

    override suspend fun getRoles(): List<RolInfo> {
        if (cache.isEmpty()) refreshRoles()
        return cache.values.toList()
    }

    override suspend fun refreshRoles() {
        val roles = apiService.getRoles().map { it.toDomain() }
        cache = roles.associateBy { it.id }
    }
}
