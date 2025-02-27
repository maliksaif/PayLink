package com.pay.link.domain.usecases.auth

import com.pay.link.data.repositories.auth.AuthRepository
import javax.inject.Inject


class SignOutUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Result<Unit> {
        return try {
            authRepository.logoutUser()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
