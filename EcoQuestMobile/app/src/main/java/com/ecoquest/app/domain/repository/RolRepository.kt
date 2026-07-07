package com.ecoquest.app.domain.repository

import com.ecoquest.app.domain.model.RolInfo

interface RolRepository {
    suspend fun getRolById(id: String): RolInfo
    suspend fun getRoles(): List<RolInfo>
    suspend fun refreshRoles()
}
