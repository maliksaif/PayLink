package com.pay.link.data.repositories.account

import com.pay.link.data.local.dao.AccountDao
import com.pay.link.data.local.entities.AccountEntity
import com.pay.link.domain.models.Account
import com.pay.link.domain.repository.AccountRepository
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao) :
    AccountRepository {

    override fun getAccounts(): List<Account> {
        return accountDao.getAllAccounts().map { account ->
            Account(
                id = account.id,
                holder = account.accountHolder,
                number = account.accountNumber,
                balance = account.balance,
                bankName = account.bankName
            )
        }
    }

    override suspend fun getAccountByNumber(accountNumber: String): Account? {
        return accountDao.getAccountByNumber(accountNumber)?.let {
            Account(
                id = it.id,
                holder = it.accountHolder,
                number = it.accountNumber,
                balance = it.balance,
                bankName = it.bankName
            )
        }
    }

    override suspend fun updateAccount(account: Account) {
        accountDao.updateAccount(
            AccountEntity(
                id = account.id,
                accountHolder = account.holder,
                accountNumber = account.number,
                balance = account.balance,
                bankName = account.bankName
            )
        )
    }

    override suspend fun updateBalance(accountId: Int, newBalance: Double) {
        accountDao.updateBalance(accountId, newBalance)
    }

    override suspend fun mockData(account: Account) {
        accountDao.createAccount(
            AccountEntity(
                accountHolder = account.holder,
                accountNumber = account.number,
                balance = account.balance,
                bankName = account.bankName
            )
        )
    }

    override suspend fun deleteAccounts() {
        accountDao.deleteAllAccounts()
    }
}
