package com.pay.link.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val sourceAccountId: Int,
    val destinationAccountId: Int,
    val amount: Double,
    val timestamp: Long
)
