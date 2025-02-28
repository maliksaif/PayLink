package com.pay.link.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pay.link.data.local.dao.AccountDao
import com.pay.link.data.local.dao.TransactionDao
import com.pay.link.data.local.entities.AccountEntity
import com.pay.link.data.local.entities.TransactionEntity

@Database(entities = [AccountEntity::class, TransactionEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}

