package com.pay.link.domain.usecases.auth

import com.pay.link.data.repositories.auth.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return try {
            authRepository.loginUser(email, password)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
