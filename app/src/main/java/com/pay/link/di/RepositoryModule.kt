package com.pay.link.di

import com.pay.link.data.local.dao.AccountDao
import com.pay.link.data.local.dao.TransactionDao
import com.pay.link.data.repositories.account.AccountRepositoryImpl
import com.pay.link.data.repositories.transaction.TransactionRepositoryImpl
import com.pay.link.domain.repository.AccountRepository
import com.pay.link.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAccountRepository(accountDao: AccountDao): AccountRepository {
        return AccountRepositoryImpl(accountDao)
    }

    @Provides
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(transactionDao)
    }
}
