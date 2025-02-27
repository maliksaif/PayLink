package com.pay.link.domain.usecases.auth

import com.pay.link.data.repositories.auth.AuthRepository
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(private val authRepository: AuthRepository)  {

    operator fun invoke () = authRepository.getCurrentUser()


}