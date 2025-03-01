package com.pay.link.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountNumber: String,
    val accountHolder: String,
    val balance: Double,
    val bankName: String,
)
