package com.pay.link.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pay.link.data.local.entities.TransactionEntity
import com.pay.link.domain.models.TransactionWithAccountNames
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query(
        """
        SELECT 
            t.id, 
            t.sourceAccountId, 
            s.accountHolder AS sourceAccountName,
            t.destinationAccountId, 
            d.accountHolder AS destinationAccountName,
            t.amount, 
            t.timestamp
        FROM transactions t
        INNER JOIN accounts s ON t.sourceAccountId = s.id
        INNER JOIN accounts d ON t.destinationAccountId = d.id
        ORDER BY t.timestamp DESC
        """
    )
    fun getTransactionsWithAccountNames(): Flow<List<TransactionWithAccountNames>>
}

