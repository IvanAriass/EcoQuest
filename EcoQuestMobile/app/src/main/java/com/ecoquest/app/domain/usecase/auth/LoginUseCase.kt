package com.ecoquest.app.domain.usecase.auth

import javax.inject.Inject

class LoginUseCase @Inject constructor() {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return Result.success(Unit)
    }
}
