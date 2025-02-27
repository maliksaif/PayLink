package com.pay.link.di

import com.google.firebase.auth.FirebaseAuth
import com.pay.link.data.repositories.auth.AuthRepository
import com.pay.link.domain.usecases.auth.GetCurrentUserUseCase
import com.pay.link.domain.usecases.auth.SignInUseCase
import com.pay.link.domain.usecases.auth.SignOutUseCase
import com.pay.link.domain.usecases.auth.SignUpUseCase
import com.pay.link.domain.usecases.validations.ValidateEmailAddressUseCase
import com.pay.link.domain.usecases.validations.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun validateEmailAddressUseCase(): ValidateEmailAddressUseCase {
        return ValidateEmailAddressUseCase()
    }

    @Provides
    fun validatePasswordUseCase() : ValidatePasswordUseCase {
        return ValidatePasswordUseCase()
    }

    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepository(firebaseAuth)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository)
    }


    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    fun provideSignOutUseCase(authRepository: AuthRepository): SignOutUseCase {
        return SignOutUseCase(authRepository)
    }

    @Provides
    fun provideGetCurrentUserUseCase(authRepository: AuthRepository): GetCurrentUserUseCase {
        return GetCurrentUserUseCase(authRepository)
    }

}
