package com.pay.link.di

import android.content.Context
import androidx.room.Room
import com.pay.link.data.local.AppDatabase
import com.pay.link.data.local.MIGRATION_1_2
import com.pay.link.data.local.dao.AccountDao
import com.pay.link.data.local.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "bank_db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    fun provideAccountDao(database: AppDatabase): AccountDao = database.accountDao()

    @Provides
    fun provideTransactionDao(database: AppDatabase): TransactionDao = database.transactionDao()
}
