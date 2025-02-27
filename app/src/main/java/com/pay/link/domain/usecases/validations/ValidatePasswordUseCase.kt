package com.pay.link.domain.usecases.validations

import javax.inject.Inject


class ValidatePasswordUseCase @Inject constructor() {

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }

    operator fun invoke(password: String): Boolean {
        return (password.isNotEmpty() && password.length >= MIN_PASSWORD_LENGTH )
    }
}
