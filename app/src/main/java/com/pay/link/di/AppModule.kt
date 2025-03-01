package com.pay.link.di

import com.google.firebase.auth.FirebaseAuth
import com.pay.link.data.repositories.auth.AuthRepository
import com.pay.link.domain.repository.AccountRepository
import com.pay.link.domain.repository.TransactionRepository
import com.pay.link.domain.usecases.accounts.DeleteAllAccountsUseCase
import com.pay.link.domain.usecases.accounts.GenerateMockAccountDataUseCase
import com.pay.link.domain.usecases.auth.GetCurrentUserUseCase
import com.pay.link.domain.usecases.auth.SignInUseCase
import com.pay.link.domain.usecases.auth.SignOutUseCase
import com.pay.link.domain.usecases.auth.SignUpUseCase
import com.pay.link.domain.usecases.common.GenerateAccountNumberUseCase
import com.pay.link.domain.usecases.common.GenerateRandomNameUseCase
import com.pay.link.domain.usecases.transfer.GetTransactionsWithNamesUseCase
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

    @Provides
    fun provideGenerateAccountNumberUseCase() : GenerateAccountNumberUseCase {
        return GenerateAccountNumberUseCase()
    }

    @Provides
    fun provideGenerateMockAccountDataUseCase(accountRepository: AccountRepository) : GenerateMockAccountDataUseCase {
        return GenerateMockAccountDataUseCase(accountRepository)
    }

    @Provides
    fun provideGenerateRandomNameUseCase() : GenerateRandomNameUseCase {
        return GenerateRandomNameUseCase()
    }

    @Provides
    fun provideDeleteAllAccounts(accountRepository: AccountRepository) : DeleteAllAccountsUseCase {
        return DeleteAllAccountsUseCase(accountRepository)
    }

    @Provides
    fun provideGetTransactionsWithNamesUseCase(transactionRepository: TransactionRepository) : GetTransactionsWithNamesUseCase {
        return GetTransactionsWithNamesUseCase(transactionRepository)
    }

}
