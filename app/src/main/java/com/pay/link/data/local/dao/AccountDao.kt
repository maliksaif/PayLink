package com.pay.link.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pay.link.data.local.entities.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE accountNumber = :accountNumber LIMIT 1")
    suspend fun getAccountByNumber(accountNumber: String): AccountEntity?

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Query("UPDATE accounts SET balance = :newBalance WHERE id = :accountId")
    suspend fun updateBalance(accountId: Int, newBalance: Double)
}
