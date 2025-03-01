package com.pay.link.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pay.link.data.local.dao.AccountDao
import com.pay.link.data.local.dao.TransactionDao
import com.pay.link.data.local.entities.AccountEntity
import com.pay.link.data.local.entities.TransactionEntity

@Database(entities = [AccountEntity::class, TransactionEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Add the new column "bankName" to the "accounts" table
        db.execSQL("ALTER TABLE accounts ADD COLUMN bankName TEXT DEFAULT 'Unknown' NOT NULL")
    }
}

