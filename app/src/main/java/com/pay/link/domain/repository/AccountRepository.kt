package com.pay.link.domain.repository

import com.pay.link.domain.models.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getAccounts(): List<Account>
    suspend fun getAccountByNumber(accountNumber: String): Account?
    suspend fun updateAccount(account: Account)
    suspend fun updateBalance(accountId: Int, newBalance: Double)
    suspend fun mockData(account: Account)
    suspend fun deleteAccounts()
}
